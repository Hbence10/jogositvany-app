package csapat.DrivingLicenseAppAPI.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import csapat.DrivingLicenseAppAPI.entity.*;
import csapat.DrivingLicenseAppAPI.repository.*;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.is;

//16db
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource(locations = "classpath:test-application.properties")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class RequestControllerIT {

    MockMvc mockMvc;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;
    private final SchoolRepository schoolRepository;
    private final PasswordEncoder passwordEncoder;
    private final DrivingLicenseCategoryRepository drivingLicenseCategoryRepository;
    private final ObjectMapper objectMapper;
    private final SchoolJoinRequestRepository schoolJoinRequestRepository;
    private final InstructorJoinRequestRepository instructorJoinRequestRepository;
    private final DrivingLessonRequestRepository drivingLessonRequestRepository;
    private final SchoolCategoryRepository schoolCategoryRepository;

    Long userId;
    Long studentId;
    Long schoolId;
    Long instructorId;
    Long secondInstructorId;
    private final String BASEURL = "http://localhost:8080/request";

    @Autowired
    public RequestControllerIT(SchoolCategoryRepository schoolCategoryRepository, InstructorJoinRequestRepository instructorJoinRequestRepository, DrivingLessonRequestRepository drivingLessonRequestRepository, SchoolJoinRequestRepository schoolJoinRequestRepository, ObjectMapper objectMapper, DrivingLicenseCategoryRepository drivingLicenseCategoryRepository, PasswordEncoder passwordEncoder, SchoolRepository schoolRepository, InstructorRepository instructorRepository, StudentRepository studentRepository, UserRepository userRepository, MockMvc mockMvc) {
        this.instructorJoinRequestRepository = instructorJoinRequestRepository;
        this.drivingLessonRequestRepository = drivingLessonRequestRepository;
        this.schoolJoinRequestRepository = schoolJoinRequestRepository;
        this.objectMapper = objectMapper;
        this.drivingLicenseCategoryRepository = drivingLicenseCategoryRepository;
        this.passwordEncoder = passwordEncoder;
        this.schoolRepository = schoolRepository;
        this.instructorRepository = instructorRepository;
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.mockMvc = mockMvc;
        this.schoolCategoryRepository = schoolCategoryRepository;
    }

    @BeforeEach
    public void setup() {
        //masodlagos
        Users secondInstructor = userRepository.save(new Users("testUser1", "registerStudent1", "test5@gmail.com", "06701111115", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));
        Users secondSchoolOwner = userRepository.save(new Users("testUser1", "registerStudent1", "tes6t@gmail.com", "06701111116", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));
        School secondTestSchool = schoolRepository.save(new School("schoolName2", "schoolTest2@gmail.com", "06706294711", "Tolna", "Nagykónyi", "sfafsafasf", "afsfassaf", secondSchoolOwner));
        Instructors secondTestInstructor = instructorRepository.save(new Instructors(secondTestSchool, secondInstructor));

        //elsodleges
        Users student = userRepository.save(new Users("testUser1", "registerStudent1", "test2@gmail.com", "06701111112", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));
        Users instructor = userRepository.save(new Users("testUser1", "registerStudent1", "test3@gmail.com", "06701111113", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));
        Users schoolOwner = userRepository.save(new Users("testUser1", "registerStudent1", "tes4t@gmail.com", "06701111114", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));
        Users user = userRepository.save(new Users("testUser1", "registerStudent1", "test1@gmail.com", "06701111111", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));

        School testSchool = schoolRepository.save(new School("schoolName", "schoolTest@gmail.com", "06706294719", "Tolna", "Nagykónyi", "sfafsafasf", "afsfassaf", schoolOwner));
        testSchool.setLicenseCategoryList(new ArrayList<>(Arrays.asList(
                schoolCategoryRepository.save(new SchoolCategory(0, drivingLicenseCategoryRepository.findById(1L).get(), testSchool))
        )));
        schoolRepository.save(testSchool);

        Instructors testInstructor = instructorRepository.save(new Instructors(testSchool, instructor));
        Students testStudent = studentRepository.save(new Students(student, testSchool, drivingLicenseCategoryRepository.findById(1L).get(), testInstructor));

        userId = user.getId();
        studentId = testStudent.getId();
        schoolId = testSchool.getId();
        instructorId = testInstructor.getId();
        secondInstructorId = secondTestInstructor.getId();
    }

    @Test
    @DisplayName("Send schoolJoin request with valid datas")
    public void sendSchoolJoinRequestWithValidDatas() throws Exception {
        Long sizeBeforeSending = schoolJoinRequestRepository.countNotDeletedSchoolJoinRequest(false);
        JsonNode requestBody = createRequestBodyForSendingSchoolJoinRequest(schoolId, userId, 1L);
        mockMvc.perform(post(BASEURL + "/school").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk());
        Long sizeAfterSending = schoolJoinRequestRepository.countNotDeletedSchoolJoinRequest(false);
        assertEquals(sizeBeforeSending + 1, sizeAfterSending, "");
    }

    @Test
    @DisplayName("Send schoolJoin request to non existent school")
    public void sendSchoolJoinRequestToNonExistentSchool() throws Exception {
        Long sizeBeforeSending = schoolJoinRequestRepository.countNotDeletedSchoolJoinRequest(false);
        JsonNode requestBody = createRequestBodyForSendingSchoolJoinRequest(schoolId + 1, userId, 1L);
        mockMvc.perform(post(BASEURL + "/school").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("schoolNotFound")));

        Long sizeAfterSending = schoolJoinRequestRepository.countNotDeletedSchoolJoinRequest(false);
        assertEquals(sizeBeforeSending, sizeAfterSending, "");
    }

    @Test
    @DisplayName("Send schoolJoin request with non existent user")
    public void sendSchoolJoinRequestWithNonExistentUser() throws Exception {
        Long sizeBeforeSending = schoolJoinRequestRepository.countNotDeletedSchoolJoinRequest(false);
        JsonNode requestBody = createRequestBodyForSendingSchoolJoinRequest(schoolId, userId + 1, 1L);
        mockMvc.perform(post(BASEURL + "/school").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("userNotFound")));

        Long sizeAfterSending = schoolJoinRequestRepository.countNotDeletedSchoolJoinRequest(false);
        assertEquals(sizeBeforeSending, sizeAfterSending, "");
    }

    @Test
    @DisplayName("Send schoolJoin request with non existent category")
    public void sendSchoolJoinRequestWithNonExistentCategory() throws Exception {
        Long sizeBeforeSending = schoolJoinRequestRepository.countNotDeletedSchoolJoinRequest(false);
        JsonNode requestBody = createRequestBodyForSendingSchoolJoinRequest(schoolId, userId, 3123L);
        mockMvc.perform(post(BASEURL + "/school").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("categoryNotFound")));

        Long sizeAfterSending = schoolJoinRequestRepository.countNotDeletedSchoolJoinRequest(false);
        assertEquals(sizeBeforeSending, sizeAfterSending, "");
    }

    @Test
    @DisplayName("Send schoolJoin request with non existent category in school")
    public void sendSchoolJoinRequestWithNonExistentCategoryInSchool() throws Exception {
        Long sizeBeforeSending = schoolJoinRequestRepository.countNotDeletedSchoolJoinRequest(false);
        JsonNode requestBody = createRequestBodyForSendingSchoolJoinRequest(schoolId, userId, 3L);
        mockMvc.perform(post(BASEURL + "/school").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", is("invalidCategory")));

        Long sizeAfterSending = schoolJoinRequestRepository.countNotDeletedSchoolJoinRequest(false);
        assertEquals(sizeBeforeSending, sizeAfterSending, "");
    }

    private JsonNode createRequestBodyForSendingSchoolJoinRequest(Long schoolId, Long userId, Long categoryId) {
        JsonNode returnObject = objectMapper.createObjectNode();
        ((ObjectNode) returnObject).put("schoolId", schoolId);
        ((ObjectNode) returnObject).put("userId", userId);
        ((ObjectNode) returnObject).put("categoryId", categoryId);
        return returnObject;
    }

    @Test
    @DisplayName("Send instructorJoin request with valid datas")
    public void sendInstructorJoinRequestWithValidDatas() throws Exception {
        Long sizeBeforeSending = instructorJoinRequestRepository.countNotDeletedInstructorJoinRequest(false);
        JsonNode requestBody = createRequestBodyForSendingInstructorJoinRequest(instructorId, studentId);
        mockMvc.perform(post(BASEURL + "/instructor").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk());
        Long sizeAfterSending = instructorJoinRequestRepository.countNotDeletedInstructorJoinRequest(false);
        assertEquals(sizeBeforeSending + 1, sizeAfterSending, "");
    }

    @Test
    @DisplayName("Send instructorJoin request to non existent instructor")
    public void sendInstructorJoinRequestToNonExistentInstructor() throws Exception {
        Long sizeBeforeSending = instructorJoinRequestRepository.countNotDeletedInstructorJoinRequest(false);
        JsonNode requestBody = createRequestBodyForSendingInstructorJoinRequest(instructorId + 1, studentId);
        mockMvc.perform(post(BASEURL + "/instructor").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("instructorNotFound")));
        Long sizeAfterSending = instructorJoinRequestRepository.countNotDeletedInstructorJoinRequest(false);
        assertEquals(sizeBeforeSending, sizeAfterSending, "");
    }

    @Test
    @DisplayName("Send instructorJoin request with non existent student")
    public void sendInstructorJoinRequestWithNonExistentStudent() throws Exception {
        Long sizeBeforeSending = instructorJoinRequestRepository.countNotDeletedInstructorJoinRequest(false);
        JsonNode requestBody = createRequestBodyForSendingInstructorJoinRequest(instructorId, studentId + 1);
        mockMvc.perform(post(BASEURL + "/instructor").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("studentNotFound")));
        Long sizeAfterSending = instructorJoinRequestRepository.countNotDeletedInstructorJoinRequest(false);
        assertEquals(sizeBeforeSending, sizeAfterSending, "");
    }

    @Test
    @DisplayName("Send instructorJoin request with invalid instructor")
    public void sendInstructorJoinRequestWithInvalidInstructor() throws Exception {
        Long sizeBeforeSending = instructorJoinRequestRepository.countNotDeletedInstructorJoinRequest(false);
        JsonNode requestBody = createRequestBodyForSendingInstructorJoinRequest(secondInstructorId, studentId);
        mockMvc.perform(post(BASEURL + "/instructor").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", is("invalidInstructor")));
        Long sizeAfterSending = instructorJoinRequestRepository.countNotDeletedInstructorJoinRequest(false);
        assertEquals(sizeBeforeSending, sizeAfterSending, "");
    }

    private JsonNode createRequestBodyForSendingInstructorJoinRequest(Long instructorId, Long studentId) {
        JsonNode returnObject = objectMapper.createObjectNode();
        ((ObjectNode) returnObject).put("studentId", studentId);
        ((ObjectNode) returnObject).put("instructorId", instructorId);
        return returnObject;
    }

    @Test
    @DisplayName("Send drivingLesson request with valid datas")
    public void sendDrivingLessonRequestWithValidDatas() throws Exception {
        Long sizeBeforeSending = drivingLessonRequestRepository.countNotDeletedDrivingLessonRequest(false);
        JsonNode requestBody = createRequestBodyForDrivingLessonRequest("msgTest", "2026-06-02", "2026-06-02 16:00:00", "2026-06-02 18:00:00", studentId, instructorId);

        mockMvc.perform(post(BASEURL + "/drivingLesson").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk());

        Long sizeAfterSending = drivingLessonRequestRepository.countNotDeletedDrivingLessonRequest(false);
        assertEquals(sizeBeforeSending + 1, sizeAfterSending, "");
    }

    @Test
    @DisplayName("Send drivingLesson request to non existent instructor")
    public void sendDrivingLessonRequestToNonExistentInstructor() throws Exception {
        Long sizeBeforeSending = drivingLessonRequestRepository.countNotDeletedDrivingLessonRequest(false);
        JsonNode requestBody = createRequestBodyForDrivingLessonRequest("msgTest", "2026-06-02", "2026-06-02 16:00:00", "2026-06-02 18:00:00", studentId, instructorId + 1);

        mockMvc.perform(post(BASEURL + "/drivingLesson").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("instructorNotFound")));

        Long sizeAfterSending = drivingLessonRequestRepository.countNotDeletedDrivingLessonRequest(false);
        assertEquals(sizeBeforeSending, sizeAfterSending, "");
    }

    @Test
    @DisplayName("Send drivingLesson request with non existent student")
    public void sendDrivingLessonRequestWithNonExistentStudent() throws Exception {
        Long sizeBeforeSending = drivingLessonRequestRepository.countNotDeletedDrivingLessonRequest(false);
        JsonNode requestBody = createRequestBodyForDrivingLessonRequest("msgTest", "2026-06-02", "2026-06-02 16:00:00", "2026-06-02 18:00:00", studentId + 1, instructorId);

        mockMvc.perform(post(BASEURL + "/drivingLesson").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("studentNotFound")));

        Long sizeAfterSending = drivingLessonRequestRepository.countNotDeletedDrivingLessonRequest(false);
        assertEquals(sizeBeforeSending, sizeAfterSending, "");
    }

    @Test
    @DisplayName("Send drivingLesson request with invalid date (format)")
    public void sendDrivingLessonRequestWithInvalidDate() throws Exception {
        Long sizeBeforeSending = drivingLessonRequestRepository.countNotDeletedDrivingLessonRequest(false);
        JsonNode requestBody = createRequestBodyForDrivingLessonRequest("msgTest", "02-08-2026", "2026-06-02 16:00:00", "2026-06-02 18:00:00", studentId, instructorId);

        mockMvc.perform(post(BASEURL + "/drivingLesson").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", is("invalidDate")));

        Long sizeAfterSending = drivingLessonRequestRepository.countNotDeletedDrivingLessonRequest(false);
        assertEquals(sizeBeforeSending, sizeAfterSending, "");
    }

    @Test
    @DisplayName("Send drivingLesson request with invalid start & end time")
    public void sendDrivingLessonRequestWithInvalidStartEndTime() throws Exception {
        Long sizeBeforeSending = drivingLessonRequestRepository.countNotDeletedDrivingLessonRequest(false);
        JsonNode requestBody = createRequestBodyForDrivingLessonRequest("msgTest", "2026-06-02", "2026-06-02 20:00:00", "2026-06-02 18:00:00", studentId, instructorId);

        mockMvc.perform(post(BASEURL + "/drivingLesson").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", is("invalidDate")));

        Long sizeAfterSending = drivingLessonRequestRepository.countNotDeletedDrivingLessonRequest(false);
        assertEquals(sizeBeforeSending, sizeAfterSending, "");
    }

    @Test
    @DisplayName("Send drivingLesson request to instructor from other school")
    public void sendDrivingLessonRequestWithInstructorFromOtherSchool() throws Exception {
        Long sizeBeforeSending = drivingLessonRequestRepository.countNotDeletedDrivingLessonRequest(false);
        JsonNode requestBody = createRequestBodyForDrivingLessonRequest("msgTest", "2026-06-02", "2026-06-02 16:00:00", "2026-06-02 18:00:00", studentId, secondInstructorId);

        mockMvc.perform(post(BASEURL + "/drivingLesson").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", is("invalidInstructor")));

        Long sizeAfterSending = drivingLessonRequestRepository.countNotDeletedDrivingLessonRequest(false);
        assertEquals(sizeBeforeSending, sizeAfterSending, "");
    }

    @Test
    @DisplayName("Send drivingLesson request to other instructor from same school")
    public void sendDrivingLessonRequestWithOtherStudentsInstructor() throws Exception {
        Long sizeBeforeSending = drivingLessonRequestRepository.countNotDeletedDrivingLessonRequest(false);
        JsonNode requestBody = createRequestBodyForDrivingLessonRequest("msgTest", "2026-06-02", "2026-06-02 16:00:00", "2026-06-02 18:00:00", studentId, secondInstructorId);
        mockMvc.perform(post(BASEURL + "/drivingLesson").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", is("invalidInstructor")));

        Long sizeAfterSending = drivingLessonRequestRepository.countNotDeletedDrivingLessonRequest(false);
        assertEquals(sizeBeforeSending, sizeAfterSending, "");
    }

    private JsonNode createRequestBodyForDrivingLessonRequest(String msg, String date, String startTime, String endTime, Long studentId, Long instructorId) {
        JsonNode returnObject = objectMapper.createObjectNode();
        ((ObjectNode) returnObject).put("msg", msg);
        ((ObjectNode) returnObject).put("date", date);
        ((ObjectNode) returnObject).put("startTime", startTime);
        ((ObjectNode) returnObject).put("endTime", endTime);
        ((ObjectNode) returnObject).put("studentId", studentId);
        ((ObjectNode) returnObject).put("instructorId", instructorId);
        return returnObject;
    }
}
