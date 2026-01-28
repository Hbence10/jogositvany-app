package csapat.DrivingLicenseAppAPI.controller;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
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
public class UserControllerIT {

    @Autowired
    MockMvc mockMvc;

//    @Autowired
//    tools.jackson.databind.ObjectMapper objectMapper;

    @Test
    public void loginWithExistentUsersDetails() throws Exception {
    }

    @Test
    public void loginWithNonExistentEmail() throws Exception{}

    @Test
    public void loginWithBadPassword() throws Exception{}

    @Test
    public void registerWithValidDatasAsStudent() throws Exception{}

    @Test
    public void registerWithValidDatasAsInstructor() throws Exception{}

    @Test
    public void registerAsNonExistentRole() throws Exception {}

    @Test
    public void registerWithInvalidObject() throws Exception {}

    @Test
    public void registerWithInvalidGender() throws Exception {}

    @Test
    public void registerWithInvalidEmail() throws Exception {}

    @Test
    public void registerWithInvalidPhone() throws Exception {}

    @Test
    public void registerWithInvalidPassword() throws Exception {}

    @Test
    public void registerWithInvalidBirthDate() throws Exception {}

    @Test
    public void getVerificationCodeWithExistentEmail() throws Exception {}

    @Test
    public void getVerificationCodeWithNonExistentEmail() throws Exception {}

    @Test
    public void checkValidVerificationCode() throws Exception {}

    @Test
    public void checkVerificationCodeWithNonExistentEmail() throws Exception{}

    @Test
    public void checkInvalidVerificationCode() throws Exception {}

    @Test
    public void updatePasswordWithValidDatas() throws Exception {}

    @Test
    public void updatePasswordWithNonExistentEmail() throws Exception {}

    @Test
    public void updatePasswordWithInvalidPassword() throws Exception {}

    @Test
    public void updateExistentUserWithValidDatas() throws Exception {}

    @Test
    public void updateNonExistentUser() throws Exception {}

    @Test
    public void updateWithInvalidObject() throws Exception {}

    @Test
    public void updateWithInvalidGender() throws Exception {}

    @Test
    public void updateWithInvalidEmail() throws Exception {}

    @Test
    public void updateWithInvalidPhone() throws Exception {}

    @Test
    public void updateWithInvalidPassword() throws Exception {}

    @Test
    public void updateWithInvalidBirthDate() throws Exception {}

    //updatePfp
    @Test
    public void deleteExistentUser() throws Exception {}

    @Test
    public void deleteNonExistentUser() throws Exception {}

    @Test
    public void getExistentUserById() throws Exception {}

    @Test
    public void getNonExistentUserById() throws Exception {}

    @Test
    public void getAllUser() throws Exception {}


}
