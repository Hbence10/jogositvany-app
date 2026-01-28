package csapat.DrivingLicenseAppAPI.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

//15db
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource(locations = "classpath:application.properties")
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class DrivingLessonControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getDrivingLicenseCategoryOfExistentSchool() throws Exception {}

    @Test
    public void getDrivingLicenseCategoryOfNonExistentSchool() throws Exception {}

    @Test
    public void cancelExistentDrivingLesson() throws Exception {}

    @Test
    public void cancelNonExistentDrivingLesson() throws Exception {}

    @Test
    public void updateExistentDrivingLessonWithValidDatas() throws Exception {}

    @Test
    public void updateNonExistentDrivingLesson() throws Exception {}

    @Test
    public void updateDrivingLessonWithInvalidStartEndRange() throws Exception {}

    @Test
    public void updateDrivingLessonWithInvalidLessonHourNumber() throws Exception {}

    @Test
    public void updateDrivingLessonWithNonExistentPaymentMethod() throws Exception {}

    @Test
    public void updateDrivingLessonWithNonExistentStatus() throws Exception {}

    @Test
    public void getReservedHourByValidDateAndInstructor() throws  Exception {}

    @Test
    public void getReservedHourByNonExistentInstructor() throws Exception {}

    @Test
    public void getReservedHourByInvalidDateFormat() throws Exception {}

    @Test
    public void getExistentDrivingLessonById() throws Exception {}

    @Test
    public void getNonExistentDrivingLessonById() throws Exception {}
}
