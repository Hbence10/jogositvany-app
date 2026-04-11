package csapat.DrivingLicenseAppAPI.controller;

import csapat.DrivingLicenseAppAPI.entity.Education;
import csapat.DrivingLicenseAppAPI.entity.School;
import csapat.DrivingLicenseAppAPI.entity.Students;
import csapat.DrivingLicenseAppAPI.entity.Users;
import csapat.DrivingLicenseAppAPI.repository.DrivingLicenseCategoryRepository;
import csapat.DrivingLicenseAppAPI.repository.SchoolRepository;
import csapat.DrivingLicenseAppAPI.repository.StudentRepository;
import csapat.DrivingLicenseAppAPI.repository.UserRepository;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

//6db
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource(locations = "classpath:test-application.properties")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class StudentControllerIT {

    MockMvc mockMvc;
    private UserRepository userRepository;
    private SchoolRepository schoolRepository;
    private StudentRepository studentRepository;
    private DrivingLicenseCategoryRepository drivingLicenseCategoryRepository;
    private PasswordEncoder passwordEncoder;
    private final String BASEURL = "http://localhost:8080/students";

    Long testId;

    @Autowired
    public StudentControllerIT(MockMvc mockMvc, UserRepository userRepository, SchoolRepository schoolRepository, StudentRepository studentRepository, PasswordEncoder passwordEncoder, DrivingLicenseCategoryRepository drivingLicenseCategoryRepository) {
        this.mockMvc = mockMvc;
        this.userRepository = userRepository;
        this.schoolRepository = schoolRepository;
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.drivingLicenseCategoryRepository = drivingLicenseCategoryRepository;
    }

    @BeforeEach
    public void setup() {
        Users testUser = userRepository.save(new Users("testUser1", "registerStudent1", "test@gmail.com", "06701111111", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));
        School testSchool = schoolRepository.save(new School("schoolName", "schoolTest@gmail.com", "06706894719", "Tolna", "Dombóvár", "sfafsafasf", "afsfassaf", testUser));
        Students testStudent = studentRepository.save(new Students(testUser, testSchool, drivingLicenseCategoryRepository.findById(1L).get()));

        testId = testStudent.getId();
    }

    @Test
    @DisplayName("Get lessons' details of existent student.")
    public void getLessonDetailsOfExistentStudent() throws Exception {
        mockMvc.perform(get(BASEURL + "/lessonDetails/" + testId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Get lessons' details of non existent student.")
    public void getLessonDetailsOfNonExistentStudent() throws Exception {
        mockMvc.perform(get(BASEURL + "/lessonDetails/" + (testId + 1)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Delete existent student by id.")
    public void deleteExistentStudent() throws Exception {
        Long sizeBeforeDelete = studentRepository.countNotDeletedStudents(false);
        mockMvc.perform(delete(BASEURL + "/" + testId))
                .andExpect(status().isOk());
        Long sizeAfterDelete = studentRepository.countNotDeletedStudents(false);
        assertEquals(sizeBeforeDelete, sizeAfterDelete + 1, "");
    }

    @Test
    @DisplayName("Delete non-existent student by id.")
    public void deleteNonExistentStudent() throws Exception {
        Long sizeBeforeDelete = studentRepository.countNotDeletedStudents(false);
        mockMvc.perform(delete(BASEURL + "/" + (testId + 1)))
                .andExpect(status().isNotFound());
        Long sizeAfterDelete = studentRepository.countNotDeletedStudents(false);
        assertEquals(sizeBeforeDelete, sizeAfterDelete, "");
    }

    @Test
    @DisplayName("Get existent student by id.")
    public void getExistentStudentById() throws Exception {
        mockMvc.perform(get(BASEURL + "/" + testId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Is.is(Integer.valueOf(testId + ""))));
    }

    @Test
    @DisplayName("Get non-existent student by id.")
    public void getNonExistentStudentById() throws Exception {
        mockMvc.perform(get(BASEURL + "/" + (testId + 1)))
                .andExpect(status().isNotFound());
    }
}
