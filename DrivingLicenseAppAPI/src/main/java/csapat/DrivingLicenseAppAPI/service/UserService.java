package csapat.DrivingLicenseAppAPI.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import csapat.DrivingLicenseAppAPI.config.email.EmailSender;
import csapat.DrivingLicenseAppAPI.entity.*;
import csapat.DrivingLicenseAppAPI.repository.EducationRepository;
import csapat.DrivingLicenseAppAPI.repository.UserRepository;
import csapat.DrivingLicenseAppAPI.service.other.ValidatorCollection;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;

@Transactional(noRollbackFor = {DataIntegrityViolationException.class, ConstraintViolationException.class, SQLIntegrityConstraintViolationException.class, SQLException.class})
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;
    private final EducationRepository educationRepository;
    private final ObjectMapper objectMapper;

    public ResponseEntity<JsonNode> login(String email, String password) {
        try {
            if (email == null || password == null) {
                return ResponseEntity.status(422).build();
            }

            if (!ValidatorCollection.emailValidator(email.trim())) {
                return ResponseEntity.status(415).build();
            }

            Users loggedUser = userRepository.getUserByEmail(email.trim()).orElse(null);

            if (loggedUser == null || loggedUser.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            }
            boolean successFullLogin = passwordEncoder.matches(password.trim(), loggedUser.getPassword());

            if (!successFullLogin || loggedUser.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            }
            loggedUser.setLastLogin(new Date());
            userRepository.save(loggedUser);

            JsonNode homePageUser = createHomePageObject(loggedUser);
            return ResponseEntity.ok(homePageUser);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Object> register(Users newUser) {
        try {
            if (newUser == null) {
                return ResponseEntity.status(422).build();
            }

            Education searchedEducation = educationRepository.getEducation(newUser.getUserEducation().getId()).orElse(null);
            if (searchedEducation == null || searchedEducation.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else if (!newUser.getGender().equals("male") && !newUser.getGender().equals("female") && !newUser.getGender().equals("other")) {
                return ResponseEntity.status(415).body("invalidGender");
            } else if (!ValidatorCollection.emailValidator(newUser.getEmail().trim())) {
                return ResponseEntity.status(415).body("invalidEmail");
            } else if (!ValidatorCollection.phoneValidator(newUser.getPhone().trim())) {
                return ResponseEntity.status(415).body("invalidPhone");
            } else if (!ValidatorCollection.passwordValidator(newUser.getPassword().trim())) {
                return ResponseEntity.status(415).body("invalidPassword");
            } else if (newUser.getId() != null) {
                return ResponseEntity.status(415).body("invalidObject");
            } else {
                newUser.setPassword(passwordEncoder.encode(newUser.getPassword().trim()));
                try {
                    emailSender.sendEmailAboutRegistration(newUser.getEmail());
                } catch (Exception e) {
                    return ResponseEntity.internalServerError().body("emailSenderError");
                }
                userRepository.save(newUser);
            }
            return ResponseEntity.ok().build();
        } catch (DataIntegrityViolationException e) {
            String errorMsg = e.getMessage().contains("Duplicate entry") && e.getMessage().contains("for key 'email'") ? "emailDuplicate" : "phoneDuplicate";
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity.status(409).body(errorMsg);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    //password reset
    public ResponseEntity<String> getVerificationCode(String email) {
        try {
            if (email == null) {
                return ResponseEntity.status(422).build();
            }

            List<String> emailList = userRepository.getAllEmail();

            if (!ValidatorCollection.emailValidator(email.trim())) {
                return ResponseEntity.status(415).body("invalidEmail");
            } else if (!emailList.contains(email.trim())) {
                return ResponseEntity.status(404).body("emailNotFound");
            } else {
                Users searchedUser = userRepository.getUserByEmail(email.trim()).orElse(null);

                if (searchedUser == null || searchedUser.getIsDeleted()) {
                    return ResponseEntity.status(404).body("userNotFound");
                }

                String vCode = generateVerificationCode();
                searchedUser.setVCode(passwordEncoder.encode(vCode));
                userRepository.save(searchedUser);
                try {
                    emailSender.sendVerificationCodeEmail(email, vCode);
                } catch (Exception e) {
                    return ResponseEntity.internalServerError().body("emailSenderError");
                }
                return ResponseEntity.ok().body("success");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Object> checkVCode(String userVCode, String email) {
        try {
            if (userVCode == null || email == null) {
                return ResponseEntity.status(422).build();
            }

            if (userVCode.trim().length() != 10) {
                return ResponseEntity.status(415).body("invalidVerificationCode");
            } else if (!ValidatorCollection.emailValidator(email.trim())) {
                return ResponseEntity.status(415).body("invalidEmail");
            } else {
                Users searchedUser = userRepository.getUserByEmail(email.trim()).orElse(null);
                if (searchedUser == null || searchedUser.getIsDeleted()) {
                    return ResponseEntity.notFound().build();
                }

                return ResponseEntity.ok().body(passwordEncoder.matches(userVCode.trim(), searchedUser.getVCode()) ? "success" : "failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }

    }

    public ResponseEntity<String> updatePassword(String email, String newPassword) {
        try {
            if (email == null || newPassword == null) {
                return ResponseEntity.status(422).build();
            }

            Users searchedUser = userRepository.getUserByEmail(email.trim()).orElse(null);

            if (searchedUser == null || searchedUser.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else if (!ValidatorCollection.emailValidator(email.trim())) {
                return ResponseEntity.status(415).body("invalidEmail");
            } else if (!ValidatorCollection.passwordValidator(newPassword.trim())) {
                return ResponseEntity.status(415).body("invalidPassword");
            } else {
                searchedUser.setPassword(passwordEncoder.encode(newPassword.trim()));
                userRepository.save(searchedUser);
                return ResponseEntity.ok("success");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // update:
    public ResponseEntity<Object> updateUser(Users updatedUser) {
        try {
            if (updatedUser == null) {
                return ResponseEntity.status(422).build();
            }

            if (updatedUser.getId() == null) {
                return ResponseEntity.status(415).body("invalidObject");
            } else {
                List<Education> allEducation = educationRepository.getAllEducation();

                if (!ValidatorCollection.phoneValidator(updatedUser.getPhone().trim())) {
                    return ResponseEntity.status(415).body("invalidPhone");
                } else if (!ValidatorCollection.emailValidator(updatedUser.getEmail().trim())) {
                    return ResponseEntity.status(415).body("invalidEmail");
                } else if (!allEducation.contains(updatedUser.getUserEducation())) {
                    return ResponseEntity.status(415).body("invalidEducation");
                } else {
                    Users result = userRepository.save(updatedUser);
                    return ResponseEntity.ok(result);
                }
            }
        } catch (DataIntegrityViolationException e) {
            String errorMsg = e.getMessage().contains("Duplicate entry") && e.getMessage().contains("for key 'email'") ? "emailDuplicate" : "phoneDuplicate";
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity.status(409).body(errorMsg);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Object> updatePfp(Integer id, MultipartFile pfpFile) {
        try {
            if (id == null || pfpFile == null) {
                return ResponseEntity.status(422).build();
            }

            Users searchedUser = userRepository.getUser(id).orElse(null);

            if (searchedUser == null || searchedUser.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                String filePath = File.separator + pfpFile.getOriginalFilename();

                try {
                    FileOutputStream fout = new FileOutputStream(filePath);
                    fout.write(pfpFile.getBytes());
                    fout.close();

                    searchedUser.setPfpPath("assets\\images\\pfp" + File.separator + pfpFile.getOriginalFilename());
                } catch (Exception e) {
                    e.printStackTrace();
                    return ResponseEntity.internalServerError().body("fileUploadingError");
                }

                return ResponseEntity.ok().body(userRepository.save(searchedUser));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("serverError");
        }
    }

    // delete:
    public ResponseEntity<String> deleteUser(Integer id) {
        try {
            if (id == null) {
                return ResponseEntity.status(422).build();
            }

            Users searchedUser = userRepository.getUser(id).orElse(null);
            if (searchedUser == null || searchedUser.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            }

            userRepository.deleteUser(id);

            return ResponseEntity.ok().body("success");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Users> getUserById(Integer id) {
        try {
            if (id == null) {
                return ResponseEntity.status(422).build();
            }

            Users searchedUser = userRepository.getUser(id).orElse(null);

            if (searchedUser == null || searchedUser.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok().body(searchedUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    //Nem Endpoint:
    public String generateVerificationCode() {
        String code = "";
        ArrayList<String> characters = new ArrayList<String>(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"));

        for (int i = 97; i <= 122; i++) {
            characters.add(String.valueOf((char) i));
        }

        while (code.length() != 10) {
            Random random = new Random();
            code += characters.get(random.nextInt(characters.size()));
        }

        return code;
    }

    public JsonNode createHomePageObject(Users loggedUser) {

        JsonNode returnObject = objectMapper.createObjectNode();
        ((ObjectNode) returnObject).put("id", loggedUser.getId());
        ((ObjectNode) returnObject).put("firstName", loggedUser.getFirstName());
        ((ObjectNode) returnObject).put("lastName", loggedUser.getLastName());
        ((ObjectNode) returnObject).put("pfpPath", loggedUser.getPfpPath());
        ((ObjectNode) returnObject).put("role", objectMapper.valueToTree(loggedUser.getRole()));

        if (loggedUser.getRole().getName().equals("ROLE_student")) {
            ((ObjectNode) returnObject).put("studentId", loggedUser.getStudent().getId());

            JsonNode instructor = objectMapper.createObjectNode();
            ((ObjectNode) instructor).put("id", loggedUser.getStudent().getStudentInstructor().getInstructorUser().getId());
            ((ObjectNode) instructor).put("firstName", loggedUser.getStudent().getStudentInstructor().getInstructorUser().getFirstName());
            ((ObjectNode) instructor).put("lastName", loggedUser.getStudent().getStudentInstructor().getInstructorUser().getLastName());
            ((ObjectNode) instructor).put("pfpPath", loggedUser.getStudent().getStudentInstructor().getInstructorUser().getPfpPath());

            JsonNode vehicle = objectMapper.createObjectNode();
            ((ObjectNode) vehicle).put("id", loggedUser.getStudent().getStudentInstructor().getVehicle().getId());
            ((ObjectNode) vehicle).put("name", loggedUser.getStudent().getStudentInstructor().getVehicle().getName());
            ((ObjectNode) vehicle).put("type", loggedUser.getStudent().getStudentInstructor().getVehicle().getVehicleType().getName());
            ((ObjectNode) vehicle).put("licensePlate", loggedUser.getStudent().getStudentInstructor().getVehicle().getLicensePlate());

            ((ObjectNode) returnObject).put("school", createSchoolJson(loggedUser.getStudent().getStudentSchool()));
            ((ObjectNode) returnObject).put("instructor", instructor);
            ((ObjectNode) returnObject).put("vehicle", vehicle);

        } else if (loggedUser.getRole().getName().equals("ROLE_instructor")) {
            ((ObjectNode) returnObject).put("instructorId", loggedUser.getInstructor().getId());

            ((ObjectNode) returnObject).put("school", createSchoolJson(loggedUser.getInstructor().getInstructorSchool()));
            ArrayList<JsonNode> studentDetails = new ArrayList<>();

            for (Students student : loggedUser.getInstructor().getStudents()) {
                JsonNode studentNode = objectMapper.createObjectNode();
                ((ObjectNode) studentNode).put("id", student.getStudentUser().getId());
                ((ObjectNode) studentNode).put("firstName", student.getStudentUser().getFirstName());
                ((ObjectNode) studentNode).put("lastName", student.getStudentUser().getLastName());
                studentDetails.add(studentNode);
            }

            ArrayNode studentNode = objectMapper.valueToTree(studentDetails);
            ((ObjectNode) returnObject).putArray("students").addAll(studentNode);

        } else if (loggedUser.getRole().getName().equals("ROLE_school_admin") || loggedUser.getRole().getName().equals("ROLE_school_owner")) {
            School school = loggedUser.getAdminSchool();
            ArrayList<JsonNode> studentDetails = new ArrayList<>();

            for (Students student : school.getStudentsList()) {
                JsonNode studentNode = objectMapper.createObjectNode();
                ((ObjectNode) studentNode).put("id", student.getStudentUser().getId());
                ((ObjectNode) studentNode).put("firstName", student.getStudentUser().getFirstName());
                ((ObjectNode) studentNode).put("lastName", student.getStudentUser().getLastName());
                studentDetails.add(studentNode);
            }

            ArrayList<JsonNode> instructorDetails = new ArrayList<>();
            for (Instructors instructors : school.getInstructorsList()) {
                JsonNode instructorNode = objectMapper.createObjectNode();
                ((ObjectNode) instructorNode).put("id", instructors.getInstructorUser().getId());
                ((ObjectNode) instructorNode).put("firstName", instructors.getInstructorUser().getFirstName());
                ((ObjectNode) instructorNode).put("lastName", instructors.getInstructorUser().getLastName());
                instructorDetails.add(instructorNode);
            }
            ((ObjectNode) returnObject).put("schoolId", loggedUser.getRole().getName().equals("ROLE_school_admin") ? loggedUser.getAdminSchool().getId() : loggedUser.getOwnedSchool().getId());
            ArrayNode studentNode = objectMapper.valueToTree(studentDetails);
            ((ObjectNode) returnObject).putArray("students").addAll(studentNode);
            ArrayNode instructorNode = objectMapper.valueToTree(instructorDetails);
            ((ObjectNode) returnObject).putArray("instructors").addAll(instructorNode);
        }


//        System.out.println(returnObject);
        return returnObject;
    }

    public JsonNode createSchoolJson(School schoolObject) {
        JsonNode school = objectMapper.createObjectNode();
        ((ObjectNode) school).put("id", schoolObject.getId());
        ((ObjectNode) school).put("name", schoolObject.getName());
        ((ObjectNode) school).put("email", schoolObject.getEmail());
        ((ObjectNode) school).put("phone", schoolObject.getPhone());
        ((ObjectNode) school).put("country", schoolObject.getCountry());
        ((ObjectNode) school).put("town", schoolObject.getTown());
        ((ObjectNode) school).put("address", schoolObject.getAddress());
        ((ObjectNode) school).put("promoText", schoolObject.getPromoText());
        ((ObjectNode) school).put("bannerImg", schoolObject.getBannerImgPath());
//        nyitvatartas
        ArrayNode oNode = objectMapper.valueToTree(schoolObject.getOpeningDetails());
        ((ObjectNode) school).putArray("openingDetails").addAll(oNode);
        return school;
    }
}

/*
 * HTTP STATUS KODOK:
 *   - 200: Sikeres muvelet
 *   - 404: Not Found
 *   - 409: Mar foglalt nev
 *   - 415: Unsupported Media Type --> Ha az adott adat invalid
 *   - 422: Hianyzo parameter/response body
 *   - 500: Internal Server Error
 * */