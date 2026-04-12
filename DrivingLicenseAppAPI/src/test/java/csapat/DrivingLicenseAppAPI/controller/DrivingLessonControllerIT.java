package csapat.DrivingLicenseAppAPI.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import csapat.DrivingLicenseAppAPI.dto.DrivingLessonUpdate;
import csapat.DrivingLicenseAppAPI.entity.*;
import csapat.DrivingLicenseAppAPI.repository.*;
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
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private final ObjectMapper mapper;
    private final SchoolCategoryRepository schoolCategoryRepository;

    @Autowired
    public DrivingLessonControllerIT(SchoolCategoryRepository schoolCategoryRepository, DrivingLicenseCategoryRepository drivingLicenseCategoryRepository, ObjectMapper mapper, ReservedDateRepository reservedDateRepository, ReservedHourRepository reservedHourRepository, DrivingLessonRepository drivingLessonRepository, PasswordEncoder passwordEncoder, InstructorRepository instructorRepository, StudentRepository studentRepository, SchoolRepository schoolRepository, UserRepository userRepository, MockMvc mockMvc) {
        this.drivingLicenseCategoryRepository = drivingLicenseCategoryRepository;
        this.mapper = mapper;
        this.reservedDateRepository = reservedDateRepository;
        this.reservedHourRepository = reservedHourRepository;
        this.drivingLessonRepository = drivingLessonRepository;
        this.passwordEncoder = passwordEncoder;
        this.instructorRepository = instructorRepository;
        this.studentRepository = studentRepository;
        this.schoolRepository = schoolRepository;
        this.userRepository = userRepository;
        this.mockMvc = mockMvc;
        this.schoolCategoryRepository = schoolCategoryRepository;
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
        testSchool.setLicenseCategoryList(new ArrayList<>(Arrays.asList(
                schoolCategoryRepository.save(new SchoolCategory(0, drivingLicenseCategoryRepository.findById(1L).get(), testSchool))
        )));
        schoolRepository.save(testSchool);
        Instructors testInstructor = instructorRepository.save(new Instructors(testSchool, instructor));
        Students testStudent = studentRepository.save(new Students(student, testSchool, drivingLicenseCategoryRepository.findById(1L).get(), testInstructor));

        DateFormat dateWithTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.GERMAN);
        ReservedHour testReservedHour;

        try {
            testReservedHour = reservedHourRepository.save(new ReservedHour(dateWithTimeFormat.parse("2026-02-01 15:00:00"), dateWithTimeFormat.parse("2026-04-01 17:00:00"), reservedDateRepository.save(new ReservedDate(new Date()))));
            DrivingLessons testDrivingLesson = drivingLessonRepository.save(new DrivingLessons(100, 150, "testLocation", "testPickupPlace", "testDropoffPlace", 23, false, false, testReservedHour, testStudent, testInstructor, new Status(1l, "státusz_tipus1 ")));
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
        mockMvc.perform(get(BASEURL + "/categories/school/" + schoolId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @DisplayName("Get driving license categories of non existent school.")
    public void getDrivingLicenseCategoryOfNonExistentSchool() throws Exception {
        mockMvc.perform(get(BASEURL + "/categories/school/" + (schoolId + 1)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("schoolNotFound")));
    }

    @Test
    @DisplayName("Cancel existent driving lesson.")
    public void cancelExistentDrivingLesson() throws Exception {
        Long beforeCancelling = drivingLessonRepository.countByCancelling(true);

        mockMvc.perform(delete(BASEURL + "/cancel/" + drivingLessonId))
                .andExpect(status().isOk());

        Long afterCancelling = drivingLessonRepository.countByCancelling(true);
        assertEquals(beforeCancelling+1, afterCancelling, "");
    }

    @Test
    @DisplayName("Cancel not existent driving lesson.")
    public void cancelNonExistentDrivingLesson() throws Exception {
        Long beforeCancelling = drivingLessonRepository.countByCancelling(true);

        mockMvc.perform(delete(BASEURL + "/cancel/" + (drivingLessonId + 1)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("drivingLessonNotFound")));

        Long afterCancelling = drivingLessonRepository.countByCancelling(true);
        assertEquals(beforeCancelling, afterCancelling, "");
    }

    @Test
    @DisplayName("Update existent driving lesson with valid datas.")
    public void updateExistentDrivingLessonWithValidDatas() throws Exception {
        DrivingLessonUpdate requestBody = createRequestBodyForDrivingLessonUpdate(22, 44, "testUpdateLocation", "testUpdatePickup", "testDropOffPlace", 321, true, 1l, 1l);
        mockMvc.perform(put(BASEURL+ "/" + drivingLessonId).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.startKm", is(22)))
                .andExpect(jsonPath("$.endKm", is(44)))
                .andExpect(jsonPath("$.location", is("testUpdateLocation")))
                .andExpect(jsonPath("$.pickUpPlace", is("testUpdatePickup")))
                .andExpect(jsonPath("$.dropOffPlace", is("testDropOffPlace")))
                .andExpect(jsonPath("$.lessonHourNumber", is(321)))
                .andExpect(jsonPath("$.isPaid", is(true)));
    }

    @Test
    @DisplayName("Update non existent driving lesson.")
    public void updateNonExistentDrivingLesson() throws Exception {
        DrivingLessonUpdate requestBody = createRequestBodyForDrivingLessonUpdate(22, 44, "testUpdateLocation", "testUpdatePickup", "testDropOffPlace", 321, true, 1l, 1l);
        mockMvc.perform(put(BASEURL+ "/" + (drivingLessonId+1)).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(requestBody)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("drivingLessonNotFound")));
    }

    @Test
    @DisplayName("Update driving lesson with invalid start & end range. Start is greater than end.")
    public void updateDrivingLessonWithInvalidStartEndRange() throws Exception {
        DrivingLessonUpdate requestBody = createRequestBodyForDrivingLessonUpdate(22, 14, "testUpdateLocation", "testUpdatePickup", "testDropOffPlace", 321, true, 1l, 1l);
        mockMvc.perform(put(BASEURL+ "/" + drivingLessonId).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(requestBody)))
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", is("invalidStartEndKm")));
    }

    @Test
    @DisplayName("Update driving lesson with invalid lesson hour number. Lesson hour didn't grow.")
    public void updateDrivingLessonWithInvalidLessonHourNumber() throws Exception {
        DrivingLessonUpdate requestBody = createRequestBodyForDrivingLessonUpdate(22, 44, "testUpdateLocation", "testUpdatePickup", "testDropOffPlace", 23, true, 1l, 1l);
        mockMvc.perform(put(BASEURL+ "/" + drivingLessonId).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(requestBody)))
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", is("invalidLessonHourNumber")));
    }

    @Test
    @DisplayName("Update driving lesson with non-existent payment method.")
    public void updateDrivingLessonWithNonExistentPaymentMethod() throws Exception {
        DrivingLessonUpdate requestBody = createRequestBodyForDrivingLessonUpdate(22, 44, "testUpdateLocation", "testUpdatePickup", "testDropOffPlace", 321, true, 1l, 1321l);
        mockMvc.perform(put(BASEURL+ "/" + drivingLessonId).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(requestBody)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("paymentMethodNotFound")));
    }

    @Test
    @DisplayName("Update driving lesson with non-existent status.")
    public void updateDrivingLessonWithNonExistentStatus() throws Exception {
        DrivingLessonUpdate requestBody = createRequestBodyForDrivingLessonUpdate(22, 44, "testUpdateLocation", "testUpdatePickup", "testDropOffPlace", 321, true, 1321l, 1l);
        mockMvc.perform(put(BASEURL+ "/" + drivingLessonId).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(requestBody)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("statusNotFound")));
    }

    public DrivingLessonUpdate createRequestBodyForDrivingLessonUpdate(Integer startKm, Integer endKm, String location, String pickUpPlace, String dropOffPlace, Integer lessonHourNumber, Boolean isPaid, Long statusId, Long paymentId){
        return new DrivingLessonUpdate(startKm, endKm, location, pickUpPlace, dropOffPlace, lessonHourNumber, isPaid, statusId, paymentId);
    }

    @Test
    @DisplayName("Get reserved hours of existent user & with valid date format.")
    public void getReservedHourByValidDateAndInstructor() throws Exception {
        mockMvc.perform(get(BASEURL + "/reservedHour?instructorId=" + instructorId + "&date=2026-02-01" ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @DisplayName("Get reserved hours of non-existent instructor.")
    public void getReservedHourByNonExistentInstructor() throws Exception {
        mockMvc.perform(get(BASEURL + "/reservedHour?instructorId=" + (instructorId + 1) + "&date=2026-02-01" ))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("instructorNotFound")));
    }

    @Test
    @DisplayName("Get Existent driving lesson by id.")
    public void getExistentDrivingLessonById() throws Exception {
        mockMvc.perform(get(BASEURL + "/" + drivingLessonId))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.startKm", is(100)))
                .andExpect(jsonPath("$.endKm", is(150)))
                .andExpect(jsonPath("$.location", is("testLocation")))
                .andExpect(jsonPath("$.pickUpPlace", is("testPickupPlace")))
                .andExpect(jsonPath("$.dropOffPlace", is("testDropoffPlace")))
                .andExpect(jsonPath("$.lessonHourNumber", is(23)))
                .andExpect(jsonPath("$.isPaid", is(false)));
    }

    @Test
    @DisplayName("Get non existent driving lesson by id.")
    public void getNonExistentDrivingLessonById() throws Exception {
        mockMvc.perform(get(BASEURL + "/" + (drivingLessonId + 1)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("drivingLessonNotFound")));
    }

    @Test
    @DisplayName("Get reserved hours between two dates with valid datas.")
    public void getReservedHoursBetweenDatesWithValidDatas() throws Exception {
        mockMvc.perform(get(BASEURL + "/reservedHours?instructorId=" + instructorId + "&startDate=2026-01-01&endDate=2026-02-06" ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @DisplayName("Get reserved hours between two dates of non existent instructor.")
    public void getNonExistentInstructorsReservedHoursBetweenTwoDates() throws Exception {
        mockMvc.perform(get(BASEURL + "/reservedHours?instructorId=" + (instructorId + 1) + "&startDate=2026-02-01&endDate=2026-02-06" ))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("instructorNotFound")));
    }

    @Test
    @DisplayName("Get reserved hours between two dates with invalid start & end range.")
    public void getReservedHoursBetweenTwoDatesWithInvalidStartEndRange() throws Exception {
        mockMvc.perform(get(BASEURL + "/reservedHours?instructorId=" + instructorId + "&startDate=2026-02-01&endDate=2026-01-06" ))
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", is("invalidDateRange")));

    }

    @Test
    @DisplayName("Get reserved hours between two dates with invalid date format")
    public void getReservedHoursBetweenTwoDatesWithInvalidDateFormat() throws Exception {
        mockMvc.perform(get(BASEURL + "/reservedHours?instructorId=" + instructorId + "&startDate=2026/02-01&endDate=2026-01-06" ))
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", is("invalidDateFormat")));
    }
}
