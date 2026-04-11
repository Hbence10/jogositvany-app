package csapat.DrivingLicenseAppAPI.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import csapat.DrivingLicenseAppAPI.dto.NewReview;
import csapat.DrivingLicenseAppAPI.entity.*;
import csapat.DrivingLicenseAppAPI.repository.*;
import org.hamcrest.core.Is.*;
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

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//13
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource(locations = "classpath:test-application.properties")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class ReviewControllerIT {

    MockMvc mockMvc;
    private final SchoolRepository schoolRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    private final ReviewRepository reviewRepository;
    private final InstructorRepository instructorRepository;
    private DrivingLicenseCategoryRepository drivingLicenseCategoryRepository;
    private final ObjectMapper objectMapper;

    private Long authorId;
    private Long testInstructorId;
    private Long testSchoolId;
    private Long testReviewId;
    private String BASEURL = "http://localhost:8080/review";

    @Autowired
    public ReviewControllerIT(ObjectMapper objectMapper, ReviewRepository reviewRepository, StudentRepository studentRepository, PasswordEncoder passwordEncoder, UserRepository userRepository, SchoolRepository schoolRepository, MockMvc mockMvc, InstructorRepository instructorRepository, DrivingLicenseCategoryRepository drivingLicenseCategoryRepository) {
        this.reviewRepository = reviewRepository;
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.schoolRepository = schoolRepository;
        this.mockMvc = mockMvc;
        this.instructorRepository = instructorRepository;
        this.drivingLicenseCategoryRepository = drivingLicenseCategoryRepository;
        this.objectMapper = objectMapper;
    }

    @BeforeEach
    public void setup() {
        Users baseAuthor = userRepository.save(new Users("testUser1", "registerStudent1", "test@gmail.com", "06701111111", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));
        Users instructor = userRepository.save(new Users("testUser4", "registerStudent4", "test4@gmail.com", "06701121111", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));
        Users schoolOwner = userRepository.save(new Users("testUser2", "registerStudent2", "test2@gmail.com", "06701111112", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));
        Users testAuthor = userRepository.save(new Users("testUser1", "registerStudent5", "test5@gmail.com", "06701211111", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));

        School testSchool = schoolRepository.save(new School("schoolName", "schoolTest@gmail.com", "06706894719", "Tolna", "Nagykónyi", "sfafsafasf", "afsfassaf", schoolOwner));
        Instructors testInstructor = instructorRepository.save(new Instructors(testSchool, instructor));
        Students testStudent = studentRepository.save(new Students(testAuthor, testSchool, drivingLicenseCategoryRepository.findById(1L).get()));
        Review instructorTestReview = reviewRepository.save(new Review("testReviewText1", 2.0, false, testStudent, testInstructor));
        Review schoolTestReview = reviewRepository.save(new Review("testReviewText1", 2.0, false, testStudent, testSchool));
        testInstructor.setReviewList(new ArrayList<Review>(Arrays.asList(instructorTestReview)));
        testSchool.setReviewList(new ArrayList<Review>(Arrays.asList(schoolTestReview)));

        authorId = testStudent.getId();
        testInstructorId = testInstructor.getId();
        testSchoolId = testSchool.getId();
        testReviewId = schoolTestReview.getId();
    }

    @Test
    @DisplayName("Get reviews about existent school")
    public void getReviewsAboutExistentSchool() throws Exception {
        String url = BASEURL + "?about=school&aboutId=" + testSchoolId;
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @DisplayName("Get reviews about existent instructor")
    public void getReviewsAboutExistentInstructor() throws Exception {
        String url = BASEURL + "?about=instructor&aboutId=" + testInstructorId;
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @DisplayName("Get reviews about non existent school")
    public void getReviewsAboutNonExistentSchool() throws Exception {
        String url = BASEURL + "?about=school&aboutId=" + (testSchoolId + 1);
        mockMvc.perform(get(url))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("schoolNotFound")));
    }

    @Test
    @DisplayName("Get reviews about non existent instructor")
    public void getReviewsAboutNonExistentInstructor() throws Exception {
        String url = BASEURL + "?about=instructor&aboutId=" + (testInstructorId + 1);
        mockMvc.perform(get(url))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("instructorNotFound")));
    }

    @Test
    @DisplayName("Get reviews about non valid destination")
    public void getReviewsAboutInvalidObjectType() throws Exception {
        String url = BASEURL + "?about=asdasa&aboutId=" + testSchoolId;
        mockMvc.perform(get(url))
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", is("invalidAbout")));
    }

    @Test
    @DisplayName("Delete existent review")
    public void deleteExistentReview() throws Exception {
        Long sizeBeforeDelete = reviewRepository.countNotDeletedReview(false);
        mockMvc.perform(delete(BASEURL + "/" + testReviewId))
                .andExpect(status().isOk());
        Long sizeAfterDelete = reviewRepository.countNotDeletedReview(false);
        assertEquals(sizeBeforeDelete, sizeAfterDelete + 1, "");
    }

    @Test
    @DisplayName("Delete non existent review")
    public void deleteNonExistent() throws Exception {
        Long sizeBeforeDelete = reviewRepository.countNotDeletedReview(false);
        mockMvc.perform(delete(BASEURL + "/" + (testReviewId + 1)))
                .andExpect(status().isNotFound());
        Long sizeAfterDelete = reviewRepository.countNotDeletedReview(false);
        assertEquals(sizeBeforeDelete, sizeAfterDelete, "");
    }

    @Test
    @DisplayName("Create review by existent student about existent instructor")
    public void createReviewByExistentStudentAboutExistentInstructor() throws Exception {
        NewReview newReview = createRequestBodyForReviewCreation("testReviewText", 2.0, authorId, testInstructorId, 0L);
        mockMvc.perform(post(BASEURL).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newReview)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text", is("testReviewText")))
                .andExpect(jsonPath("$.rating", is(2.0)));
    }

    @Test
    @DisplayName("Create review by existent student about existent school")
    public void createReviewByExistentStudentAboutExistentSchool() throws Exception {
        NewReview newReview = createRequestBodyForReviewCreation("testReviewText", 2.0, authorId, 0L, testSchoolId);
        mockMvc.perform(post(BASEURL).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newReview)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text", is("testReviewText")))
                .andExpect(jsonPath("$.rating", is(2.0)));
    }

    @Test
    @DisplayName("Create review with invalid rating")
    public void createReviewWithInvalidRating() throws Exception {
        NewReview newReview = createRequestBodyForReviewCreation("testReviewText", 7.0, authorId, 0L, testSchoolId);
        mockMvc.perform(post(BASEURL).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newReview)))
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", is("invalidRating")));
    }

    @Test
    @DisplayName("Create review by non existent student.")
    public void createReviewByNonExistentStudent() throws Exception {
        NewReview newReview = createRequestBodyForReviewCreation("testReviewText", 4.0, authorId + 1, 0L, testSchoolId);
        mockMvc.perform(post(BASEURL).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newReview)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("studentNotFound")));
    }

    @Test
    @DisplayName("Create review by existent student about non existent instructor")
    public void createReviewByExistentStudentAboutNonExistentInstructor() throws Exception {
        NewReview newReview = createRequestBodyForReviewCreation("testReviewText", 4.0, authorId, testInstructorId + 1, 0L);
        mockMvc.perform(post(BASEURL).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newReview)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("instructorNotFound")));
    }

    @Test
    @DisplayName("Create review by existent student about non existent school")
    public void createReviewByExistentStudentAboutNonExistentSchool() throws Exception {
        NewReview newReview = createRequestBodyForReviewCreation("testReviewText", 4.0, authorId, 0L, testSchoolId + 1);
        mockMvc.perform(post(BASEURL).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newReview)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("schoolNotFound")));
    }

    public NewReview createRequestBodyForReviewCreation(String reviewText, Double rating, Long studentId, Long instructorId, Long schoolId) {
        return new NewReview(reviewText, rating, studentId, false, instructorId, schoolId);
    }
}
