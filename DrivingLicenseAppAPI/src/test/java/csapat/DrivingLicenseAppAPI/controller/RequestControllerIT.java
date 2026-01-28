package csapat.DrivingLicenseAppAPI.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

//16db
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource(locations = "classpath:test-application.properties")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RequestControllerIT {

    @Autowired
    MockMvc mockMvc;

    @BeforeAll
    public void setUpRequiredDatas() {
        System.out.println("setUpRequiredDatas");
    }

    @Test
    public void sendSchoolJoinRequestWithValidDatas() throws Exception {}

    @Test
    public void sendSchoolJoinRequestWithNonExistentSchool() throws Exception {}

    @Test
    public void sendSchoolJoinRequestWithNonExistentUser() throws Exception {}

    @Test
    public void sendSchoolJoinRequestWithNonExistentCategory() throws Exception {}

    @Test
    public void sendSchoolJoinRequestWithNonExistentCategoryInSchool() throws Exception {}

    @Test
    public void sendInstructorJoinRequestWithValidDatas() throws Exception {}

    @Test
    public void sendInstructorJoinRequestWithNonExistentInstructor() throws Exception {}

    @Test
    public void sendInstructorJoinRequestWithNonExistentStudent() throws Exception {}

    @Test
    public void sendInstructorJoinRequestWithInvalidInstructor() throws Exception {}

    @Test
    public void sendDrivingLessonRequestWithValidDatas() throws Exception {}

    @Test
    public void sendDrivingLessonRequestWithNonExistentInstructor() throws Exception {}

    @Test
    public void sendDrivingLessonRequestWithNonExistentStudent() throws Exception {}

    @Test
    public void sendDrivingLessonRequestWithInvalidDate() throws Exception {}

    @Test
    public void sendDrivingLessonRequestWithInvalidStartEndTime() throws Exception {}

    @Test
    public void sendDrivingLessonRequestWithInstructorFromOtherSchool() throws Exception {}

    @Test
    public void sendDrivingLessonRequestWithOtherStudentsInstructor() throws Exception {}
}
