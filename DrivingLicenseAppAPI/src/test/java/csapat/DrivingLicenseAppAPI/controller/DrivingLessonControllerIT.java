package csapat.DrivingLicenseAppAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource(locations = "classpath:application.properties")
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class DrivingLessonControllerIT {

    @Autowired
    MockMvc mockMvc;

    public void getDrivingLicenseCategoryOfExistentSchool() throws Exception {}

    public void getDrivingLicenseCategoryOfNonExistentSchool() throws Exception {}

    public void cancelExistentDrivingLesson() throws Exception {}

    public void cancelNonExistentDrivingLesson() throws Exception {}

    public void updateExistentDrivingLessonWithValidDatas() throws Exception {}

    public void updateNonExistentDrivingLesson() throws Exception {}

    public void updateDrivingLessonWithInvalidStartEndRange() throws Exception {}

    public void updateDrivingLessonWithInvalidLessonHourNumber() throws Exception {}

    public void updateDrivingLessonWithNonExistentPaymentMethod() throws Exception {}

    public void updateDrivingLessonWithNonExistentStatus() throws Exception {}

    public void getReservedHourByValidDateAndInstructor() throws  Exception {}

    public void getReservedHourByNonExistentInstructor() throws Exception {}

    public void getReservedHourByInvalidDateFormat() throws Exception {}

    public void getExistentDrivingLessonById() throws Exception {}

    public void getNonExistentDrivingLessonById() throws Exception {}
}
