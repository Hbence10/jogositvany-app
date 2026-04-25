package csapat.DrivingLicenseAppAPI.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import csapat.DrivingLicenseAppAPI.dto.InstructorUpdate;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//28db
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource(locations = "classpath:test-application.properties")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class InstructorControllerIT {

    MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final InstructorRepository instructorRepository;
    private final SchoolRepository schoolRepository;
    private final StudentRepository studentRepository;
    private final InstructorJoinRequestRepository instructorJoinRequestRepository;
    private final DrivingLessonRequestRepository drivingLessonRequestRepository;
    private final VehicleRepository vehicleRepository;
    private final FuelTypeRepository fuelTypeRepository;
    private final VehicleTypeRepository vehicleTypeRepository;
    private final PasswordEncoder passwordEncoder;
    private final DrivingLicenseCategoryRepository drivingLicenseCategoryRepository;

    @Autowired
    public InstructorControllerIT(PasswordEncoder passwordEncoder, DrivingLicenseCategoryRepository drivingLicenseCategoryRepository, VehicleTypeRepository vehicleTypeRepository, FuelTypeRepository fuelTypeRepository, VehicleRepository vehicleRepository, DrivingLessonRequestRepository drivingLessonRequestRepository, InstructorJoinRequestRepository instructorJoinRequestRepository, StudentRepository studentRepository, SchoolRepository schoolRepository, InstructorRepository instructorRepository, UserRepository userRepository, ObjectMapper objectMapper, MockMvc mockMvc) {
        this.passwordEncoder = passwordEncoder;
        this.drivingLicenseCategoryRepository = drivingLicenseCategoryRepository;
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.fuelTypeRepository = fuelTypeRepository;
        this.vehicleRepository = vehicleRepository;
        this.drivingLessonRequestRepository = drivingLessonRequestRepository;
        this.instructorJoinRequestRepository = instructorJoinRequestRepository;
        this.studentRepository = studentRepository;
        this.schoolRepository = schoolRepository;
        this.instructorRepository = instructorRepository;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
        this.mockMvc = mockMvc;
    }

    private Long instructorId;
    private Long studentId;
    private Long joinRequestId;
    private Long drivingLessonRequestId;
    private Long vehicleId;
    private Long schoolId;
    private String BASEURL = "http://localhost:8080/instructor";

    @BeforeEach
    public void setup() {
        Users student = userRepository.save(new Users("testUser1", "registerStudent1", "test2@gmail.com", "06701111112", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));
        Users instructor = userRepository.save(new Users("testUser1", "registerStudent1", "test3@gmail.com", "06701111113", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));
        Users schoolOwner = userRepository.save(new Users("testUser1", "registerStudent1", "tes4t@gmail.com", "06701111114", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));
        Users secondInstructor = userRepository.save(new Users("testUser1", "registerStudent1", "test1@gmail.com", "06701111111", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));


        School testSchool = schoolRepository.save(new School("schoolName", "schoolTest@gmail.com", "06706294719", "Tolna", "Nagykónyi", "sfafsafasf", "afsfassaf", schoolOwner));
        schoolId = testSchool.getId();

        Vehicle secondtestVehicle = vehicleRepository.save(new Vehicle("PHP-112", "testAuto", vehicleTypeRepository.findById(1L).get(), fuelTypeRepository.findById(1L).get()));
        Vehicle testVehicle = vehicleRepository.save(new Vehicle("PHP-111", "testAuto", vehicleTypeRepository.findById(1L).get(), fuelTypeRepository.findById(1L).get()));

        instructorRepository.save(new Instructors(testSchool, secondInstructor, secondtestVehicle));
        Instructors testInstructor = instructorRepository.save(new Instructors(testSchool, instructor, testVehicle));

        Students testStudent = studentRepository.save(new Students(student, testSchool, drivingLicenseCategoryRepository.findById(1L).get(), testInstructor));
        instructorJoinRequestRepository.save(new InstructorJoinRequest(testStudent, testInstructor, false));
        InstructorJoinRequest testInstructorJoinRequest = instructorJoinRequestRepository.save(new InstructorJoinRequest(testStudent, testInstructor));

        DateFormat dateWithTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.GERMAN);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMAN);
       try {
           DrivingLessonRequest testDrivingLessonRequest = drivingLessonRequestRepository.save(new DrivingLessonRequest("", dateFormat.parse("2026-08-02"), dateWithTimeFormat.parse("2026-08-02 15:00:00"), dateWithTimeFormat.parse("2026-08-02 17:00:00"), testStudent, testInstructor));
           drivingLessonRequestId = testDrivingLessonRequest.getId();
       } catch (Exception e) {
           throw new RuntimeException();
       }

        instructorId = testInstructor.getId();
        studentId = testStudent.getId();
        joinRequestId = testInstructorJoinRequest.getId();
        vehicleId = testVehicle.getId();
    }

    //Csatlakozási kérelmek
    @Test
    @DisplayName("Accept existent join request")
    public void acceptExistentJoinRequest() throws Exception {
        JsonNode requestBody = createRequestBodyForHandleRequest(joinRequestId, "accept");
        mockMvc.perform(post(BASEURL + "/handleJoinRequest").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Refuse existent join request")
    public void refuseExistentJoinRequest() throws Exception {
        JsonNode requestBody = createRequestBodyForHandleRequest(joinRequestId, "refuse");
        mockMvc.perform(post(BASEURL + "/handleJoinRequest").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Handle non existent join request")
    public void handleNonExistentJoinRequest() throws Exception {
        JsonNode requestBody = createRequestBodyForHandleRequest(joinRequestId + 1, "accept");
        mockMvc.perform(post(BASEURL + "/handleJoinRequest").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("requestNotFound")));
    }

    @Test
    @DisplayName("Handle existent request with invalid status")
    public void handleExistentRequestWithInvalidStatus() throws Exception {
        JsonNode requestBody = createRequestBodyForHandleRequest(joinRequestId, "acceptrrasd");
        mockMvc.perform(post(BASEURL + "/handleJoinRequest").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", is("invalidStatus")));
    }

    @Test
    @DisplayName("Get all join request of existent instructor")
    public void getAllJoinRequestByExistentInstructor() throws Exception {
        mockMvc.perform(get(BASEURL + "/" + instructorId + "/joinRequest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @DisplayName("Get all join request of non-existent instructor")
    public void getAllJoinRequestByNonExistentInstructor() throws Exception {
        mockMvc.perform(get(BASEURL + "/" + (instructorId + 1) + "/joinRequest"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("instructorNotFound")));
    }

    //Vezetési óra kérelmek
    @Test
    @DisplayName("Get all drivingLessonRequest of existent instructor")
    public void getAllDrivingLessonRequestByExistentInstructor() throws Exception {
        mockMvc.perform(get(BASEURL + "/" + instructorId + "/drivingLessonRequest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @DisplayName("Get all drivingLessonRequest of non-existent instructor")
    public void getAllDrivingLessonRequestByNonExistentInstructor() throws Exception {
        mockMvc.perform(get(BASEURL + "/" + (instructorId + 1) + "/drivingLessonRequest"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("instructorNotFound")));
    }

    @Test
    @DisplayName("Accept an existent drivingLessonRequest")
    public void acceptExistentDrivingLessonRequest() throws Exception {
        JsonNode requestBody = createRequestBodyForHandleRequest(drivingLessonRequestId, "accept");
        mockMvc.perform(post(BASEURL + "/handleDrivingLessonRequest").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Refuse an existent drivingLessonRequest")
    public void refuseExistentDrivingLessonRequest() throws Exception {
        JsonNode requestBody = createRequestBodyForHandleRequest(drivingLessonRequestId, "refuse");
        mockMvc.perform(post(BASEURL + "/handleDrivingLessonRequest").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Handle a non existent drivingLessonRequest")
    public void handleNonExistentDrivingLessonRequest() throws Exception {
        JsonNode requestBody = createRequestBodyForHandleRequest(drivingLessonRequestId + 1, "accept");
        mockMvc.perform(post(BASEURL + "/handleDrivingLessonRequest").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("requestNotFound")));
    }

    @Test
    @DisplayName("Handle drivingLessonRequest with invalid status")
    public void handleDrivingLessonWithInvalidStatus() throws Exception {
        JsonNode requestBody = createRequestBodyForHandleRequest(drivingLessonRequestId, "acceptasdads");
        mockMvc.perform(post(BASEURL + "/handleDrivingLessonRequest").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", is("invalidStatus")));
    }

    JsonNode createRequestBodyForHandleRequest(Long requestId, String status) {
        JsonNode returnObject = objectMapper.createObjectNode();
        ((ObjectNode) returnObject).put("requestId", requestId);
        ((ObjectNode) returnObject).put("status", status);
        return returnObject;
    }

    //Frissites
    @Test
    @DisplayName("Update existent instructor with valid datas")
    public void updateExistentInstructorWithValidData() throws Exception {
        InstructorUpdate requestBody = createRequestBodyForUpdate("testPromo", vehicleId, "testVehicleUpdate", "PHP-121", 2L, 2L);
        mockMvc.perform(put(BASEURL + "/" + instructorId).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.instructor.promoText", is("testPromo")))
                .andExpect(jsonPath("$.instructor.vehicle.id", is(Integer.valueOf(vehicleId + ""))))
                .andExpect(jsonPath("$.instructor.vehicle.name", is("testVehicleUpdate")))
                .andExpect(jsonPath("$.instructor.vehicle.licensePlate", is("PHP-121")))
                .andExpect(jsonPath("$.instructor.vehicle.fuelType.id", is(2)))
                .andExpect(jsonPath("$.instructor.vehicle.vehicleType.id", is(2)));
    }

    @Test
    @DisplayName("Update non existent instructor")
    public void updateNonExistentInstructor() throws Exception {
        InstructorUpdate requestBody = createRequestBodyForUpdate("testPromo", vehicleId, "testVehicleUpdate", "PHP-121", 2L, 2L);
        mockMvc.perform(put(BASEURL + "/" + (instructorId + 1)).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("instructorNotFound")));
    }

    @Test
    @DisplayName("Update non existent vehicle of instructor")
    public void updateNonExistentVehicleOfInstructor() throws Exception {
        InstructorUpdate requestBody = createRequestBodyForUpdate("testPromo", vehicleId+1, "testVehicleUpdate", "PHP-121", 2L, 2L);
        mockMvc.perform(put(BASEURL + "/" + instructorId).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("vehicleNotFound")));
    }

    @Test
    @DisplayName("Update existent vehicle with non existent fuel type")
    public void updateExistentVehicleWithNonExistentFuelType() throws Exception {
        InstructorUpdate requestBody = createRequestBodyForUpdate("testPromo", vehicleId, "testVehicleUpdate", "PHP-121", 100L, 2L);
        mockMvc.perform(put(BASEURL + "/" + instructorId).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("fuelTypeNotFound")));
    }

    @Test
    @DisplayName("Update existent vehicle with non existent vehicle type")
    public void updateExistentVehicleWithNonExistentVehicleType() throws Exception {
        InstructorUpdate requestBody = createRequestBodyForUpdate("testPromo", vehicleId, "testVehicleUpdate", "PHP-121", 2L, 100L);
        mockMvc.perform(put(BASEURL + "/" + instructorId).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("vehicleTypeNotFound")));
    }

    @Test
    @DisplayName("Update existent vehicle with invalid license plate")
    public void updateExistentVehicleWithInvalidLicensePlate() throws Exception {
        InstructorUpdate requestBody = createRequestBodyForUpdate("testPromo", vehicleId, "testVehicleUpdate", "PHP-4121", 2L, 2L);
        mockMvc.perform(put(BASEURL + "/" + instructorId).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", is("invalidLicensePlate")));
    }

    @Test
    @DisplayName("Update vehicle with registered license plate")
    public void updateVehicleWithRegisteredLicensePlate() throws Exception {
        InstructorUpdate requestBody = createRequestBodyForUpdate("testPromo", vehicleId, "testVehicleUpdate", "PHP-112", 2L, 2L);
        mockMvc.perform(put(BASEURL + "/" + instructorId).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().is(409))
                .andExpect(jsonPath("$.statusText", is("registeredLicensePlate")));
    }

    private InstructorUpdate createRequestBodyForUpdate(String promoText, Long vehicleId, String vehicleName, String licensePlate, Long fuelTypeId, Long vehicleTypeId) {
        return new InstructorUpdate(promoText, vehicleId, vehicleName, licensePlate, fuelTypeId, vehicleTypeId);
    }

    //Keresés
    @Test
    @DisplayName("Search instructor with valid datas")
    public void searchInstructorWithValidDatas() throws Exception {
        String url = BASEURL + "?category=1&fuelType=1&school=" + schoolId;
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @DisplayName("Search instructor with non existent fuel type")
    public void searchInstructorWithNonExistentFuelType() throws Exception {
        String url = BASEURL + "?category=1&fuelType=100&school=" + schoolId;
        mockMvc.perform(get(url))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("fuelTypeNotFound")));
    }

    @Test
    @DisplayName("Search instructor with non existent school")
    public void searchInstructorWithNonExistentSchool() throws Exception {
        String url = BASEURL + "?category=1&fuelType=1&school=" + (schoolId+1);
        mockMvc.perform(get(url))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("schoolNotFound")));
    }

    //Id alapján
    @Test
    @DisplayName("Get existent instructor by id")
    public void getExistentInstructorById() throws Exception {
        mockMvc.perform(get(BASEURL + "/" + instructorId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(Integer.valueOf(String.valueOf(instructorId)))));
    }

    @Test
    @DisplayName("Get non existent instructor by id")
    public void getNonExistentInstructorById() throws Exception {
        mockMvc.perform(get(BASEURL + "/" + (instructorId + 1)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("instructorNotFound")));
    }

    @Test
    @DisplayName("Get students of existent instructor")
    public void getStudentsOfExistentInstructor() throws Exception {
        mockMvc.perform(get(BASEURL + "/" + instructorId + "/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @DisplayName("Get students of non existent instructor")
    public void getStudentsOfNonExistestInstructor() throws Exception {
        mockMvc.perform(get(BASEURL + "/" + (instructorId + 1) + "/students"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("instructorNotFound")));
    }

    //diak kirugasa
    @Test
    @DisplayName("Kick out existent student")
    public void kickOutExistentStudent() throws Exception {
        mockMvc.perform(delete(BASEURL+"/kickout?studentId="+studentId))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Kick out non existent student")
    public void kickOutNonExistentStudent() throws Exception {
        mockMvc.perform(delete(BASEURL+"/kickout?studentId="+(studentId+1)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("studentNotFound")));
    }
}
