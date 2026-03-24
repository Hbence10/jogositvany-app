package csapat.DrivingLicenseAppAPI.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import csapat.DrivingLicenseAppAPI.dto.InstructorUpdate;
import csapat.DrivingLicenseAppAPI.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

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
    private final InstructorRepository instructorRepository,
    private final SchoolRepository schoolRepository;
    private final StudentRepository studentRepository;
    private final InstructorJoinRequestRepository instructorJoinRequestRepository;
    private final DrivingLessonRequestRepository drivingLessonRequestRepository;
    private final VehicleRepository vehicleRepository;
    private final FuelTypeRepository fuelTypeRepository;
    private final VehicleTypeRepository vehicleTypeRepository;

    @Autowired
    public InstructorControllerIT(FuelTypeRepository fuelTypeRepository, VehicleTypeRepository vehicleTypeRepository, VehicleRepository vehicleRepository, DrivingLessonRequestRepository drivingLessonRequestRepository, InstructorJoinRequestRepository instructorJoinRequestRepository, StudentRepository studentRepository, SchoolRepository schoolRepository, InstructorRepository instructorRepository, UserRepository userRepository, ObjectMapper objectMapper, MockMvc mockMvc) {
        this.fuelTypeRepository = fuelTypeRepository;
        this.vehicleTypeRepository = vehicleTypeRepository;
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

    @BeforeEach
    public void setup() {

    }

    //Csatlakozási kérelmek
    @Test
    @DisplayName("")
    public void acceptExistentJoinRequest() throws Exception {
    }

    @Test
    @DisplayName("")
    public void refuseExistentJoinRequest() throws Exception {
    }

    @Test
    @DisplayName("")
    public void handleNonExistentJoinRequest() throws Exception {
    }

    @Test
    @DisplayName("")
    public void handleExistentRequestWithInvalidStatus() throws Exception {
    }

    @Test
    @DisplayName("")
    public void getAllJoinRequestByExistentInstructor() throws Exception {
    }

    @Test
    @DisplayName("")
    public void getAllJoinRequestByNonExistentInstructor() throws Exception {
    }

    //Vezetési óra kérelmek
    @Test
    @DisplayName("")
    public void getAllDrivingLessonRequestByExistentInstructor() throws Exception {
    }

    @Test
    @DisplayName("")
    public void getAllDrivingLessonRequestByNonExistentInstructor() throws Exception {
    }

    @Test
    @DisplayName("")
    public void acceptExistentDrivingLessonRequest() throws Exception {
    }

    @Test
    @DisplayName("")
    public void refuseExistentDrivingLessonRequest() throws Exception {
    }

    @Test
    @DisplayName("")
    public void handleNonExistentDrivingLessonRequest() throws Exception {
    }

    @Test
    @DisplayName("")
    public void handleDrivingLessonWithInvalidStatus() throws Exception {
    }

    JsonNode createRequestBodyForHandleRequest(Long requestId, String status) {
        JsonNode returnObject = objectMapper.createObjectNode();
        ((ObjectNode) returnObject).put("requestId", requestId);
        ((ObjectNode) returnObject).put("status", status);
        return returnObject;
    }

    //Frissites
    @Test
    @DisplayName("")
    public void updateExistentInstructorWithValidData() throws Exception {
    }

    @Test
    @DisplayName("")
    public void updateNonExistentInstructor() throws Exception {
    }

    @Test
    @DisplayName("")
    public void updateNonExistentVehicleOfInstructor() throws Exception {
    }

    @Test
    @DisplayName("")
    public void updateExistentVehicleWithNonExistentFuelType() throws Exception {
    }

    @Test
    @DisplayName("")
    public void updateExistentVehicleWithNonExistentVehicleType() throws Exception {
    }

    @Test
    @DisplayName("")
    public void updateExistentVehicleWithInvalidLicensePlate() throws Exception {
    }

    private InstructorUpdate createRequestBodyForUpdate(String promoText, Long vehicleId, String vehicleName, String licensePlate, Long fuelTypeId, Long vehicleTypeId) {
        return new InstructorUpdate(promoText, vehicleId, vehicleName, licensePlate, fuelTypeId, vehicleTypeId);
    }

    //Keresés
    @Test
    @DisplayName("")
    public void searchInstructorWithValidDatas() throws Exception {
    }

    @Test
    @DisplayName("")
    public void searchInstructorWithNonExistentFuelType() throws Exception {
    }

    @Test
    @DisplayName("")
    public void searchInstructorWithNonExistentSchool() throws Exception {
    }

    @Test
    @DisplayName("")
    public void searchInstructorWithNonExistentLicenseCategory() throws Exception {
    }

    //Id alapján
    @Test
    @DisplayName("")
    public void getExistentInstructorById() throws Exception {
    }

    @Test
    @DisplayName("")
    public void getNonExistentInstructorById() throws Exception {
    }

    @Test
    @DisplayName("")
    public void getStudentsOfExistentInstructor() throws Exception {
    }

    @Test
    @DisplayName("")
    public void getStudentsOfNonExistestInstructor() throws Exception {
    }

    @Test
    @DisplayName("")
    public void kickOutExistentStudent() throws Exception {
    }

    @Test
    @DisplayName("")
    public void kickOutNonExistentStudent() throws Exception {
    }

    @Test
    @DisplayName("")
    public void kickOutInvalidStudent() throws Exception {
    }
}
