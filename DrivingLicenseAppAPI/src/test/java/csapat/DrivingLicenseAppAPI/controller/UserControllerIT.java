package csapat.DrivingLicenseAppAPI.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import csapat.DrivingLicenseAppAPI.entity.Education;
import csapat.DrivingLicenseAppAPI.entity.Role;
import csapat.DrivingLicenseAppAPI.entity.Users;
import csapat.DrivingLicenseAppAPI.repository.EducationRepository;
import csapat.DrivingLicenseAppAPI.repository.RoleRepository;
import csapat.DrivingLicenseAppAPI.repository.UserRepository;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//38db
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource(locations = "classpath:test-application.properties")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerIT {

    MockMvc mockMvc;
    UserRepository userRepository;
    EducationRepository educationRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    ObjectMapper objectMapper;

    @Autowired
    public UserControllerIT(MockMvc mockMvc, UserRepository userRepository, EducationRepository educationRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.userRepository = userRepository;
        this.educationRepository = educationRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.objectMapper = objectMapper;
    }

    private final String BASE_URL = "http://localhost:8080/users";

    @BeforeAll
    public void setUpRequiredDatas() {
        List<Education> educations = new ArrayList<Education>(Arrays.asList(
                new Education("education1"),
                new Education("education2"),
                new Education("education3")
        ));
        educationRepository.saveAll(educations);
        educations = educationRepository.findAll();
        Role role = roleRepository.save(new Role("ROLE_user"));

        ArrayList<Users> userList = new ArrayList<>(Arrays.asList(
                new Users("firstName1", "lastName1", "bzhalmai@gmail.com", "06706285231", new Date(), "male", passwordEncoder.encode("test5.Asd"), educations.get(0), role),
                new Users("firstName2", "lastName2", "sulisdolgok8@gmail.com", "06706285232", new Date(), "male", passwordEncoder.encode("test5.Asd"), educations.get(0), role),
                new Users("firstName3", "lastName3", "halmaib.21d@acsjszki.hu", "06706285233", new Date(), "male", passwordEncoder.encode("test5.Asd"), educations.get(0), role)
        ));

        userRepository.saveAll(userList);
    }

    @Test
    @DisplayName("Login with existent e-mail and password combination.")
    public void loginWithExistentUsersDetails() throws Exception {
//        JsonNode requestBody = createLoginBody("bzhalmai@gmail.com", "test5.Asd");
//        mockMvc.perform(post(BASE_URL + "/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", Is.is(1)));
    }

    @Test
    @DisplayName("Login with non-existent e-mail.")
    public void loginWithNonExistentEmail() throws Exception {
//        JsonNode requestBody = createLoginBody("bzhalmaii@gmail.com", "test5.Asd");
//        mockMvc.perform(post(BASE_URL + "/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
//                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Login with existent e-mail and bad password.")
    public void loginWithBadPassword() throws Exception {
//        JsonNode requestBody = createLoginBody("bzhalmai@gmail.com", "test.Asd");
//        mockMvc.perform(post(BASE_URL + "/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
//                .andExpect(status().isNotFound());
    }

    public JsonNode createLoginBody(String email, String password) {
        JsonNode returnObject = objectMapper.createObjectNode();
        ((ObjectNode) returnObject).put("email", email);
        ((ObjectNode) returnObject).put("password", password);
        return returnObject;
    }

    @Test
    @DisplayName("Registration with valid datas as student.")
    public void registerWithValidDatasAsStudent() throws Exception {
//        Users newUser = new Users("registerStudent1", "registerStudent1", "register@gmail.com", "06706285231", new Date(), "male", passwordEncoder.encode("test5.Asd"), educationRepository.findById(1).get());
//        int sizeBeforeRegistration = userRepository.findAll().size();
//
//        mockMvc.perform(post(BASE_URL + "/register/student").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newUser)))
//                .andExpect(status().isOk());
//        int sizeAfterRegistration = userRepository.findAll().size();
//        Assertions.assertEquals(sizeBeforeRegistration + 1, sizeAfterRegistration, "");

    }

    @Test
    @DisplayName("Registration with valid datas as instructor.")
    public void registerWithValidDatasAsInstructor() throws Exception {
    }

    @Test
    @DisplayName("Registration with non-existent role.")
    public void registerAsNonExistentRole() throws Exception {
    }

    @Test
    @DisplayName("Registration with invalid Users object. (id attribute isn't null)")
    public void registerWithInvalidObject() throws Exception {
    }

    @Test
    @DisplayName("Registration with invalid gender. Gender isn't equal to male, female or other.")
    public void registerWithInvalidGender() throws Exception {
    }

    @Test
    @DisplayName("Registration with invalid e-mail. Required e-")
    public void registerWithInvalidEmail() throws Exception {
    }

    @Test
    @DisplayName("Registration with invalid phone.")
    public void registerWithInvalidPhone() throws Exception {
    }

    @Test
    @DisplayName("Registration with invalid password.")
    public void registerWithInvalidPassword() throws Exception {
    }

    @Test
    @DisplayName("Registration with invalid birthDate. It must be in the past.")
    public void registerWithInvalidBirthDate() throws Exception {
    }

    @Test
    public void registerWithDuplicatedEmail() throws Exception {}

    @Test
    public void registerWithDuplicatedPhone() throws Exception {}

    @Test
    @DisplayName("")
    public void getVerificationCodeWithExistentEmail() throws Exception {
    }

    @Test
    public void getVerificationCodeWithNonExistentEmail() throws Exception {
    }

    @Test
    public void checkValidVerificationCode() throws Exception {
    }

    @Test
    public void checkVerificationCodeWithNonExistentEmail() throws Exception {
    }

    @Test
    public void checkInvalidVerificationCode() throws Exception {
    }

    @Test
    public void updatePasswordWithValidDatas() throws Exception {
    }

    @Test
    public void updatePasswordWithNonExistentEmail() throws Exception {
    }

    @Test
    public void updatePasswordWithInvalidPassword() throws Exception {
    }

    @Test
    public void updateExistentUserWithValidDatas() throws Exception {
    }

    @Test
    public void updateNonExistentUser() throws Exception {
    }

    @Test
    public void updateWithInvalidObject() throws Exception {
    }

    @Test
    public void updateWithInvalidGender() throws Exception {
    }

    @Test
    public void updateWithInvalidEmail() throws Exception {
    }

    @Test
    public void updateWithInvalidPhone() throws Exception {
    }

    @Test
    public void updateWithInvalidPassword() throws Exception {
    }

    @Test
    public void updateWithInvalidBirthDate() throws Exception {
    }

    @Test
    public void updateExistentUserPfpWithValidPhoto() throws Exception {
    }

    @Test
    public void updateNoneExistentUsersPfp() throws Exception {
    }

    @Test
    public void updateUsersPfpWithInvalidPhoto() throws Exception {
    }

    @Test
    public void deleteExistentUser() throws Exception {
    }

    @Test
    public void deleteNonExistentUser() throws Exception {
    }

    @Test
    public void getExistentUserById() throws Exception {
    }

    @Test
    public void getNonExistentUserById() throws Exception {
    }

    @Test
    public void getAllUser() throws Exception {
    }


}
