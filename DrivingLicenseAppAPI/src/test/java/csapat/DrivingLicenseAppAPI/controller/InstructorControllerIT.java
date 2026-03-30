package csapat.DrivingLicenseAppAPI.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import csapat.DrivingLicenseAppAPI.dto.InstructorUpdate;
import csapat.DrivingLicenseAppAPI.entity.*;
import csapat.DrivingLicenseAppAPI.repository.*;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;

//29db
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
    private String BASEURL = "http://localhost:8080/instructor";

    @BeforeEach
    public void setup() {
        Users student = userRepository.save(new Users("testUser1", "registerStudent1", "test2@gmail.com", "06701111112", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));
        Users instructor = userRepository.save(new Users("testUser1", "registerStudent1", "test3@gmail.com", "06701111113", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));
        Users schoolOwner = userRepository.save(new Users("testUser1", "registerStudent1", "tes4t@gmail.com", "06701111114", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));
        Users user = userRepository.save(new Users("testUser1", "registerStudent1", "test1@gmail.com", "06701111111", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));

        School testSchool = schoolRepository.save(new School("schoolName", "schoolTest@gmail.com", "06706294719", "Tolna", "Nagykónyi", "sfafsafasf", "afsfassaf", schoolOwner));
        schoolRepository.save(testSchool);

        Instructors testInstructor = instructorRepository.save(new Instructors(testSchool, instructor));
        Students testStudent = studentRepository.save(new Students(student, testSchool, drivingLicenseCategoryRepository.findById(1L).get(), testInstructor));
        instructorJoinRequestRepository.save(new InstructorJoinRequest(testStudent, testInstructor, false));
        InstructorJoinRequest testInstructorJoinRequest = instructorJoinRequestRepository.save(new InstructorJoinRequest(testStudent, testInstructor));
        DrivingLessonRequest testDrivingLessonRequest = drivingLessonRequestRepository.save(new DrivingLessonRequest("", new Date(), new Date(), new Date(), testStudent, testInstructor));

        instructorId = testInstructor.getId();
        drivingLessonRequestId = testDrivingLessonRequest.getId();
        studentId = testStudent.getId();
        joinRequestId = testInstructorJoinRequest.getId();
    }

    //Csatlakozási kérelmek
    @Test
    @DisplayName("Accept existent join request")
    public void acceptExistentJoinRequest() throws Exception {
        JsonNode requestBody  = createRequestBodyForHandleRequest(joinRequestId, "accept");
        mockMvc.perform(post(BASEURL + "/handleJoinRequest").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Refuse existent join request")
    public void refuseExistentJoinRequest() throws Exception {
        JsonNode requestBody  = createRequestBodyForHandleRequest(joinRequestId, "refuse");
        mockMvc.perform(post(BASEURL + "/handleJoinRequest").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Handle non existent join request")
    public void handleNonExistentJoinRequest() throws Exception {
        JsonNode requestBody  = createRequestBodyForHandleRequest(joinRequestId+1, "accept");
        mockMvc.perform(post(BASEURL + "/handleJoinRequest").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", Is.is("requestNotFound")));
    }

    @Test
    @DisplayName("Handle existent request with invalid status")
    public void handleExistentRequestWithInvalidStatus() throws Exception {
        JsonNode requestBody  = createRequestBodyForHandleRequest(joinRequestId, "acceptrrasd");
        mockMvc.perform(post(BASEURL + "/handleJoinRequest").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", Is.is("invalidStatus")));
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
                .andExpect(jsonPath("$", Is.is("instructorNotFound")));
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
                .andExpect(jsonPath("$", Is.is("instructorNotFound")));
    }

    @Test
    @DisplayName("Accept an existent drivingLessonRequest")
    public void acceptExistentDrivingLessonRequest() throws Exception {
        JsonNode requestBody  = createRequestBodyForHandleRequest(drivingLessonRequestId, "accept");
        mockMvc.perform(post(BASEURL + "/handleDrivingLessonRequest").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Refuse an existent drivingLessonRequest")
    public void refuseExistentDrivingLessonRequest() throws Exception {
        JsonNode requestBody  = createRequestBodyForHandleRequest(drivingLessonRequestId, "refuse");
        mockMvc.perform(post(BASEURL + "/handleDrivingLessonRequest").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Handle a non existent drivingLessonRequest")
    public void handleNonExistentDrivingLessonRequest() throws Exception {
        JsonNode requestBody  = createRequestBodyForHandleRequest(drivingLessonRequestId + 1, "accept");
        mockMvc.perform(post(BASEURL + "/handleDrivingLessonRequest").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", Is.is("requestNotFound")));
    }

    @Test
    @DisplayName("Handle drivingLessonRequest with invalid status")
    public void handleDrivingLessonWithInvalidStatus() throws Exception {
        JsonNode requestBody  = createRequestBodyForHandleRequest(drivingLessonRequestId, "acceptasdads");
        mockMvc.perform(post(BASEURL + "/handleDrivingLessonRequest").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", Is.is("invalidStatus")));
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
    }

    @Test
    @DisplayName("Update non existent instructor")
    public void updateNonExistentInstructor() throws Exception {
    }

    @Test
    @DisplayName("Update non existent vehicle of instructor")
    public void updateNonExistentVehicleOfInstructor() throws Exception {
    }

    @Test
    @DisplayName("Update existent vehicle with non existent fuel type")
    public void updateExistentVehicleWithNonExistentFuelType() throws Exception {
    }

    @Test
    @DisplayName("Update existent vehicle with non existent vehicle type")
    public void updateExistentVehicleWithNonExistentVehicleType() throws Exception {
    }

    @Test
    @DisplayName("Update existent vehicle with invalid license plate")
    public void updateExistentVehicleWithInvalidLicensePlate() throws Exception {
    }

    private InstructorUpdate createRequestBodyForUpdate(String promoText, Long vehicleId, String vehicleName, String licensePlate, Long fuelTypeId, Long vehicleTypeId) {
        return new InstructorUpdate(promoText, vehicleId, vehicleName, licensePlate, fuelTypeId, vehicleTypeId);
    }

    //Keresés
    @Test
    @DisplayName("Search instructor with valid datas")
    public void searchInstructorWithValidDatas() throws Exception {
    }

    @Test
    @DisplayName("Search instructor with non existent fuel type")
    public void searchInstructorWithNonExistentFuelType() throws Exception {
    }

    @Test
    @DisplayName("Search instructor with non existent school")
    public void searchInstructorWithNonExistentSchool() throws Exception {
    }

    @Test
    @DisplayName("Search instructor with non existent license category")
    public void searchInstructorWithNonExistentLicenseCategory() throws Exception {
    }

    //Id alapján
    @Test
    @DisplayName("Get existent instructor by id")
    public void getExistentInstructorById() throws Exception {
    }

    @Test
    @DisplayName("Get non existent instructor by id")
    public void getNonExistentInstructorById() throws Exception {
    }

    @Test
    @DisplayName("Get students of existent instructor")
    public void getStudentsOfExistentInstructor() throws Exception {
    }

    @Test
    @DisplayName("Get students of non existent instructor")
    public void getStudentsOfNonExistestInstructor() throws Exception {
    }

    //diak kirugasa
    @Test
    @DisplayName("Kick out existent student")
    public void kickOutExistentStudent() throws Exception {
    }

    @Test
    @DisplayName("Kick out non existent student")
    public void kickOutNonExistentStudent() throws Exception {
    }

    @Test
    @DisplayName("Kick out invalid student")
    public void kickOutInvalidStudent() throws Exception {
    }
}
