package csapat.DrivingLicenseAppAPI.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//15db
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource(locations = "classpath:test-application.properties")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class DrivingLessonControllerIT {

    private final String BASEURL = "http://localhost:8080/drivingLesson"

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    public void setup() {

    }

    @Test
    @DisplayName("Get driving license categories of existent school.")
    public void getDrivingLicenseCategoriesOfExistentSchool() throws Exception {
    }

    @Test
    @DisplayName("Get driving license categories of non existent school.")
    public void getDrivingLicenseCategoryOfNonExistentSchool() throws Exception {
    }

    @Test
    @DisplayName("Cancel existent driving lesson.")
    public void cancelExistentDrivingLesson() throws Exception {
    }

    @Test
    @DisplayName("Cancel not existent driving lesson.")
    public void cancelNonExistentDrivingLesson() throws Exception {
        mockMvc.perform(delete(BASEURL + "/cancel/421"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Update existent driving lesson with valid datas.")
    public void updateExistentDrivingLessonWithValidDatas() throws Exception {
    }

    @Test
    @DisplayName("Update non existent driving lesson.")
    public void updateNonExistentDrivingLesson() throws Exception {
    }

    @Test
    @DisplayName("Update driving lesson with invalid start & end range. Start is greater than end.")
    public void updateDrivingLessonWithInvalidStartEndRange() throws Exception {
    }

    @Test
    @DisplayName("Update driving lesson with invalid lesson hour number. Lesson hour didn't grow.")
    public void updateDrivingLessonWithInvalidLessonHourNumber() throws Exception {
    }

    @Test
    @DisplayName("Update driving lesson with non-existent payment method.")
    public void updateDrivingLessonWithNonExistentPaymentMethod() throws Exception {
    }

    @Test
    @DisplayName("Update driving lesson with non-existent status.")
    public void updateDrivingLessonWithNonExistentStatus() throws Exception {
    }

    @Test
    @DisplayName("Get reserved hours of existent user & with valid date format.")
    public void getReservedHourByValidDateAndInstructor() throws Exception {
    }

    @Test
    @DisplayName("Get reserved hours of non-existent instructor.")
    public void getReservedHourByNonExistentInstructor() throws Exception {
    }

    @Test
    public void getReservedHourByInvalidDateFormat() throws Exception {
    }

    @Test
    @DisplayName("Get Existent driving lesson by id.")
    public void getExistentDrivingLessonById() throws Exception {
    }

    @Test
    @DisplayName("Get non existent driving lesson by id.")
    public void getNonExistentDrivingLessonById() throws Exception {
        mockMvc.perform(get(BASEURL+"/32141"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Get reserved hours between two dates with valid datas.")
    public void getReservedHoursBetweenDatesWithValidDatas() throws Exception {

    }

    @Test
    @DisplayName("Get reserved hours between two dates of non existent instructor.")
    public void getNonExistentInstructorsReservedHoursBetweenTwoDates() throws Exception {

    }

    @Test
    @DisplayName("Get reserved hours between two dates with invalid start & end range.")
    public void getReservedHoursBetweenTwoDatesWithInvalidStartEndRange() throws Exception {

    }

    @Test
    @DisplayName("Get reserved hours between two dates with invalid date format")
    public void getReservedHoursBetweenTwoDatesWithInvalidDateFormat() throws Exception {

    }
}
