package csapat.DrivingLicenseAppAPI.controller;

import csapat.DrivingLicenseAppAPI.entity.Education;
import csapat.DrivingLicenseAppAPI.entity.Role;
import csapat.DrivingLicenseAppAPI.entity.Users;
import csapat.DrivingLicenseAppAPI.repository.EducationRepository;
import csapat.DrivingLicenseAppAPI.repository.RoleRepository;
import csapat.DrivingLicenseAppAPI.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

//36db
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource(locations = "classpath:test-application.properties")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EducationRepository educationRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeAll
    public void setUpRequiredDatas() {
        List<Education> educations = new ArrayList<Education>(Arrays.asList(
                new Education("education1"),
                new Education("education2"),
                new Education("education3")
        ));
        educationRepository.saveAll(educations);
        educations = educationRepository.findAll();
        Role role = roleRepository.save(new Role("ROLE_user"));

        ArrayList<Users> userList = new ArrayList<>(Arrays.asList(
                new Users("firstName1", "lastName1", "bzhalmai@gmail.com", "06706285231", new Date(), "male", passwordEncoder.encode("test5.Asd"), educations.get(0), role),
                new Users("firstName2", "lastName2", "sulisdolgok8@gmail.com", "06706285232", new Date(), "male", passwordEncoder.encode("test5.Asd"), educations.get(0), role),
                new Users("firstName3", "lastName3", "halmaib.21d@acsjszki.hu", "06706285233", new Date(), "male", passwordEncoder.encode("test5.Asd"), educations.get(0), role)
        ));

        userRepository.saveAll(userList);
    }

    @Test
    public void loginWithExistentUsersDetails() throws Exception {

    }

    @Test
    public void loginWithNonExistentEmail() throws Exception {
    }

    @Test
    public void loginWithBadPassword() throws Exception {
    }

    @Test
    public void registerWithValidDatasAsStudent() throws Exception {
    }

    @Test
    public void registerWithValidDatasAsInstructor() throws Exception {
    }

    @Test
    public void registerAsNonExistentRole() throws Exception {
    }

    @Test
    public void registerWithInvalidObject() throws Exception {
    }

    @Test
    public void registerWithInvalidGender() throws Exception {
    }

    @Test
    public void registerWithInvalidEmail() throws Exception {
    }

    @Test
    public void registerWithInvalidPhone() throws Exception {
    }

    @Test
    public void registerWithInvalidPassword() throws Exception {
    }

    @Test
    public void registerWithInvalidBirthDate() throws Exception {
    }

    @Test
    public void getVerificationCodeWithExistentEmail() throws Exception {
    }

    @Test
    public void getVerificationCodeWithNonExistentEmail() throws Exception {
    }

    @Test
    public void checkValidVerificationCode() throws Exception {
    }

    @Test
    public void checkVerificationCodeWithNonExistentEmail() throws Exception {
    }

    @Test
    public void checkInvalidVerificationCode() throws Exception {
    }

    @Test
    public void updatePasswordWithValidDatas() throws Exception {
    }

    @Test
    public void updatePasswordWithNonExistentEmail() throws Exception {
    }

    @Test
    public void updatePasswordWithInvalidPassword() throws Exception {
    }

    @Test
    public void updateExistentUserWithValidDatas() throws Exception {
    }

    @Test
    public void updateNonExistentUser() throws Exception {
    }

    @Test
    public void updateWithInvalidObject() throws Exception {
    }

    @Test
    public void updateWithInvalidGender() throws Exception {
    }

    @Test
    public void updateWithInvalidEmail() throws Exception {
    }

    @Test
    public void updateWithInvalidPhone() throws Exception {
    }

    @Test
    public void updateWithInvalidPassword() throws Exception {
    }

    @Test
    public void updateWithInvalidBirthDate() throws Exception {
    }

    @Test
    public void updateExistentUserPfpWithValidPhoto() throws Exception {
    }

    @Test
    public void updateNoneExistentUsersPfp() throws Exception {
    }

    @Test
    public void updateUsersPfpWithInvalidPhoto() throws Exception {
    }

    @Test
    public void deleteExistentUser() throws Exception {
    }

    @Test
    public void deleteNonExistentUser() throws Exception {
    }

    @Test
    public void getExistentUserById() throws Exception {
    }

    @Test
    public void getNonExistentUserById() throws Exception {
    }

    @Test
    public void getAllUser() throws Exception {
    }


}
