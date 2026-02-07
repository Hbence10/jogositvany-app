package csapat.DrivingLicenseAppAPI.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

//13
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource(locations = "classpath:test-application.properties")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReviewControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getReviewsAboutExistentSchool() throws Exception {
    }

    @Test
    public void getReviewsAboutExistentInstructor() throws Exception {
    }

    @Test
    public void getReviewsAboutNonExistentSchool() throws Exception {
    }

    @Test
    public void getReviewsAboutNonExistentInstructor() throws Exception {
    }

    @Test
    public void getReviewsAboutInvalidObjectType() throws Exception {
    }

    @Test
    public void deleteExistentReview() throws Exception {
    }

    @Test
    public void deleteNonExistent() throws Exception {
    }

    @Test
    public void createReviewByExistentStudentAboutExistentInstructor() throws Exception {
    }

    @Test
    public void createReviewByExistentStudentAboutExistentSchool() throws Exception {
    }

    @Test
    public void createReviewWithInvalidRating() throws Exception {
    }

    @Test
    public void createReviewByNonExistentStudent() throws Exception {
    }

    @Test
    public void createReviewByExistentStudentAboutNonExistentInstructor() throws Exception {
    }

    @Test
    public void createReviewByExistentStudentAboutNonExistentSchool() throws Exception {
    }
}
