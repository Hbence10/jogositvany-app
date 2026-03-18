package csapat.DrivingLicenseAppAPI.controller;

import csapat.DrivingLicenseAppAPI.dto.NewReview;
import csapat.DrivingLicenseAppAPI.entity.Education;
import csapat.DrivingLicenseAppAPI.entity.Instructors;
import csapat.DrivingLicenseAppAPI.entity.School;
import csapat.DrivingLicenseAppAPI.entity.Users;
import csapat.DrivingLicenseAppAPI.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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

    private Long authorId;
    private Long testInstructorId;
    private Long testSchoolId;

    @Autowired
    public ReviewControllerIT(ReviewRepository reviewRepository, StudentRepository studentRepository, PasswordEncoder passwordEncoder, UserRepository userRepository, SchoolRepository schoolRepository, MockMvc mockMvc, InstructorRepository instructorRepository) {
        this.reviewRepository = reviewRepository;
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.schoolRepository = schoolRepository;
        this.mockMvc = mockMvc;
        this.instructorRepository = instructorRepository;
    }

    @BeforeEach
    public void setup() {
        Users baseAuthor = userRepository.save(new Users("testUser1", "registerStudent1", "test@gmail.com", "06701111111", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));
        Users instructor = userRepository.save(new Users("testUser4", "registerStudent4", "test4@gmail.com", "06701121111", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));
        Users schoolOwner = userRepository.save(new Users("testUser2", "registerStudent2", "test2@gmail.com", "06701111112", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));
        Users testAuthor = userRepository.save(new Users("testUser1", "registerStudent1", "test@gmail.com", "06701111111", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));

        School testSchool = schoolRepository.save(new School("schoolName", "schoolTest@gmail.com", "06706894719", "Tolna", "Nagykónyi", "sfafsafasf", "afsfassaf", schoolOwner));
        Instructors testInstructor = instructorRepository.save(new Instructors(testSchool, instructor));

        authorId = testAuthor.getId();
        testInstructorId = testInstructor.getId();
        testSchoolId = testSchool.getId();
    }

    @Test
    @DisplayName("Get reviews about existent school")
    public void getReviewsAboutExistentSchool() throws Exception {
    }

    @Test
    @DisplayName("Get reviews about existent instructor")
    public void getReviewsAboutExistentInstructor() throws Exception {
    }

    @Test
    @DisplayName("Get reviews about non existent school")
    public void getReviewsAboutNonExistentSchool() throws Exception {
    }

    @Test
    @DisplayName("Get reviews about non existent instructor")
    public void getReviewsAboutNonExistentInstructor() throws Exception {
    }

    @Test
    @DisplayName("Get reviews about non valid destination")
    public void getReviewsAboutInvalidObjectType() throws Exception {
    }

    @Test
    @DisplayName("Delete existent review")
    public void deleteExistentReview() throws Exception {
    }

    @Test
    @DisplayName("Delete non existent review")
    public void deleteNonExistent() throws Exception {
    }

    @Test
    @DisplayName("Create review by existent student about existent instructor")
    public void createReviewByExistentStudentAboutExistentInstructor() throws Exception {
    }

    @Test
    @DisplayName("Create review by existent student about existent school")
    public void createReviewByExistentStudentAboutExistentSchool() throws Exception {
    }

    @Test
    @DisplayName("Create review with invalid rating")
    public void createReviewWithInvalidRating() throws Exception {
    }

    @Test
    @DisplayName("Create review by non existent student.")
    public void createReviewByNonExistentStudent() throws Exception {
    }

    @Test
    @DisplayName("Create review by existent student about non existent instructor")
    public void createReviewByExistentStudentAboutNonExistentInstructor() throws Exception {
    }

    @Test
    @DisplayName("Create review by existent student about non existent school")
    public void createReviewByExistentStudentAboutNonExistentSchool() throws Exception {
    }

    public NewReview createRequestBodyForReviewCreation(String reviewText, Double rating, Long studentId, Long instructorId, Long schoolId) {
        return new NewReview(reviewText, rating, studentId, false, instructorId, schoolId);
    }
}
