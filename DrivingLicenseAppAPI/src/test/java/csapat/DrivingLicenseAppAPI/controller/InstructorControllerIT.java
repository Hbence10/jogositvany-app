package csapat.DrivingLicenseAppAPI.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

//29db
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource(locations = "classpath:application.properties")
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class InstructorControllerIT {

    @Test
    public void acceptExistentJoinRequest() throws Exception {}

    @Test
    public void refuseExistentJoinRequest() throws Exception {}

    @Test
    public void handleNonExistentJoinRequest() throws Exception {}

    @Test
    public void handleExistentRequestWithInvalidStatus() throws Exception {}

    @Test
    public void getAllJoinRequestByExistentInstructor() throws Exception {}

    @Test
    public void getAllJoinRequestByNonExistentInstructor() throws Exception {}

    @Test
    public void getAllDrivingLessonRequestByExistentInstructor() throws Exception {}

    @Test
    public void getAllDrivingLessonRequestByNonExistentInstructor() throws Exception {}

    @Test
    public void updateExistentInstructorWithValidData() throws Exception {}

    @Test
    public void updateNonExistentInstructor() throws Exception {}

    @Test
    public void updateNonExistentVehicleOfInstructor() throws Exception {}

    @Test
    public void updateExistentVehicleWithNonExistentFuelType() throws Exception {}

    @Test
    public void updateExistentVehicleWithNonExistentVehicleType() throws Exception {}

    @Test
    public void updateExistentVehicleWithInvalidLicensePlate() throws Exception {}

    @Test
    public void acceptExistentDrivingLessonRequest() throws Exception {}

    @Test
    public void refuseExistentDrivingLessonRequest() throws Exception {}

    @Test
    public void handleNonExistentDrivingLessonRequest() throws Exception {}

    @Test
    public void handleDrivingLessonWithInvalidStatus() throws Exception {}

    @Test
    public void searchInstructorWithValidDatas() throws Exception {}

    @Test
    public void searchInstructorWithNonExistentFuelType() throws Exception {}

    @Test
    public void searchInstructorWithNonExistentSchool() throws Exception {}

    @Test
    public void searchInstructorWithNonExistentLicenseCategory() throws Exception {}

    @Test
    public void getExistentInstructorById() throws Exception {}

    @Test
    public void getNonExistentInstructorById() throws Exception {}

    @Test
    public void getStudentsOfExistentInstructor() throws Exception {}

    @Test
    public void getStudentsOfNonExistestInstructor() throws Exception {}

    @Test
    public void kickOutExistentStudent() throws Exception {}

    @Test
    public void kickOutNonExistentStudent() throws Exception {}

    @Test
    public void kickOutInvalidStudent() throws Exception {}

}
