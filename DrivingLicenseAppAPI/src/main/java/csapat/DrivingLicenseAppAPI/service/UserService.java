package csapat.DrivingLicenseAppAPI.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import csapat.DrivingLicenseAppAPI.config.email.EmailSender;
import csapat.DrivingLicenseAppAPI.dto.ProfileCard;
import csapat.DrivingLicenseAppAPI.dto.UserUpdate;
import csapat.DrivingLicenseAppAPI.entity.*;
import csapat.DrivingLicenseAppAPI.exception.InvalidDataException;
import csapat.DrivingLicenseAppAPI.exception.NotFoundException;
import csapat.DrivingLicenseAppAPI.exception.UniqueErrorException;
import csapat.DrivingLicenseAppAPI.repository.EducationRepository;
import csapat.DrivingLicenseAppAPI.repository.InstructorRepository;
import csapat.DrivingLicenseAppAPI.repository.UserRepository;
import csapat.DrivingLicenseAppAPI.repository.VehicleRepository;
import csapat.DrivingLicenseAppAPI.service.other.ValidatorCollection;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
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
        Users loggedUser = userRepository.findByEmail(email.trim()).orElseThrow(() -> new NotFoundException("userNotFound"));
        boolean successFullLogin = passwordEncoder.matches(password.trim(), loggedUser.getPassword());
        if (!successFullLogin) {
            throw new NotFoundException("userNotFound");
        }
        loggedUser.setLastLogin(new Date());
        userRepository.save(loggedUser);
        JsonNode homePageUser = createHomePageObject(loggedUser);
        return ResponseEntity.ok(homePageUser);
    }

    public ResponseEntity<Object> register(Users newUser, String registerAs) {

        if (!registerAs.equals("student") && !registerAs.equals("instructor") && !registerAs.equals("user")) {
            return ResponseEntity.status(415).body("invalidParameter");
        } else if (userRepository.findByEmail(newUser.getEmail()).isPresent()) {
            throw new UniqueErrorException("duplicateEmail");
        } else if (userRepository.findByPhone(newUser.getPhone()).isPresent()) {
            throw new UniqueErrorException("duplicatePhone");
        }

        Education searchedEducation = educationRepository.getEducation(newUser.getUserEducation().getId()).orElseThrow(() -> new NotFoundException("educationNotFound"));
        if (!newUser.getGender().equals("male") && !newUser.getGender().equals("female") && !newUser.getGender().equals("other")) {
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
            return ResponseEntity.status(415).body(Map.of("statusText", "invalidDate"));
        } else {
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword().trim()));
            try {
                emailSender.sendEmailAboutRegistration(newUser.getEmail(), newUser.getFirstName() + " " + newUser.getLastName());
            } catch (MessagingException mailException) {
                mailException.printStackTrace();
                System.out.println("Email error");
            }

            newUser.setPfpPath("http://localhost:8080/pfp/defaultPfp.png");
            newUser.setCreatedAt(new Date());

            newUser = userRepository.save(newUser);

            if (registerAs.equals("instructor")) {
                Instructors newInstructor = new Instructors();
                newInstructor.setVehicle(vehicleRepository.save(new Vehicle()));
                newInstructor.setInstructorUser(newUser);
                instructorRepository.save(newInstructor);
                userRepository.setRoleOfUser(newUser.getId(), 3L);
            }

        }
        return ResponseEntity.ok().body(Map.of("statusText", "successfullyRegistration"));

    }

    public ResponseEntity<Object> getVerificationCode(String email) {
        if (!ValidatorCollection.emailValidator(email.trim())) {
            throw new InvalidDataException("invalidEmail");
        } else {
            Users searchedUser = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("emailNotFound"));
            String vCode = generateVerificationCode();
            searchedUser.setvCode(passwordEncoder.encode(vCode));
            userRepository.save(searchedUser);
            try {
                emailSender.sendVerificationCodeEmail(email, searchedUser.getFirstName() + " " + searchedUser.getLastName(), vCode);
            } catch (MessagingException mailException) {
            }

            return ResponseEntity.ok().build();
        }
    }

    public ResponseEntity<Object> checkVerificationCode(String userVCode, String email) {
        Users searchedUser = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("userNotFound"));

        if (userVCode.length() != 10) {
            throw new InvalidDataException("invalidVerificationCode");
        } else {
            JsonNode returnObject = objectMapper.createObjectNode();
            ((ObjectNode) returnObject).put("success", passwordEncoder.matches(userVCode, searchedUser.getvCode()));
            return ResponseEntity.ok().body(returnObject);
        }
    }

    public ResponseEntity<Object> updatePassword(String email, String newPassword) {
        if (!ValidatorCollection.emailValidator(email)) {
            throw new InvalidDataException("invalidEmail");
        }

        Users searchedUser = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("userNotFound"));
        if (!ValidatorCollection.passwordValidator(newPassword)) {
            throw new InvalidDataException("invalidPassword");
        } else {
            String hashedPassword = passwordEncoder.encode(newPassword);
            searchedUser.setPassword(hashedPassword);
            userRepository.save(searchedUser);
            try {
                emailSender.sendEmailAboutPasswordReset(searchedUser.getEmail(), searchedUser.getFirstName() + " " + searchedUser.getLastName());
            } catch (MessagingException e) {
            }
            return ResponseEntity.ok().build();
        }
    }

    @PreAuthorize("(isAuthenticated() and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> updateUser(Long id, UserUpdate updatedUser) {
        try {
            Users searchedUser = userRepository.getUser(id).orElseThrow(() -> new NotFoundException("userNotFound"));
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMAN);

            if (!searchedUser.getEmail().equals(updatedUser.email()) && userRepository.findByEmail(updatedUser.email()).isPresent()) {
                throw new UniqueErrorException("duplicateEmail");
            } else if (!searchedUser.getPhone().equals(updatedUser.phone()) && userRepository.findByPhone(updatedUser.phone()).isPresent()) {
                throw new UniqueErrorException("duplicatePhone");
            }

            Education searchedEducation = educationRepository.getEducation(updatedUser.educationId()).orElseThrow(() -> new NotFoundException("educationNotFound"));
            if (!ValidatorCollection.phoneValidator(updatedUser.phone().trim())) {
                return ResponseEntity.status(415).body("invalidPhone");
            } else if (!ValidatorCollection.emailValidator(updatedUser.email().trim())) {
                return ResponseEntity.status(415).body("invalidEmail");
            } else if (!updatedUser.gender().equals("male") && !updatedUser.gender().equals("female") && !updatedUser.gender().equals("other")) {
                return ResponseEntity.status(415).body("invalidGender");
            } else if (dateFormat.parse(updatedUser.birthDate()).after(new Date())) {
                return ResponseEntity.status(415).body(Map.of("statusText", "invalidDate"));
            } else {
                searchedUser.setFirstName(updatedUser.firstName().trim());
                searchedUser.setLastName(updatedUser.lastName().trim());
                searchedUser.setEmail(updatedUser.email().trim());
                searchedUser.setPhone(updatedUser.phone().trim());
                searchedUser.setBirthDate(dateFormat.parse(updatedUser.birthDate()));
                searchedUser.setGender(updatedUser.gender());
                searchedUser.setUserEducation(searchedEducation);
                return ResponseEntity.ok(userRepository.save(searchedUser));
            }

        } catch (ParseException e) {
            throw new InvalidDataException("invalidDateFormat");
        }
    }

    @PreAuthorize("(isAuthenticated() and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> updatePfp(Long id, MultipartFile pfpFile) {
        Users searchedUser = userRepository.getUser(id).orElseThrow(() -> new NotFoundException("userNotFound"));
        String filePath = "images/pfp" + File.separator + searchedUser.getId() + pfpFile.getOriginalFilename();

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

    @PreAuthorize("(isAuthenticated() and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<String> deleteUser(Long id) {
        Users searchedUser = userRepository.getUser(id).orElseThrow(() -> new NotFoundException("userNotFound"));
        userRepository.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("(isAuthenticated() and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> getUserById(Long id, Boolean isLogin) {
        Users searchedUser = userRepository.findById(id).orElseThrow(() -> new NotFoundException("userNotFound"));
        if (isLogin) {
            return ResponseEntity.ok().body(createHomePageObject(searchedUser));
        } else {
            return ResponseEntity.ok().body(searchedUser);
        }
    }

    @PreAuthorize("(hasRole('administrator') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> getAllUser(Pageable pageable) {
        Page<Users> allUser = userRepository.findAll(pageable);
        List<ProfileCard> returnList = new ArrayList<>();

        for (Users i : allUser) {
            returnList.add(new ProfileCard(i.getId(), i.getFirstName() + " " + i.getLastName(), i.getPfpPath(), i.getId()));
        }
        HttpHeaders header = new HttpHeaders();
        header.add("PageNumber", allUser.getTotalPages() + "");
        return new ResponseEntity<>(returnList, header, HttpStatus.OK);
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
                ((ObjectNode) instructor).put("email", loggedUser.getStudent().getStudentInstructor().getInstructorUser().getEmail());

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

            if (loggedUser.getInstructor().getInstructorSchool() != null) {
                ((ObjectNode) returnObject).put("school", createSchoolJson(loggedUser.getInstructor().getInstructorSchool()));
            }
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
            School school;
            if (loggedUser.getRole().getName().equals("ROLE_school_admin")) {
                school = loggedUser.getAdminSchool();
            } else {
                school = loggedUser.getOwnedSchool();
            }
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
            ((ObjectNode) returnObject).put("school", createSchoolJson(school));
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