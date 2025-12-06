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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;
    private final EducationRepository educationRepository;
    private final ObjectMapper objectMapper;

    public ResponseEntity<JsonNode> login(String email, String password) {
        Users loggedUser = userRepository.getUserByEmail(email);

        if (loggedUser == null) {
            return ResponseEntity.notFound().build();
        }
        boolean successFullLogin = passwordEncoder.matches(password, loggedUser.getPassword());

        if (!successFullLogin || loggedUser.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        }
        loggedUser.setLastLogin(LocalDateTime.now());
        userRepository.save(loggedUser);

        JsonNode homePageUser = createHomePageObject(loggedUser);
        return ResponseEntity.ok(homePageUser);
    }

    public ResponseEntity<Object> register(Users newUser) {
        Education searchedEducation = educationRepository.findById(newUser.getUserEducation().getId()).orElse(null);
        if (searchedEducation == null || searchedEducation.getId() == null) {
            return ResponseEntity.notFound().build();
        } else if (!ValidatorCollection.emailValidator(newUser.getEmail())) {
            System.out.println("invalidEmail");
            return ResponseEntity.status(417).body("invalidEmail");
        } else if (!ValidatorCollection.phoneValidator(newUser.getPhone())) {
            System.out.println("invalidPhone");
            return ResponseEntity.status(417).body("invalidPhone");
        } else if (!ValidatorCollection.passwordValidator(newUser.getPassword())) {
            System.out.println("invalidPassword");
            return ResponseEntity.status(417).body("invalidPassword");
        } else {
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
//            emailSender.sendEmailAboutRegistration(newUser.getEmail()); //Itt majd le kell kezelni, ha a user egy nem letezo emailt ad meg
            userRepository.save(newUser);
        }

        return ResponseEntity.ok().build();
    }

    //password reset
    public ResponseEntity<String> getVerificationCode(String email) {
        List<String> emailList = userRepository.getAllEmail();

        if (!ValidatorCollection.emailValidator(email.trim())) {
            return ResponseEntity.status(417).body("InvalidEmail");
        } else if (!emailList.contains(email.trim())) {
            return ResponseEntity.notFound().build();
        } else {
            Users searchedUser = userRepository.getUserByEmail(email);

            if (searchedUser == null || searchedUser.getId() == null || searchedUser.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            }

            String vCode = generateVerificationCode();
            searchedUser.setVCode(passwordEncoder.encode(vCode));
            userRepository.save(searchedUser);
            emailSender.sendVerificationCodeEmail(email, vCode);
            return ResponseEntity.ok().body("success");
        }
    }

    public ResponseEntity<Object> checkVCode(String userVCode, String email) {
        if (userVCode.length() != 10) {
            return ResponseEntity.status(417).body("InvalidVerificationCode");
        } else if (!ValidatorCollection.emailValidator(email)) {
            return ResponseEntity.status(417).body("InvalidEmail");
        } else {
            Users searchedUser = userRepository.getUserByEmail(email);
            if (searchedUser == null || searchedUser.getId() == null || searchedUser.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok().body(passwordEncoder.matches(userVCode, searchedUser.getVCode()) ? "success" : "failed");
        }

    }

    public ResponseEntity<String> updatePassword(String email, String newPassword) {
        Users user = userRepository.getUserByEmail(email);

        if (!ValidatorCollection.emailValidator(email)) {
            return ResponseEntity.status(417).body("InvalidEmail");
        } else if (!ValidatorCollection.passwordValidator(newPassword)) {
            return ResponseEntity.status(417).body("InvalidPassword");
        } else if (user == null || user.getId() == null || user.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return ResponseEntity.ok("success");
        }
    }

    // update:
    public ResponseEntity<Object> updateUser(Users updatedUser) {

        if (updatedUser.getId() == null) {
            return ResponseEntity.notFound().build();
        } else {
            List<Education> allEducation = educationRepository.getAllEducation();

            if (!ValidatorCollection.phoneValidator(updatedUser.getPhone())) {
                return ResponseEntity.status(417).body("InvalidPhone");
            } else if (!ValidatorCollection.emailValidator(updatedUser.getEmail())) {
                return ResponseEntity.status(417).body("InvalidEmail");
            } else if (!allEducation.contains(updatedUser.getUserEducation())) {
                return ResponseEntity.status(417).body("InvalidEducation");
            } else {
                Users result = userRepository.save(updatedUser);
                return ResponseEntity.ok(result);
            }
        }
    }

    public ResponseEntity<Users> updatePfp(Integer id, MultipartFile pfpFile) {
        Users searchedUser = userRepository.getUser(id);

        if (searchedUser.getId() == null || searchedUser.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else {
            String filePath = "" + File.separator + pfpFile.getOriginalFilename();

            try {
                FileOutputStream fout = new FileOutputStream(filePath);
                fout.write(pfpFile.getBytes());
                fout.close();

                searchedUser.setPfpPath("assets\\images\\pfp" + File.separator + pfpFile.getOriginalFilename());
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }

            return ResponseEntity.ok().body(userRepository.save(searchedUser));
        }
    }

    // delete:
    public ResponseEntity<String> deleteUser(Integer id) {
        Users searchedUser = userRepository.getUser(id);
        if (searchedUser == null || searchedUser.getId() == null || searchedUser.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        }

        userRepository.deleteUser(id);

        return ResponseEntity.ok().body("success");
    }

    //egyeb endpointok:
    public ResponseEntity<Users> getUserById(Integer id) {
        Users searchedUser = userRepository.getUser(id);

        if (searchedUser == null || searchedUser.getId() == null || searchedUser.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(searchedUser);
        }
    }

    //egyeb:
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
            ((ObjectNode) returnObject).put("school", createSchoolJson(loggedUser.getInstructor().getInstructorSchool()));
            ArrayList<JsonNode> studentDetails = new ArrayList<>();

            for (Students student : loggedUser.getInstructor().getStudents()) {
                JsonNode studentNode = objectMapper.createObjectNode();
                ((ObjectNode) studentNode).put("id", student.getId());
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
                ((ObjectNode) studentNode).put("id", student.getId());
                ((ObjectNode) studentNode).put("firstName", student.getStudentUser().getFirstName());
                ((ObjectNode) studentNode).put("lastName", student.getStudentUser().getLastName());
                studentDetails.add(studentNode);
            }

            ArrayList<JsonNode> instructorDetails = new ArrayList<>();
            for (Instructors instructors : school.getInstructorsList()) {
                JsonNode instructorNode = objectMapper.createObjectNode();
                ((ObjectNode) instructorNode).put("id", instructors.getId());
                ((ObjectNode) instructorNode).put("firstName", instructors.getInstructorUser().getFirstName());
                ((ObjectNode) instructorNode).put("lastName", instructors.getInstructorUser().getLastName());
                instructorDetails.add(instructorNode);
            }


            ArrayNode studentNode = objectMapper.valueToTree(studentDetails);
            ((ObjectNode) returnObject).putArray("students").addAll(studentNode);
            ArrayNode instructorNode = objectMapper.valueToTree(studentDetails);
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
