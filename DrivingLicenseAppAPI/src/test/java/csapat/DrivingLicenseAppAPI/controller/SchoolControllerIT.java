package csapat.DrivingLicenseAppAPI.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

//37
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource(locations = "classpath:test-application.properties")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SchoolControllerIT {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void acceptExistentJoinRequest() throws Exception {
    }

    @Test
    public void refuseExistentJoinRequest() throws Exception {
    }

    @Test
    public void handleJoinRequestWithInvalidStatus() throws Exception {
    }

    @Test
    public void handleNonExistentJoinRequest() throws Exception {
    }

    @Test
    public void updateExistentSchoolWithValidDatas() throws Exception {
    }

    @Test
    public void updateNonExistentSchool() throws Exception {
    }

    @Test
    public void updateExistentSchoolWithInvalidPhone() throws Exception {
    }

    @Test
    public void updateExistentSchoolWithInvalidEmail() throws Exception {
    }

    @Test
    public void updateExistentSchoolWithDuplicatedName() throws Exception {
    }

    @Test
    public void updateExistentSchoolWithDuplicatedPhone() throws Exception {
    }

    @Test
    public void updateExistentSchoolWithDuplicatedEmail() throws Exception {
    }

    //coverImg
    @Test
    public void updateExistentSchoolsCoverImgWithValidPhoto() throws Exception {
    }

    @Test
    public void updateNonExistentSchoolsCoverImg() throws Exception {
    }

    @Test
    public void updateExistentSchoolsCoverImgWithInvalidPhoto() throws Exception {
    }

    @Test
    public void updateOpeningDetailsOfExistentSchool() throws Exception {
    }

    @Test
    public void updateOpeningDetailsOfNonExistentSchool() throws Exception {
    }

    @Test
    public void updateOpeningDetailsOfExistentSchoolWithInvalidDateFormat() throws Exception {
    }

    @Test
    public void getAllJoinRequestOfExistentSchool() throws Exception {
    }

    @Test
    public void getAllJoinRequestOfNonExistentSchool() throws Exception {
    }

    @Test
    public void deleteExistentSchool() throws Exception {
    }

    @Test
    public void deleteNonExistentSchool() throws Exception {
    }

    @Test
    public void searchSchoolsByExistentTown() throws Exception {
    }

    @Test
    public void searchSchoolByNonExistentTown() throws Exception {
    }

    @Test
    public void getExistentSchoolById() throws Exception {
    }

    @Test
    public void getNonExistentSchoolById() throws Exception {
    }

    @Test
    public void createSchoolWithValidDatas() throws Exception {
    }

    @Test
    public void createSchoolWithInvalidObject() throws Exception {
    }

    @Test
    public void createSchoolWithInvalidEmail() throws Exception {
    }

    @Test
    public void createSchoolWithInvalidPhone() throws Exception {
    }

    @Test
    public void createSchoolWithNonExistentTown() throws Exception {
    }

    @Test
    public void getAllStudentsOfExistentSchool() throws Exception {
    }

    @Test
    public void getAllInstructorOfExistentSchool() throws Exception {
    }

    @Test
    public void getAllMemberOfNonExistentSchool() throws Exception {
    }

    @Test
    public void getAllMemberOfSchoolByInvalidRole() throws Exception {
    }

    @Test
    public void kickoutExistentInstructor() throws Exception {
    }

    @Test
    public void kickoutNonExistentInstructor() throws Exception {
    }

    @Test
    public void getAllSchool() throws Exception {
    }
}

