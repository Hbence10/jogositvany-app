package csapat.DrivingLicenseAppAPI.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import csapat.DrivingLicenseAppAPI.entity.Education;
import csapat.DrivingLicenseAppAPI.entity.School;
import csapat.DrivingLicenseAppAPI.entity.SchoolJoinRequest;
import csapat.DrivingLicenseAppAPI.entity.Users;
import csapat.DrivingLicenseAppAPI.repository.DrivingLicenseCategoryRepository;
import csapat.DrivingLicenseAppAPI.repository.SchoolJoinRequestRepository;
import csapat.DrivingLicenseAppAPI.repository.SchoolRepository;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

//37
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource(locations = "classpath:test-application.properties")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class SchoolControllerIT {

    MockMvc mockMvc;
    private final SchoolRepository schoolRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SchoolJoinRequestRepository schoolJoinRequestRepository;
    private final DrivingLicenseCategoryRepository drivingLicenseCategoryRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public SchoolControllerIT(MockMvc mockMvc, SchoolRepository schoolRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, SchoolJoinRequestRepository schoolJoinRequestRepository, DrivingLicenseCategoryRepository drivingLicenseCategoryRepository, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.schoolRepository = schoolRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.schoolJoinRequestRepository = schoolJoinRequestRepository;
        this.drivingLicenseCategoryRepository = drivingLicenseCategoryRepository;
        this.objectMapper = objectMapper;
    }

    private Long testSchoolId;
    private Long testJoinRequestId;
    private String BASEURL = "http://localhost:8080/school";

    @BeforeEach
    public void setup() {
        Users owner = userRepository.save(new Users("testUser1", "registerStudent1", "test@gmail.com", "06701111111", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));
        Users testUser = userRepository.save(new Users("testUser2", "registerStudent2", "test2@gmail.com", "06701111112", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));
        Users testInstructor = userRepository.save(new Users("testUser2", "registerStudent2", "test3@gmail.com", "06701111113", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));

        School testSchool = schoolRepository.save(new School("schoolName", "schoolTest@gmail.com", "06706894719", "Tolna", "Nagykónyi", "sfafsafasf", "afsfassaf", owner));
        testSchoolId = testSchool.getId();


        SchoolJoinRequest testRequest = schoolJoinRequestRepository.save(new SchoolJoinRequest(testUser, testSchool, drivingLicenseCategoryRepository.findById(1L).get()));
        testUser.setSchoolJoinRequestList(new ArrayList<>(Arrays.asList(testRequest)));
        userRepository.save(testUser);

        testJoinRequestId = testRequest.getId();
    }

    @Test
    @DisplayName("Accept existent join Request from user")
    public void acceptExistentJoinRequestFromUser() throws Exception {
        mockMvc.perform(post(BASEURL + "/" + testJoinRequestId + "/joinRequest").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createBodyForJoinRequestHandling("accept"))))
                .andExpect(status().isOk());

        Assertions.assertEquals(true, schoolJoinRequestRepository.findById(testJoinRequestId).get().getIsAccepted(), "It should be accepted");
    }

    @Test
    @DisplayName("Refuse existent join request from user")
    public void refuseExistentJoinRequestFromUser() throws Exception {
        mockMvc.perform(post(BASEURL + "/" + testJoinRequestId + "/joinRequest").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createBodyForJoinRequestHandling("refuse"))))
                .andExpect(status().isOk());

        Assertions.assertEquals(false, schoolJoinRequestRepository.findById(testJoinRequestId).get().getIsAccepted(), "It should be refused");
    }

    @Test
    @DisplayName("Handle existent request with invalid status")
    public void handleJoinRequestWithInvalidStatus() throws Exception {
        mockMvc.perform(post(BASEURL + "/" + testJoinRequestId + "/joinRequest").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createBodyForJoinRequestHandling("asfas"))))
                .andExpect(status().is4xxClientError())
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", Is.is("invalidStatus")))
        ;
    }

    @Test
    @DisplayName("Handle non-existent request")
    public void handleNonExistentJoinRequest() throws Exception {
        mockMvc.perform(post(BASEURL + "/" + 3214124 + "/joinRequest").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createBodyForJoinRequestHandling("accept"))))
                .andExpect(status().isNotFound())
        ;
    }

    private JsonNode createBodyForJoinRequestHandling(String status) {
        JsonNode returnObject = objectMapper.createObjectNode();
        ((ObjectNode) returnObject).put("status", status);

        return returnObject;
    }

    @Test
    @DisplayName("Update existent school with existent datas")
    public void updateExistentSchoolWithValidDatas() throws Exception {
    }

    @Test
    @DisplayName("Update none existent school.")
    public void updateNonExistentSchool() throws Exception {
    }

    @Test
    @DisplayName("Update existent school with invalid phone")
    public void updateExistentSchoolWithInvalidPhone() throws Exception {
    }

    @Test
    @DisplayName("Update existent school with invalid email")
    public void updateExistentSchoolWithInvalidEmail() throws Exception {
    }

    @Test
    @DisplayName("Update existent school with duplicated name")
    public void updateExistentSchoolWithDuplicatedName() throws Exception {
    }

    @Test
    @DisplayName("Update existent school with duplicated phone")
    public void updateExistentSchoolWithDuplicatedPhone() throws Exception {
    }

    @Test
    @DisplayName("Update existent school with duplicated email")
    public void updateExistentSchoolWithDuplicatedEmail() throws Exception {
    }

    //coverImg
    @Test
    @DisplayName("")
    public void updateExistentSchoolsCoverImgWithValidPhoto() throws Exception {
    }

    @Test
    @DisplayName("")
    public void updateNonExistentSchoolsCoverImg() throws Exception {
    }

    @Test
    @DisplayName("")
    public void updateExistentSchoolsCoverImgWithInvalidPhoto() throws Exception {
    }

    @Test
    @DisplayName("")
    public void updateOpeningDetailsOfExistentSchool() throws Exception {
    }

    @Test
    @DisplayName("")
    public void updateOpeningDetailsOfNonExistentSchool() throws Exception {
    }

    @Test
    @DisplayName("")
    public void updateOpeningDetailsOfExistentSchoolWithInvalidDateFormat() throws Exception {
    }

    public JsonNode createRequestBodyForUpdate() {
        return null;
    }

    @Test
    @DisplayName("Get all join request of existent school")
    public void getAllJoinRequestOfExistentSchool() throws Exception {
        mockMvc.perform(get(BASEURL + "/" + testSchoolId + "/joinRequests"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @DisplayName("Get all join request of non existent school")
    public void getAllJoinRequestOfNonExistentSchool() throws Exception {
        mockMvc.perform(get(BASEURL + "/" + (testSchoolId + 1) + "/joinRequests"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Delete existent user by id")
    public void deleteExistentSchool() throws Exception {
        Long sizeBeforeDelete = schoolRepository.countNotDeletedSchool(false);

        mockMvc.perform(delete(BASEURL + "/" + testSchoolId).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createBodyForJoinRequestHandling("accept"))))
                .andExpect(status().isOk())
        ;

        Long sizeAfterDelete = schoolRepository.countNotDeletedSchool(false);
        Assertions.assertEquals(sizeBeforeDelete - 1, sizeAfterDelete);
    }

    @Test
    @DisplayName("Delete non-existent user")
    public void deleteNonExistentSchool() throws Exception {
        Long sizeBeforeDelete = schoolRepository.countNotDeletedSchool(false);

        mockMvc.perform(delete(BASEURL + "/" + (testSchoolId + 1)).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createBodyForJoinRequestHandling("accept"))))
                .andExpect(status().isNotFound())
        ;

        Long sizeAfterDelete = schoolRepository.countNotDeletedSchool(false);
        Assertions.assertEquals(sizeBeforeDelete, sizeAfterDelete);
    }

    @Test
    @DisplayName("Search school by registered town")
    public void searchSchoolsByExistentTown() throws Exception {
        mockMvc.perform(get(BASEURL + "/search?town=Nagykónyi"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
        ;
    }

    @Test
    @DisplayName("Search school by non registered town")
    public void searchSchoolByNonExistentTown() throws Exception {
        mockMvc.perform(get(BASEURL + "/search?town=dasdasda"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)))
        ;
    }

    @Test
    @DisplayName("Get Existent school by id")
    public void getExistentSchoolById() throws Exception {
        mockMvc.perform(get(BASEURL + "/" + testSchoolId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Is.is(Integer.valueOf(testSchoolId + ""))));
    }

    @Test
    @DisplayName("")
    public void getNonExistentSchoolById() throws Exception {
        mockMvc.perform(get(BASEURL + "/" + (testSchoolId + 1)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("")
    public void createSchoolWithValidDatas() throws Exception {
    }

    @Test
    @DisplayName("")
    public void createSchoolWithInvalidObject() throws Exception {
    }

    @Test
    @DisplayName("")
    public void createSchoolWithInvalidEmail() throws Exception {
    }

    @Test
    @DisplayName("")
    public void createSchoolWithInvalidPhone() throws Exception {
    }

    public JsonNode createRequestBodyForSchoolCreation() {
        return null;
    }

    @Test
    @DisplayName("")
    public void createSchoolWithNonExistentTown() throws Exception {
    }

    @Test
    @DisplayName("")
    public void getAllStudentsOfExistentSchool() throws Exception {

    }

    @Test
    @DisplayName("")
    public void getAllInstructorOfExistentSchool() throws Exception {
    }

    @Test
    @DisplayName("")
    public void getAllMemberOfNonExistentSchool() throws Exception {
    }

    @Test
    @DisplayName("")
    public void getAllMemberOfSchoolByInvalidRole() throws Exception {
    }

    @Test
    @DisplayName("")
    public void kickoutExistentInstructor() throws Exception {
    }

    @Test
    @DisplayName("")
    public void kickoutNonExistentInstructor() throws Exception {
    }

    @Test
    @DisplayName("")
    public void getAllSchool() throws Exception {
    }
}

