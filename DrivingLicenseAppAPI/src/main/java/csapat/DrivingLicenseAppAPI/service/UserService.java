package csapat.DrivingLicenseAppAPI.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import csapat.DrivingLicenseAppAPI.config.email.EmailSender;
import csapat.DrivingLicenseAppAPI.entity.*;
import csapat.DrivingLicenseAppAPI.repository.EducationRepository;
import csapat.DrivingLicenseAppAPI.repository.InstructorRepository;
import csapat.DrivingLicenseAppAPI.repository.UserRepository;
import csapat.DrivingLicenseAppAPI.repository.VehicleRepository;
import csapat.DrivingLicenseAppAPI.service.other.ProfileCard;
import csapat.DrivingLicenseAppAPI.service.other.ValidatorCollection;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final InstructorRepository instructorRepository;
    private final VehicleRepository vehicleRepository;
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

            Users loggedUser = userRepository.findByEmail(email.trim()).orElse(null);

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

    public ResponseEntity<Object> register(Users newUser, String registerAs) {
        try {
            if (newUser == null) {
                return ResponseEntity.status(422).build();
            }

            if (!registerAs.equals("student") && !registerAs.equals("instructor")) {
                return ResponseEntity.status(415).body("invalidParameter");
            }

            Education searchedEducation = educationRepository.getEducation(newUser.getUserEducation().getId()).orElse(null);
            if (searchedEducation == null || searchedEducation.getIsDeleted()) {
                return ResponseEntity.status(404).body("educationNotFound");
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
            } else if (newUser.getBirthDate().after(new Date())) {
                return ResponseEntity.status(415).body("invalidBirthDate");
            } else {
                newUser.setPassword(passwordEncoder.encode(newUser.getPassword().trim()));
                try {
                    emailSender.sendEmailAboutRegistration(newUser.getEmail());
                } catch (MailSendException mailException) {
                }

                newUser.setPfpPath("http://localhost:8080/pfp/defaultPfp.png");
                newUser = userRepository.save(newUser);

                if (registerAs.equals("instructor")) {
                    Instructors newInstructor = new Instructors();
                    newInstructor.setVehicle(vehicleRepository.save(new Vehicle()));
                    newInstructor.setInstructorUser(newUser);
                    instructorRepository.save(newInstructor);
                    userRepository.setRoleOfUser(newUser.getId(), 3);
                }
            }
            return ResponseEntity.ok().build();
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("Duplicate entry '" + newUser.getEmail() + "' for key 'email'")) {
                return ResponseEntity.status(409).body("duplicateEmail");
            } else if (e.getMessage().contains("Duplicate entry '" + newUser.getPhone() + "' for key 'phone'")) {
                return ResponseEntity.status(409).body("duplicatePhone");
            } else {
                return ResponseEntity.internalServerError().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    //password reset
    public ResponseEntity<Object> getVerificationCode(String email) {
        try {
            if (email == null) {
                return ResponseEntity.status(422).build();
            }

            Users searchedUser = userRepository.findByEmail(email).orElse(null);

            if (!ValidatorCollection.emailValidator(email.trim())) {
                return ResponseEntity.status(415).body("invalidEmail");
            } else if (searchedUser == null || searchedUser.getIsDeleted()) {
                return ResponseEntity.status(404).body("emailNotFound");
            } else {
                String vCode = generateVerificationCode();
                searchedUser.setVCode(passwordEncoder.encode(vCode));
                userRepository.save(searchedUser);
                System.out.println(vCode);
                try {
                    emailSender.sendVerificationCodeEmail(email, vCode);
                } catch (MailSendException mailException) {
                }

                return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Object> checkVerificationCode(String userVCode, String email) {
        try {
            if (userVCode == null || email == null) {
                return ResponseEntity.status(422).build();
            }

            Users searchedUser = userRepository.findByEmail(email).orElse(null);
            if (searchedUser == null || searchedUser.getIsDeleted()) {
                return ResponseEntity.status(404).body("userNotFound");
            }

            if (userVCode.length() != 10) {
                return ResponseEntity.status(415).body("invalidVerificationCode");
            } else {
                JsonNode returnObject = objectMapper.createObjectNode();
                ((ObjectNode) returnObject).put("success", passwordEncoder.matches(userVCode, searchedUser.getVCode()));
                return ResponseEntity.ok().body(returnObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Object> updatePassword(String email, String newPassword) {
        try {
            if (email == null || newPassword == null) {
                return ResponseEntity.status(422).build();
            }

            Users searchedUser = userRepository.findByEmail(email).orElse(null);

            if (searchedUser == null || searchedUser.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            }

            if (!ValidatorCollection.emailValidator(email)) {
                return ResponseEntity.status(415).body("InvalidEmail");
            } else if (!ValidatorCollection.passwordValidator(newPassword)) {
                return ResponseEntity.status(415).body("InvalidPassword");
            } else {
                String hashedPassword = passwordEncoder.encode(newPassword);
                searchedUser.setPassword(hashedPassword);
                userRepository.save(searchedUser);
                emailSender.sendEmailAboutPasswordReset(searchedUser.getEmail());
                return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // update:
    public ResponseEntity<Object> updateUser(Integer id, String firstName, String lastName, String email, String phone, String birthDateText, String gender, Integer educationId) {
        try {
            if (id == null || firstName == null || lastName == null || email == null || phone == null || birthDateText == null || gender == null || educationId == null) {
                return ResponseEntity.status(422).build();
            }

            Users searchedUser = userRepository.getUser(id).orElse(null);
            if (searchedUser == null || searchedUser.getIsDeleted()) {
                return ResponseEntity.status(404).body("userNotFound");
            } else {
                Education searchedEducation = educationRepository.getEducation(educationId).orElse(null);
                if (searchedEducation == null) {
                    return ResponseEntity.status(404).body("educationNotFound");
                } else if (!ValidatorCollection.phoneValidator(phone.trim())) {
                    return ResponseEntity.status(415).body("invalidPhone");
                } else if (!ValidatorCollection.emailValidator(email.trim())) {
                    return ResponseEntity.status(415).body("invalidEmail");
                } else if (!gender.equals("male") && !gender.equals("female") && !gender.equals("other")) {
                    return ResponseEntity.status(415).body("invalidGender");
                } else {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMAN);

                    searchedUser.setFirstName(firstName.trim());
                    searchedUser.setLastName(lastName.trim());
                    searchedUser.setEmail(email.trim());
                    searchedUser.setPhone(phone.trim());
                    searchedUser.setBirthDate(dateFormat.parse(birthDateText));
                    searchedUser.setGender(gender);
                    searchedUser.setUserEducation(searchedEducation);
                    return ResponseEntity.ok(userRepository.save(searchedUser));
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

                String filePath = "src/main/resources/static/pfp" + File.separator + searchedUser.getId() + pfpFile.getOriginalFilename();

                try {
                    FileOutputStream fout = new FileOutputStream(filePath);
                    fout.write(pfpFile.getBytes());
                    fout.close();

                    searchedUser.setPfpPath("http://localhost:8080/pfp/" + searchedUser.getId() + pfpFile.getOriginalFilename());
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

            return ResponseEntity.ok().build();
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

    public ResponseEntity<Object> getAllUser(Pageable pageable) {
        try {
            Page<Users> allUser = userRepository.findAll(pageable);
            List<ProfileCard> returnList = new ArrayList<>();

            for (Users i : allUser) {
                returnList.add(new ProfileCard(i.getId(), i.getFirstName() + " " + i.getLastName(), i.getPfpPath(), i.getId()));
            }

            HttpHeaders header = new HttpHeaders();
            header.add("PageNumber", allUser.getTotalPages() + "");

            return new ResponseEntity<>(returnList, header, HttpStatus.OK);
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
            ((ObjectNode) returnObject).put("categoryId", loggedUser.getStudent().getSelectedCategory().getId());
            ((ObjectNode) returnObject).put("category", loggedUser.getStudent().getSelectedCategory().getName());

            if (loggedUser.getStudent().getStudentInstructor() != null) {
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

                ((ObjectNode) returnObject).put("instructor", instructor);
                ((ObjectNode) returnObject).put("vehicle", vehicle);
                ((ObjectNode) returnObject).put("instructorId", loggedUser.getStudent().getStudentInstructor().getId());
            }
            ((ObjectNode) returnObject).put("school", createSchoolJson(loggedUser.getStudent().getStudentSchool()));

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