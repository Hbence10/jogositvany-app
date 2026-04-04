package csapat.DrivingLicenseAppAPI.controller;

import csapat.DrivingLicenseAppAPI.entity.*;
import csapat.DrivingLicenseAppAPI.repository.*;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.hamcrest.Matchers.hasSize;
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

    private String BASEURL = "http://localhost:8080/drivingLesson";

    MockMvc mockMvc;
    private final UserRepository userRepository;
    private final SchoolRepository schoolRepository;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;
    private final PasswordEncoder passwordEncoder;
    private final DrivingLessonRepository drivingLessonRepository;
    private final ReservedHourRepository reservedHourRepository;
    private final ReservedDateRepository reservedDateRepository;
    private final DrivingLicenseCategoryRepository drivingLicenseCategoryRepository;

    @Autowired
    public DrivingLessonControllerIT(ReservedDateRepository reservedDateRepository, DrivingLicenseCategoryRepository drivingLicenseCategoryRepository, ReservedHourRepository reservedHourRepository, DrivingLessonRepository drivingLessonRepository, PasswordEncoder passwordEncoder, InstructorRepository instructorRepository, StudentRepository studentRepository, SchoolRepository schoolRepository, UserRepository userRepository, MockMvc mockMvc) {
        this.reservedDateRepository = reservedDateRepository;
        this.drivingLicenseCategoryRepository = drivingLicenseCategoryRepository;
        this.reservedHourRepository = reservedHourRepository;
        this.drivingLessonRepository = drivingLessonRepository;
        this.passwordEncoder = passwordEncoder;
        this.instructorRepository = instructorRepository;
        this.studentRepository = studentRepository;
        this.schoolRepository = schoolRepository;
        this.userRepository = userRepository;
        this.mockMvc = mockMvc;
    }

    Long studentId;
    Long instructorId;
    Long schoolId;
    Long drivingLessonId;

    @BeforeEach
    public void setup() {
        Users student = userRepository.save(new Users("testUser1", "registerStudent1", "test2@gmail.com", "06701111112", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));
        Users instructor = userRepository.save(new Users("testUser1", "registerStudent1", "test3@gmail.com", "06701111113", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));
        Users schoolOwner = userRepository.save(new Users("testUser1", "registerStudent1", "tes4t@gmail.com", "06701111114", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));

        School testSchool = schoolRepository.save(new School("schoolName", "schoolTest@gmail.com", "06706294719", "Tolna", "Nagykónyi", "sfafsafasf", "afsfassaf", schoolOwner));
        Instructors testInstructor = instructorRepository.save(new Instructors(testSchool, instructor));
        Students testStudent = studentRepository.save(new Students(student, testSchool, drivingLicenseCategoryRepository.findById(1L).get(), testInstructor));

        DateFormat dateWithTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.GERMAN);
        ReservedHour testReservedHour;

        try {
            testReservedHour = reservedHourRepository.save(new ReservedHour(dateWithTimeFormat.parse("2026-04-01 15:00:00"), dateWithTimeFormat.parse("2026-04-01 17:00:00"), reservedDateRepository.save(new ReservedDate(new Date()))));
            DrivingLessons testDrivingLesson = drivingLessonRepository.save(new DrivingLessons(100, 150, "testLocation", "testPickupPlace", "testDropoffPlace", 23, false, false, testReservedHour, testStudent, testInstructor));
            studentId = testStudent.getId();
            instructorId = testInstructor.getId();
            schoolId = testSchool.getId();
            drivingLessonId = testDrivingLesson.getId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
        Long beforeCancelling = drivingLessonRepository.countNotCanceledDrivingLesson(false);

        mockMvc.perform(delete(BASEURL + "/cancel/" + drivingLessonId))
                .andExpect(status().isOk());

        Long afterCancelling = drivingLessonRepository.countNotCanceledDrivingLesson(false);
        Assertions.assertEquals(beforeCancelling+1, afterCancelling, "");
    }

    @Test
    @DisplayName("Cancel not existent driving lesson.")
    public void cancelNonExistentDrivingLesson() throws Exception {
        Long beforeCancelling = drivingLessonRepository.countNotCanceledDrivingLesson(false);

        mockMvc.perform(delete(BASEURL + "/cancel/" + (drivingLessonId + 1)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", Is.is("drivingLessonNotFound")));

        Long afterCancelling = drivingLessonRepository.countNotCanceledDrivingLesson(false);
        Assertions.assertEquals(beforeCancelling, afterCancelling, "");
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
        mockMvc.perform(get(BASEURL + "/" + (drivingLessonId + 1)))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$", Is.is("drivingLessonNotFound")));
    }

    @Test
    @DisplayName("Get non existent driving lesson by id.")
    public void getNonExistentDrivingLessonById() throws Exception {
        mockMvc.perform(get(BASEURL + "/" + (drivingLessonId + 1)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", Is.is("drivingLessonNotFound")));
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
