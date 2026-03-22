package csapat.DrivingLicenseAppAPI.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import csapat.DrivingLicenseAppAPI.entity.*;
import csapat.DrivingLicenseAppAPI.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;

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

    Long userId;
    Long studentId;
    Long schoolId;
    Long instructorId;

    public RequestControllerIT(UserRepository userRepository, StudentRepository studentRepository, InstructorRepository instructorRepository, SchoolRepository schoolRepository, PasswordEncoder passwordEncoder, DrivingLicenseCategoryRepository drivingLicenseCategoryRepository, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.instructorRepository = instructorRepository;
        this.schoolRepository = schoolRepository;
        this.passwordEncoder = passwordEncoder;
        this.drivingLicenseCategoryRepository = drivingLicenseCategoryRepository;
        this.objectMapper = objectMapper;
    }

    @BeforeEach
    public void setup() {
        Users user = userRepository.save(new Users("testUser1", "registerStudent1", "test@gmail.com", "06701111111", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));
        Users student = userRepository.save(new Users("testUser1", "registerStudent1", "test@gmail.com", "06701111111", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));
        Users instructor = userRepository.save(new Users("testUser1", "registerStudent1", "test@gmail.com", "06701111111", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));
        Users schoolOwner = userRepository.save(new Users("testUser1", "registerStudent1", "test@gmail.com", "06701111111", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));

        School testSchool = schoolRepository.save(new School("schoolName", "schoolTest@gmail.com", "06706894719", "Tolna", "Nagykónyi", "sfafsafasf", "afsfassaf", schoolOwner));
        Instructors testInstructor = instructorRepository.save(new Instructors(testSchool, instructor));
        Students testStudent = studentRepository.save(new Students(student, testSchool, drivingLicenseCategoryRepository.findById(1L).get()));

        userId = user.getId();
        studentId = testStudent.getId();
        schoolId = testSchool.getId();
        instructorId = testInstructor.getId();
    }

    @Test
    @DisplayName("Send schoolJoin request with valid datas as student")
    public void sendSchoolJoinRequestWithValidDatasAsStudent() throws Exception {
    }

    @Test
    @DisplayName("Send schoolJoin request with valid datas as instructor")
    public void sendSchoolJoinRequestWithValidDatasAsInstructor() throws Exception {
    }

    @Test
    @DisplayName("Send schoolJoin request to non existent school")
    public void sendSchoolJoinRequestToNonExistentSchool() throws Exception {
    }

    @Test
    @DisplayName("Send schoolJoin request with non existent user")
    public void sendSchoolJoinRequestWithNonExistentUser() throws Exception {
    }

    @Test
    @DisplayName("Send schoolJoin request with non existent category")
    public void sendSchoolJoinRequestWithNonExistentCategory() throws Exception {
    }

    @Test
    @DisplayName("Send schoolJoin request with non existent category in school")
    public void sendSchoolJoinRequestWithNonExistentCategoryInSchool() throws Exception {
    }

    private JsonNode createRequestBodyForSendingSchoolJoinRequest(Long schoolId, Long userId, Long categoryId) {
        return null;
    }

    @Test
    @DisplayName("Send instructorJoin request with valid datas")
    public void sendInstructorJoinRequestWithValidDatas() throws Exception {
    }

    @Test
    @DisplayName("Send instructorJoin request to non existent instructor")
    public void sendInstructorJoinRequestToNonExistentInstructor() throws Exception {
    }

    @Test
    @DisplayName("Send instructorJoin request with non existent student")
    public void sendInstructorJoinRequestWithNonExistentStudent() throws Exception {
    }

    @Test
    @DisplayName("Send instructorJoin request with invalid instructor")
    public void sendInstructorJoinRequestWithInvalidInstructor() throws Exception {
    }

    private JsonNode createRequestBodyForSendingInstructorJoinRequest(Long instructorId, Long studentId) {
        return null;
    }

    @Test
    @DisplayName("Send drivingLesson request with valid datas")
    public void sendDrivingLessonRequestWithValidDatas() throws Exception {
    }

    @Test
    @DisplayName("Send drivingLesson request to non existent instructor")
    public void sendDrivingLessonRequestToNonExistentInstructor() throws Exception {
    }

    @Test
    @DisplayName("Send drivingLesson request with non existent student")
    public void sendDrivingLessonRequestWithNonExistentStudent() throws Exception {
    }

    @Test
    @DisplayName("Send drivingLesson request with invalid date (format)")
    public void sendDrivingLessonRequestWithInvalidDate() throws Exception {
    }

    @Test
    @DisplayName("Send drivingLesson request with invalid start & end time")
    public void sendDrivingLessonRequestWithInvalidStartEndTime() throws Exception {
    }

    @Test
    @DisplayName("Send drivingLesson request to instructor from other school")
    public void sendDrivingLessonRequestWithInstructorFromOtherSchool() throws Exception {
    }

    @Test
    @DisplayName("Send drivingLesson request to other instructor from same school")
    public void sendDrivingLessonRequestWithOtherStudentsInstructor() throws Exception {
    }

    private JsonNode createRequestBodyForDrivingLessonRequest(String msg, Date date, LocalDateTime startTime, LocalDateTime endTime, Long studentId, Long instructorId) {
        return null;
    }
}
