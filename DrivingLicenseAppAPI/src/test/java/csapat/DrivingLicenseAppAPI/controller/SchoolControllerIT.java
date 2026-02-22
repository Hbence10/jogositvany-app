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
import lombok.RequiredArgsConstructor;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    private Integer testSchoolId;
    private Integer testJoinRequestId;
    private String BASEURL = "http://localhost:8080/school";

    @BeforeEach
    public void setup() {
        Users owner = userRepository.save(new Users("testUser1", "registerStudent1", "test@gmail.com", "06701111111", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));
        Users testUser = userRepository.save(new Users("testUser2", "registerStudent2", "test2@gmail.com", "06701111112", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));
        Users testInstructor = userRepository.save(new Users("testUser2", "registerStudent2", "test2@gmail.com", "06701111112", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));

        School testSchool = schoolRepository.save(new School("schoolName", "schoolTest@gmail.com", "06706894719", "Tolna", "Dombóvár", "sfafsafasf", "afsfassaf", owner));
        testSchoolId = testSchool.getId();


        SchoolJoinRequest testRequest = schoolJoinRequestRepository.save(new SchoolJoinRequest(testUser, testSchool, drivingLicenseCategoryRepository.findById(1).get()));
        testUser.setSchoolJoinRequestList(new ArrayList<>(Arrays.asList(testRequest)));
        userRepository.save(testUser);

        testJoinRequestId = testRequest.getId();
        System.out.println(testJoinRequestId);
    }

    @Test
    @DisplayName("Accept existent join Request from user")
    public void acceptExistentJoinRequestFromUser() throws Exception {
        mockMvc.perform(post(BASEURL + "/" + testJoinRequestId + "/joinRequest").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createBodyForJoinRequestHandling("accept"))))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Refuse existent join")
    public void refuseExistentJoinRequestFromUser() throws Exception {

    }

    @Test
    @DisplayName("")
    public void acceptExistentJoinRequestFromInstructor() throws Exception {

    }

    @Test
    @DisplayName("")
    public void refuseExistentJoinRequestFromInstructor() throws Exception {

    }

    @Test
    @DisplayName("")
    public void handleJoinRequestWithInvalidStatus() throws Exception {
    }

    @Test
    @DisplayName("")
    public void handleNonExistentJoinRequest() throws Exception {
    }

    private JsonNode createBodyForJoinRequestHandling(String status) {
        JsonNode returnObject = objectMapper.createObjectNode();
        ((ObjectNode) returnObject).put("status", status);

        return returnObject;
    }

    @Test
    @DisplayName("")
    public void updateExistentSchoolWithValidDatas() throws Exception {
    }

    @Test
    @DisplayName("")
    public void updateNonExistentSchool() throws Exception {
    }

    @Test
    @DisplayName("")
    public void updateExistentSchoolWithInvalidPhone() throws Exception {
    }

    @Test
    @DisplayName("")
    public void updateExistentSchoolWithInvalidEmail() throws Exception {
    }

    @Test
    @DisplayName("")
    public void updateExistentSchoolWithDuplicatedName() throws Exception {
    }

    @Test
    @DisplayName("")
    public void updateExistentSchoolWithDuplicatedPhone() throws Exception {
    }

    @Test
    @DisplayName("")
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

    @Test
    @DisplayName("")
    public void getAllJoinRequestOfExistentSchool() throws Exception {
    }

    @Test
    @DisplayName("")
    public void getAllJoinRequestOfNonExistentSchool() throws Exception {
    }

    @Test
    @DisplayName("")
    public void deleteExistentSchool() throws Exception {
    }

    @Test
    @DisplayName("")
    public void deleteNonExistentSchool() throws Exception {
    }

    @Test
    @DisplayName("")
    public void searchSchoolsByExistentTown() throws Exception {
    }

    @Test
    @DisplayName("")
    public void searchSchoolByNonExistentTown() throws Exception {
    }

    @Test
    @DisplayName("")
    public void getExistentSchoolById() throws Exception {
    }

    @Test
    @DisplayName("")
    public void getNonExistentSchoolById() throws Exception {
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

