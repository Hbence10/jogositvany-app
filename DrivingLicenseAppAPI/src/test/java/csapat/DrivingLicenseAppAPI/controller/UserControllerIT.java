package csapat.DrivingLicenseAppAPI.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import csapat.DrivingLicenseAppAPI.entity.Education;
import csapat.DrivingLicenseAppAPI.entity.Users;
import csapat.DrivingLicenseAppAPI.repository.InstructorRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//38db
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource(locations = "classpath:test-application.properties")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class UserControllerIT {

    MockMvc mockMvc;
    UserRepository userRepository;
    ObjectMapper objectMapper;
    PasswordEncoder passwordEncoder;
    InstructorRepository instructorRepository;

    @Autowired
    public UserControllerIT(MockMvc mockMvc, UserRepository userRepository, ObjectMapper objectMapper, PasswordEncoder passwordEncoder, InstructorRepository instructorRepository) {
        this.mockMvc = mockMvc;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
        this.instructorRepository = instructorRepository;
    }

    private final String BASE_URL = "http://localhost:8080/users";
    int testUserId;

    @BeforeEach
    public void setup() {
        Users newUser = userRepository.save(new Users("testUser1", "registerStudent1", "test@gmail.com", "06701111111", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1, "Általános Iskola")));
        testUserId = newUser.getId();
    }

    @Test
    @DisplayName("Login with existent e-mail and password combination.")
    public void loginWithExistentUsersDetails() throws Exception {
        JsonNode requestBody = createLoginBody("test@gmail.com", "test5.Asd");
        mockMvc.perform(post(BASE_URL + "/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Is.is(testUserId)));
    }

    @Test
    @DisplayName("Login with non-existent e-mail.")
    public void loginWithNonExistentEmail() throws Exception {
        JsonNode requestBody = createLoginBody("bzhalmaii@gmail.com", "test5.Asd");
        mockMvc.perform(post(BASE_URL + "/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Login with existent e-mail and bad password.")
    public void loginWithBadPassword() throws Exception {
        JsonNode requestBody = createLoginBody("bzhalmai@gmail.com", "test.Asd");
        mockMvc.perform(post(BASE_URL + "/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isNotFound());
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
        Users newUser = new Users("registerStudent1", "registerStudent1", "register@gmail.com", "06707412356", new Date(), "male", "test5.Asd", new Education(1, "Általános Iskola"));
        long usersSizeBeforeRegistration = userRepository.count();

        mockMvc.perform(post(BASE_URL + "/register/student").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk());

        long usersSizeAfterRegistration = userRepository.count();
        Assertions.assertEquals(usersSizeBeforeRegistration + 1, usersSizeAfterRegistration, "");

    }

    @Test
    @DisplayName("Registration with valid datas as instructor.")
    public void registerWithValidDatasAsInstructor() throws Exception {
        Users newUser = new Users("registerStudent1", "registerStudent1", "register@gmail.com", "06707412356", new Date(), "male", "test5.Asd", new Education(1, "Általános Iskola"));
        long usersSizeBeforeRegistration = userRepository.count();
        long instructorsSizeBeforeRegistration = instructorRepository.count();

        mockMvc.perform(post(BASE_URL + "/register/instructor").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk());

        long usersSizeAfterRegistration = userRepository.count();
        long instructorsSizeAfterRegistration = instructorRepository.count();

        Assertions.assertEquals(usersSizeBeforeRegistration + 1, usersSizeAfterRegistration, "");
        Assertions.assertEquals(instructorsSizeBeforeRegistration + 1, instructorsSizeAfterRegistration, "");
    }

    @Test
    @DisplayName("Registration with non-existent role.")
    public void registerAsNonExistentRole() throws Exception {
        Users newUser = new Users("registerStudent1", "registerStudent1", "register@gmail.com", "06707412356", new Date(), "male", "test5.Asd", new Education(1, "Általános Iskola"));
        long usersSizeBeforeRegistration = userRepository.count();
        mockMvc.perform(post(BASE_URL + "/register/admin").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().is4xxClientError())
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", Is.is("invalidParameter")));
        long usersSizeAfterRegistration = userRepository.count();
        Assertions.assertEquals(usersSizeBeforeRegistration, usersSizeAfterRegistration, "");
    }

    @Test
    @DisplayName("Registration with invalid Users object. (id attribute isn't null)")
    public void registerWithInvalidObject() throws Exception {
        Users newUser = new Users(4132, "registerStudent1", "registerStudent1", "register@gmail.com", "06707412356", new Date(), "male", "test5.Asd", new Education(1, "Általános Iskola"));
        long usersSizeBeforeRegistration = userRepository.count();
        mockMvc.perform(post(BASE_URL + "/register/student").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().is4xxClientError())
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", Is.is("invalidObject")));
        long usersSizeAfterRegistration = userRepository.count();
        Assertions.assertEquals(usersSizeBeforeRegistration, usersSizeAfterRegistration, "");
    }

    @Test
    @DisplayName("Registration with invalid gender. Gender isn't equal to male, female or other.")
    public void registerWithInvalidGender() throws Exception {
        Users newUser = new Users("registerStudent1", "registerStudent1", "register@gmail.com", "06707412356", new Date(), "non-binary", "test5.Asd", new Education(1, "Általános Iskola"));
        long usersSizeBeforeRegistration = userRepository.count();
        mockMvc.perform(post(BASE_URL + "/register/student").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().is4xxClientError())
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", Is.is("invalidGender")));
        long usersSizeAfterRegistration = userRepository.count();
        Assertions.assertEquals(usersSizeBeforeRegistration, usersSizeAfterRegistration, "");
    }

    @Test
    @DisplayName("Registration with invalid e-mail.")
    public void registerWithInvalidEmail() throws Exception {
        Users newUser = new Users("registerStudent1", "registerStudent1", "registergmail.com", "06707412356", new Date(), "male", "test5.Asd", new Education(1, "Általános Iskola"));
        long usersSizeBeforeRegistration = userRepository.count();
        mockMvc.perform(post(BASE_URL + "/register/student").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().is4xxClientError())
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", Is.is("invalidEmail")));
        long usersSizeAfterRegistration = userRepository.count();
        Assertions.assertEquals(usersSizeBeforeRegistration, usersSizeAfterRegistration, "");

    }

    @Test
    @DisplayName("Registration with invalid phone.")
    public void registerWithInvalidPhone() throws Exception {
        Users newUser = new Users("registerStudent1", "registerStudent1", "register@gmail.com", "06787412356", new Date(), "male", "test5.Asd", new Education(1, "Általános Iskola"));
        long usersSizeBeforeRegistration = userRepository.count();
        mockMvc.perform(post(BASE_URL + "/register/student").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().is4xxClientError())
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", Is.is("invalidPhone")));
        long usersSizeAfterRegistration = userRepository.count();
        Assertions.assertEquals(usersSizeBeforeRegistration, usersSizeAfterRegistration, "");
    }

    @Test
    @DisplayName("Registration with invalid password.")
    public void registerWithInvalidPassword() throws Exception {
        Users newUser = new Users("registerStudent1", "registerStudent1", "register@gmail.com", "06707412356", new Date(), "male", "tes.Asd", new Education(1, "Általános Iskola"));
        long usersSizeBeforeRegistration = userRepository.count();
        mockMvc.perform(post(BASE_URL + "/register/student").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().is4xxClientError())
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", Is.is("invalidPassword")));
        long usersSizeAfterRegistration = userRepository.count();
        Assertions.assertEquals(usersSizeBeforeRegistration, usersSizeAfterRegistration, "");
    }

    @Test
    @DisplayName("Registration with invalid birthDate. It must be in the past.")
    public void registerWithInvalidBirthDate() throws Exception {
        LocalDate birthDate = LocalDate.now().plusYears(2);
        Users newUser = new Users("registerStudent1", "registerStudent1", "register@gmail.com", "06707412356", Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant()), "male", "test5.Asd", new Education(1, "Általános Iskola"));
        long usersSizeBeforeRegistration = userRepository.count();
        mockMvc.perform(post(BASE_URL + "/register/student").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().is4xxClientError())
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", Is.is("invalidBirthDate")));
        long usersSizeAfterRegistration = userRepository.count();
        Assertions.assertEquals(usersSizeBeforeRegistration, usersSizeAfterRegistration, "");
    }

    @Test
    @DisplayName("Registration with non-existent education")
    public void registerWithNonExistentEducation() throws Exception {
        Users newUser = new Users("registerStudent1", "registerStudent1", "register@gmail.com", "06707412356", new Date(), "male", "test5.Asd", new Education(432, "Általános Iskola"));
        long usersSizeBeforeRegistration = userRepository.count();
        mockMvc.perform(post(BASE_URL + "/register/student").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().is4xxClientError())
                .andExpect(status().isNotFound())
                .andExpect(status().is(404))
                .andExpect(jsonPath("$", Is.is("educationNotFound")));
        long usersSizeAfterRegistration = userRepository.count();
        Assertions.assertEquals(usersSizeBeforeRegistration, usersSizeAfterRegistration, "");
    }

    @Test
    @DisplayName("Registration with registered e-mail.")
    public void registerWithDuplicatedEmail() throws Exception {
        Users newUser = new Users("registerStudent1", "registerStudent1", "test@gmail.com", "06707412356", new Date(), "male", "test5.Asd", new Education(1, "Általános Iskola"));
//        long usersSizeBeforeRegistration = userRepository.count();
        mockMvc.perform(post(BASE_URL + "/register/student").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().is4xxClientError())
                .andExpect(status().is(409))
                .andExpect(jsonPath("$", Is.is("duplicateEmail")));
//        long usersSizeAfterRegistration = userRepository.count();
//        Assertions.assertEquals(usersSizeBeforeRegistration, usersSizeAfterRegistration, "");
    }

    @Test
    @DisplayName("Registration with registered phone.")
    public void registerWithDuplicatedPhone() throws Exception {
        Users newUser = new Users("registerStudent1", "registerStudent1", "register@gmail.com", "06701111111", new Date(), "male", "test5.Asd", new Education(1, "Általános Iskola"));
//        long usersSizeBeforeRegistration = userRepository.count();
        mockMvc.perform(post(BASE_URL + "/register/student").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().is4xxClientError())
                .andExpect(status().is(409))
                .andExpect(jsonPath("$", Is.is("duplicatePhone")));
//        long usersSizeAfterRegistration = userRepository.count();
//        Assertions.assertEquals(usersSizeBeforeRegistration, usersSizeAfterRegistration, "");
    }

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
