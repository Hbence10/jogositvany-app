package csapat.DrivingLicenseAppAPI.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import csapat.DrivingLicenseAppAPI.entity.Education;
import csapat.DrivingLicenseAppAPI.entity.Users;
import csapat.DrivingLicenseAppAPI.repository.InstructorRepository;
import csapat.DrivingLicenseAppAPI.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    Long testUserId;

    @BeforeEach
    public void setup() {
        Users newUser = userRepository.save(new Users("testUser1", "registerStudent1", "test@gmail.com", "06701111111", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));
        testUserId = newUser.getId();
    }

    @Test
    @DisplayName("Login with existent e-mail and password combination.")
    public void loginWithExistentUsersDetails() throws Exception {
        JsonNode requestBody = createLoginBody("test@gmail.com", "test5.Asd");
        mockMvc.perform(post(BASE_URL + "/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(Integer.valueOf(testUserId + ""))));
    }

    @Test
    @DisplayName("Login with non-existent e-mail.")
    public void loginWithNonExistentEmail() throws Exception {
        JsonNode requestBody = createLoginBody("bzhalmaii@gmail.com", "test5.Asd");
        mockMvc.perform(post(BASE_URL + "/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("userNotFound")));
    }

    @Test
    @DisplayName("Login with existent e-mail and bad password.")
    public void loginWithBadPassword() throws Exception {
        JsonNode requestBody = createLoginBody("bzhalmai@gmail.com", "ads14fa");
        mockMvc.perform(post(BASE_URL + "/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("userNotFound")));
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
        Users newUser = new Users("registerStudent1", "registerStudent1", "register@gmail.com", "06707412356", new Date(), "male", "test5.Asd", new Education(1L, "Általános Iskola"));
        long usersSizeBeforeRegistration = userRepository.count();

        mockMvc.perform(post(BASE_URL + "/register/student").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk());

        long usersSizeAfterRegistration = userRepository.count();
        assertEquals(usersSizeBeforeRegistration + 1, usersSizeAfterRegistration, "");

    }

    @Test
    @DisplayName("Registration with valid datas as instructor.")
    public void registerWithValidDatasAsInstructor() throws Exception {
        Users newUser = new Users("registerStudent1", "registerStudent1", "register@gmail.com", "06707412356", new Date(), "male", "test5.Asd", new Education(1L, "Általános Iskola"));
        long usersSizeBeforeRegistration = userRepository.count();
        long instructorsSizeBeforeRegistration = instructorRepository.count();

        mockMvc.perform(post(BASE_URL + "/register/instructor").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk());

        long usersSizeAfterRegistration = userRepository.count();
        long instructorsSizeAfterRegistration = instructorRepository.count();

        assertEquals(usersSizeBeforeRegistration + 1, usersSizeAfterRegistration, "");
        assertEquals(instructorsSizeBeforeRegistration + 1, instructorsSizeAfterRegistration, "");
    }

    @Test
    @DisplayName("Registration with non-existent role.")
    public void registerAsNonExistentRole() throws Exception {
        Users newUser = new Users("registerStudent1", "registerStudent1", "register@gmail.com", "06707412356", new Date(), "male", "test5.Asd", new Education(1L, "Általános Iskola"));
        long usersSizeBeforeRegistration = userRepository.count();
        mockMvc.perform(post(BASE_URL + "/register/admin").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().is4xxClientError())
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", is("invalidParameter")));
        long usersSizeAfterRegistration = userRepository.count();
        assertEquals(usersSizeBeforeRegistration, usersSizeAfterRegistration, "");
    }

    @Test
    @DisplayName("Registration with invalid Users object. (id attribute isn't null)")
    public void registerWithInvalidObject() throws Exception {
        Users newUser = new Users(4132L, "registerStudent1", "registerStudent1", "register@gmail.com", "06707412356", new Date(), "male", "test5.Asd", new Education(1L, "Általános Iskola"));
        long usersSizeBeforeRegistration = userRepository.count();
        mockMvc.perform(post(BASE_URL + "/register/student").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().is4xxClientError())
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", is("invalidObject")));
        long usersSizeAfterRegistration = userRepository.count();
        assertEquals(usersSizeBeforeRegistration, usersSizeAfterRegistration, "");
    }

    @Test
    @DisplayName("Registration with invalid gender. Gender isn't equal to male, female or other.")
    public void registerWithInvalidGender() throws Exception {
        Users newUser = new Users("registerStudent1", "registerStudent1", "register@gmail.com", "06707412356", new Date(), "non-binary", "test5.Asd", new Education(1L, "Általános Iskola"));
        long usersSizeBeforeRegistration = userRepository.count();
        mockMvc.perform(post(BASE_URL + "/register/student").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().is4xxClientError())
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", is("invalidGender")));
        long usersSizeAfterRegistration = userRepository.count();
        assertEquals(usersSizeBeforeRegistration, usersSizeAfterRegistration, "");
    }

    @Test
    @DisplayName("Registration with invalid e-mail.")
    public void registerWithInvalidEmail() throws Exception {
        Users newUser = new Users("registerStudent1", "registerStudent1", "registergmail.com", "06707412356", new Date(), "male", "test5.Asd", new Education(1L, "Általános Iskola"));
        long usersSizeBeforeRegistration = userRepository.count();
        mockMvc.perform(post(BASE_URL + "/register/student").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().is4xxClientError())
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", is("invalidEmail")));
        long usersSizeAfterRegistration = userRepository.count();
        assertEquals(usersSizeBeforeRegistration, usersSizeAfterRegistration, "");

    }

    @Test
    @DisplayName("Registration with invalid phone.")
    public void registerWithInvalidPhone() throws Exception {
        Users newUser = new Users("registerStudent1", "registerStudent1", "register@gmail.com", "06787412356", new Date(), "male", "test5.Asd", new Education(1L, "Általános Iskola"));
        long usersSizeBeforeRegistration = userRepository.count();
        mockMvc.perform(post(BASE_URL + "/register/student").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().is4xxClientError())
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", is("invalidPhone")));
        long usersSizeAfterRegistration = userRepository.count();
        assertEquals(usersSizeBeforeRegistration, usersSizeAfterRegistration, "");
    }

    @Test
    @DisplayName("Registration with invalid password.")
    public void registerWithInvalidPassword() throws Exception {
        Users newUser = new Users("registerStudent1", "registerStudent1", "register@gmail.com", "06707412356", new Date(), "male", "tes.Asd", new Education(1L, "Általános Iskola"));
        long usersSizeBeforeRegistration = userRepository.count();
        mockMvc.perform(post(BASE_URL + "/register/student").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().is4xxClientError())
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", is("invalidPassword")));
        long usersSizeAfterRegistration = userRepository.count();
        assertEquals(usersSizeBeforeRegistration, usersSizeAfterRegistration, "");
    }

    @Test
    @DisplayName("Registration with invalid birthDate. It must be in the past.")
    public void registerWithInvalidBirthDate() throws Exception {
        LocalDate birthDate = LocalDate.now().plusYears(2);
        Users newUser = new Users("registerStudent1", "registerStudent1", "register@gmail.com", "06707412356", Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant()), "male", "test5.Asd", new Education(1L, "Általános Iskola"));
        long usersSizeBeforeRegistration = userRepository.count();
        mockMvc.perform(post(BASE_URL + "/register/student").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().is4xxClientError())
                .andExpect(status().is(415))
                .andExpect(jsonPath("$.statusText", is("invalidDate")));
        long usersSizeAfterRegistration = userRepository.count();
        assertEquals(usersSizeBeforeRegistration, usersSizeAfterRegistration, "");
    }

    @Test
    @DisplayName("Registration with non-existent education")
    public void registerWithNonExistentEducation() throws Exception {
        Users newUser = new Users("registerStudent1", "registerStudent1", "register@gmail.com", "06707412356", new Date(), "male", "test5.Asd", new Education(432L, "Általános Iskola"));
        long usersSizeBeforeRegistration = userRepository.count();
        mockMvc.perform(post(BASE_URL + "/register/student").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().is4xxClientError())
                .andExpect(status().isNotFound())
                .andExpect(status().is(404))
                .andExpect(jsonPath("$", is("educationNotFound")));
        long usersSizeAfterRegistration = userRepository.count();
        assertEquals(usersSizeBeforeRegistration, usersSizeAfterRegistration, "");
    }

    @Test
    @DisplayName("Registration with registered e-mail.")
    public void registerWithDuplicatedEmail() throws Exception {
        Users newUser = new Users("registerStudent1", "registerStudent1", "test@gmail.com", "06707412356", new Date(), "male", "test5.Asd", new Education(1L, "Általános Iskola"));
        long usersSizeBeforeRegistration = userRepository.count();
        mockMvc.perform(post(BASE_URL + "/register/student").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().is4xxClientError())
                .andExpect(status().is(409))
                .andExpect(jsonPath("$.statusText", is("duplicateEmail")));
        long usersSizeAfterRegistration = userRepository.count();
        assertEquals(usersSizeBeforeRegistration, usersSizeAfterRegistration, "");
    }

    @Test
    @DisplayName("Registration with registered phone.")
    public void registerWithDuplicatedPhone() throws Exception {
        Users newUser = new Users("registerStudent1", "registerStudent1", "register@gmail.com", "06701111111", new Date(), "male", "test5.Asd", new Education(1L, "Általános Iskola"));
        long usersSizeBeforeRegistration = userRepository.count();
        mockMvc.perform(post(BASE_URL + "/register/student").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().is4xxClientError())
                .andExpect(status().is(409))
                .andExpect(jsonPath("$.statusText", is("duplicatePhone")));
        long usersSizeAfterRegistration = userRepository.count();
        assertEquals(usersSizeBeforeRegistration, usersSizeAfterRegistration, "");
    }

    @Test
    @DisplayName("Get verification code for password reset with existent and valid e-mail.")
    public void getVerificationCodeWithExistentEmail() throws Exception {
        mockMvc.perform(get(BASE_URL + "/getVerificationCode?email={wantedEmail}", "test@gmail.com"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Get verification code for password reset with invalid e-mail.")
    public void getVerificationCodeWithInvalidEmail() throws Exception {
        mockMvc.perform(get(BASE_URL + "/getVerificationCode?email={wantedEmail}", "testmail.com"))
                .andExpect(status().is4xxClientError())
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", is("invalidEmail")));
    }

    @Test
    @DisplayName("Get verification code for password reset with non-existent e-mail.")
    public void getVerificationCodeWithNonExistentEmail() throws Exception {
        mockMvc.perform(get(BASE_URL + "/getVerificationCode?email={wantedEmail}", "passwordReset@gmail.com"))
                .andExpect(status().isNotFound())
                .andExpect(status().is(404))
                .andExpect(jsonPath("$", is("emailNotFound")));
    }

    @Test
    @DisplayName("Check good verification code with existent e-mail.")
    public void checkGoodVerificationCode() throws Exception {
        JsonNode requestBody = createRequestBodyForVCodeCheck("test@gmail.com", "aaaaaaaaaa");
        mockMvc.perform(post(BASE_URL + "/checkVerificationCode").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)));

    }

    @Test
    @DisplayName("Check bad verification code with existent e-mail.")
    public void checkBadVerificationCode() throws Exception {
        JsonNode requestBody = createRequestBodyForVCodeCheck("test@gmail.com", "aaaaaaaaa1");
        mockMvc.perform(post(BASE_URL + "/checkVerificationCode").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(false)));
    }

    @Test
    @DisplayName("Check invalid verification code with existent e-mail.")
    public void checkInvalidVerificationCode() throws Exception {
        JsonNode requestBody = createRequestBodyForVCodeCheck("test@gmail.com", "aaaaaaaaa");
        mockMvc.perform(post(BASE_URL + "/checkVerificationCode").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().is4xxClientError())
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", is("invalidVerificationCode")));
    }

    @Test
    @DisplayName("Check good verification code with non-existent e-mail.")
    public void checkVerificationCodeWithNonExistentEmail() throws Exception {
        JsonNode requestBody = createRequestBodyForVCodeCheck("checkVCode@gmail.com", "aaaaaaaaaa");
        mockMvc.perform(post(BASE_URL + "/checkVerificationCode").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isNotFound())
                .andExpect(status().is(404))
                .andExpect(jsonPath("$", is("userNotFound")));
    }

    public JsonNode createRequestBodyForVCodeCheck(String email, String vCode) {
        JsonNode returnObject = objectMapper.createObjectNode();
        ((ObjectNode) returnObject).put("email", email);
        ((ObjectNode) returnObject).put("vCode", vCode);
        return returnObject;
    }

    @Test
    @DisplayName("Update existent user's password")
    public void updatePasswordWithValidDatas() throws Exception {
        JsonNode requestBody = createRequestBodyForPasswordReset("test@gmail.com", "test5.Asd");
        mockMvc.perform(patch(BASE_URL + "/passwordReset").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Update non-existent user's password")
    public void updatePasswordWithNonExistentEmail() throws Exception {
        JsonNode requestBody = createRequestBodyForPasswordReset("passwordUpdateTest@gmail.com", "test5.Asd");
        mockMvc.perform(patch(BASE_URL + "/passwordReset").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isNotFound())
                .andExpect(status().is(404))
                .andExpect(jsonPath("$", is("userNotFound")));
    }

    @Test
    @DisplayName("Update user's password with invalid password")
    public void updatePasswordWithInvalidPassword() throws Exception {
        JsonNode requestBody = createRequestBodyForPasswordReset("test@gmail.com", "tes.Asd");
        mockMvc.perform(patch(BASE_URL + "/passwordReset").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().is4xxClientError())
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", is("invalidPassword")));
    }

    @Test
    @DisplayName("Update user's password with valid password.")
    public void updatePasswordWithInvalidEmail() throws Exception {
        JsonNode requestBody = createRequestBodyForPasswordReset("tesgmail.com", "test5.Asd");
        mockMvc.perform(patch(BASE_URL + "/passwordReset").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().is4xxClientError())
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", is("invalidEmail")));
    }

    public JsonNode createRequestBodyForPasswordReset(String email, String newPassword) {
        JsonNode returnObject = objectMapper.createObjectNode();
        ((ObjectNode) returnObject).put("email", email);
        ((ObjectNode) returnObject).put("newPassword", newPassword);
        return returnObject;
    }

    @Test
    @DisplayName("Update existent user with valid datas.")
    public void updateExistentUserWithValidDatas() throws Exception {
        JsonNode requestBody = createRequestBodyForUpdate("testUserUpdate1", "registerStudent1", "test@gmail.com", "06701111111", "2022-08-02", "male", 1);
        mockMvc.perform(put(BASE_URL + "/" + testUserId).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(Integer.valueOf(testUserId + ""))))
                .andExpect(jsonPath("$.firstName", is("testUserUpdate1")))
                .andExpect(jsonPath("$.lastName", is("registerStudent1")))
                .andExpect(jsonPath("$.email", is("test@gmail.com")))
                .andExpect(jsonPath("$.phone", is("06701111111")))
                .andExpect(jsonPath("$.birthDate", is("2022-08-01T22:00:00.000+00:00")))
                .andExpect(jsonPath("$.gender", is("male")))
                .andExpect(jsonPath("$.userEducation.id", is(1)));
    }

    @Test
    @DisplayName("Update non existent user's data.")
    public void updateNonExistentUser() throws Exception {
        JsonNode requestBody = createRequestBodyForUpdate("testUserUpdate1", "registerStudent1", "test@gmail.com", "06701111111", "2022-08-02", "male", 1);
        mockMvc.perform(put(BASE_URL + "/" + 78978564).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isNotFound())
                .andExpect(status().is(404))
                .andExpect(jsonPath("$", is("userNotFound")));
    }

    @Test
    @DisplayName("Update user with invalid gender.")
    public void updateWithInvalidGender() throws Exception {
        JsonNode requestBody = createRequestBodyForUpdate("testUserUpdate1", "registerStudent1", "test@gmail.com", "06701111111", "2022-08-02", "non-binary", 1);
        mockMvc.perform(put(BASE_URL + "/" + testUserId).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", is("invalidGender")));
    }

    @Test
    @DisplayName("Update user with invalid e-mail.")
    public void updateWithInvalidEmail() throws Exception {
        JsonNode requestBody = createRequestBodyForUpdate("testUserUpdate1", "registerStudent1", "testmail.com", "06701111111", "2022-08-02", "male", 1);
        mockMvc.perform(put(BASE_URL + "/" + testUserId).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", is("invalidEmail")));
    }

    @Test
    @DisplayName("Update user with invalid phone.")
    public void updateWithInvalidPhone() throws Exception {
        JsonNode requestBody = createRequestBodyForUpdate("testUserUpdate1", "registerStudent1", "test@mail.com", "06351111111", "2022-08-02", "male", 1);
        mockMvc.perform(put(BASE_URL + "/" + testUserId).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", is("invalidPhone")));
    }

    @Test
    @DisplayName("Update user with invalid birth date.")
    public void updateWithInvalidBirthDate() throws Exception {
        JsonNode requestBody = createRequestBodyForUpdate("testUserUpdate1", "registerStudent1", "test@mail.com", "06701111111", "2029-08-02", "male", 1);
        mockMvc.perform(put(BASE_URL + "/" + testUserId).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().is(415))
                .andExpect(jsonPath("$.statusText", is("invalidDate")));
    }

    @Test
    @DisplayName("Update user with non-existent education.")
    public void updateWithNonExistentEducation() throws Exception {
        JsonNode requestBody = createRequestBodyForUpdate("testUserUpdate1", "registerStudent1", "test@mail.com", "06701111111", "2029-08-02", "male", 456);
        mockMvc.perform(put(BASE_URL + "/" + testUserId).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isNotFound())
                .andExpect(status().is(404))
                .andExpect(jsonPath("$", is("educationNotFound")));
    }

    public JsonNode createRequestBodyForUpdate(String firstName, String lastName, String email, String phone, String birthDate, String gender, Integer educationId) {
        JsonNode returnObject = objectMapper.createObjectNode();
        ((ObjectNode) returnObject).put("firstName", firstName);
        ((ObjectNode) returnObject).put("lastName", lastName);
        ((ObjectNode) returnObject).put("email", email);
        ((ObjectNode) returnObject).put("phone", phone);
        ((ObjectNode) returnObject).put("birthDate", birthDate);
        ((ObjectNode) returnObject).put("gender", gender);
        ((ObjectNode) returnObject).put("educationId", educationId);
        return returnObject;
    }

    @Test
    @DisplayName("Delete existent user.")
    public void deleteExistentUser() throws Exception {
        Long sizeBeforeDelete = userRepository.countNotDeletedUsers(false);
        mockMvc.perform(delete(BASE_URL + "/" + testUserId))
                .andExpect(status().isOk());
        Long sizeAfterDelete = userRepository.countNotDeletedUsers(false);
        assertEquals(sizeBeforeDelete - 1, sizeAfterDelete, "");
    }

    @Test
    @DisplayName("Delete non-existent user.")
    public void deleteNonExistentUser() throws Exception {
        Long sizeBeforeDelete = userRepository.countNotDeletedUsers(false);
        mockMvc.perform(delete(BASE_URL + "/" + 23132))
                .andExpect(status().isNotFound())
                .andExpect(status().is(404));
        Long sizeAfterDelete = userRepository.countNotDeletedUsers(false);
        assertEquals(sizeBeforeDelete, sizeAfterDelete, "");
    }

    @Test
    @DisplayName("Get existent user by id.")
    public void getExistentUserById() throws Exception {
        mockMvc.perform(get(BASE_URL + "/" + 124124124))
                .andExpect(status().isNotFound())
                .andExpect(status().is(404));
    }

    @Test
    @DisplayName("Get non-existent user by id.")
    public void getNonExistentUserById() throws Exception {
        mockMvc.perform(get(BASE_URL + "/" + testUserId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(Integer.valueOf(testUserId + ""))));
    }

    @Test
    public void getAllUser() throws Exception {
        long sizeOfAllUser = userRepository.countNotDeletedUsers(false);
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(Integer.valueOf(20))));
    }


}
