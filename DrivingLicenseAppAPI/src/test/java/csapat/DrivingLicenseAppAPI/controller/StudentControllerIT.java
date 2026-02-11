package csapat.DrivingLicenseAppAPI.controller;

import csapat.DrivingLicenseAppAPI.repository.SchoolRepository;
import csapat.DrivingLicenseAppAPI.repository.StudentRepository;
import csapat.DrivingLicenseAppAPI.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.transaction.annotation.Transactional;

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
    private final String BASEURL = "http://localhost:8080/students";

    int testId;

    @Autowired
    public StudentControllerIT(MockMvc mockMvc, UserRepository userRepository, SchoolRepository schoolRepository, StudentRepository studentRepository) {
        this.mockMvc = mockMvc;
        this.userRepository = userRepository;
        this.schoolRepository = schoolRepository;
        this.studentRepository = studentRepository;
    }

    @BeforeEach
    public void setup() {

    }

    @Test
    @DisplayName("Get lessons' details of existent student.")
    public void getLessonDetailsOfExistentStudent() throws Exception {
    }

    @Test
    @DisplayName("Get lessons' details of existent student.")
    public void getLessonDetailsOfNonExistentStudent() throws Exception {
        mockMvc.perform(get(BASEURL + "/lessonDetails/" + 21314))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Delete existent student by id.")
    public void deleteExistentStudent() throws Exception {
    }

    @Test
    @DisplayName("Delete non-existent student by id.")
    public void deleteNonExistentStudent() throws Exception {
        Long sizeBeforeDelete = studentRepository.countNotDeletedUsers(false);
        mockMvc.perform(delete(BASEURL + "/" + 21314))
                .andExpect(status().isNotFound());
        Long sizeAfterDelete = studentRepository.countNotDeletedUsers(false);
        Assertions.assertEquals(sizeBeforeDelete, sizeAfterDelete, "");
    }

    @Test
    @DisplayName("Get existent student by id.")
    public void getExistentStudentById() throws Exception {
    }

    @Test
    @DisplayName("Get non-existent student by id.")
    public void getNonExistentStudentById() throws Exception {
        mockMvc.perform(get(BASEURL + "/" + 21314))
                .andExpect(status().isNotFound());
    }
}
