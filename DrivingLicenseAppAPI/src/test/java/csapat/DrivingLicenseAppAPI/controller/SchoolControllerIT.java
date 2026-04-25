package csapat.DrivingLicenseAppAPI.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import csapat.DrivingLicenseAppAPI.dto.SchoolDto;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

//33
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource(locations = "classpath:test-application.properties")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class SchoolControllerIT {

    MockMvc mockMvc;
    private final SchoolRepository schoolRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;
    private final SchoolJoinRequestRepository schoolJoinRequestRepository;
    private final DrivingLicenseCategoryRepository drivingLicenseCategoryRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public SchoolControllerIT(MockMvc mockMvc, StudentRepository studentRepository, InstructorRepository instructorRepository, SchoolRepository schoolRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, SchoolJoinRequestRepository schoolJoinRequestRepository, DrivingLicenseCategoryRepository drivingLicenseCategoryRepository, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.schoolRepository = schoolRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.schoolJoinRequestRepository = schoolJoinRequestRepository;
        this.drivingLicenseCategoryRepository = drivingLicenseCategoryRepository;
        this.objectMapper = objectMapper;
        this.studentRepository = studentRepository;
        this.instructorRepository = instructorRepository;
    }

    private Long testSchoolId;
    private Long testJoinRequestId;
    private Long testInstructorId;
    private String BASEURL = "http://localhost:8080/school";

    @BeforeEach
    public void setup() {
        Users owner = userRepository.save(new Users("testUser1", "registerStudent1", "test@gmail.com", "06701111111", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));
        Users owner2 = userRepository.save(new Users("testUser4", "registerStudent4", "test4@gmail.com", "06701121111", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));
        Users testUser = userRepository.save(new Users("testUser2", "registerStudent2", "test2@gmail.com", "06701111112", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));
        Users student = userRepository.save(new Users("testUser8", "registerStudent1", "test8@gmail.com", "06701151112", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));
        Users instructor = userRepository.save(new Users("testUser9", "registerStudent1", "test9@gmail.com", "06701191113", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));

        schoolRepository.save(new School("schoolName2", "schoolTest2@gmail.com", "06706894711", "Tolna", "Nagykónyi", "sfafsafasf", "afsfassaf", owner2));
        School testSchool = schoolRepository.save(new School("schoolName", "schoolTest@gmail.com", "06706894719", "Tolna", "Nagykónyi", "sfafsafasf", "afsfassaf", owner));
        testSchoolId = testSchool.getId();

        Instructors testInstructor = instructorRepository.save(new Instructors(testSchool, instructor));
        Students testStudent = studentRepository.save(new Students(student, testSchool, drivingLicenseCategoryRepository.findById(1L).get(), testInstructor));

        SchoolJoinRequest testRequest = schoolJoinRequestRepository.save(new SchoolJoinRequest(testUser, testSchool, drivingLicenseCategoryRepository.findById(1L).get()));
        testUser.setSchoolJoinRequestList(new ArrayList<>(Arrays.asList(testRequest)));
        userRepository.save(testUser);
        testInstructorId = testInstructor.getId();

        testJoinRequestId = testRequest.getId();
    }

    @Test
    @DisplayName("Accept existent join Request from user")
    public void acceptExistentJoinRequestFromUser() throws Exception {
        mockMvc.perform(post(BASEURL + "/" + testJoinRequestId + "/joinRequest").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createBodyForJoinRequestHandling("accept"))))
                .andExpect(status().isOk());

        Assertions.assertEquals(true, schoolJoinRequestRepository.findById(testJoinRequestId).get().getIsAccepted(), "It should be accepted");
    }

    @Test
    @DisplayName("Refuse existent join request from user")
    public void refuseExistentJoinRequestFromUser() throws Exception {
        mockMvc.perform(post(BASEURL + "/" + testJoinRequestId + "/joinRequest").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createBodyForJoinRequestHandling("refuse"))))
                .andExpect(status().isOk());

        Assertions.assertEquals(false, schoolJoinRequestRepository.findById(testJoinRequestId).get().getIsAccepted(), "It should be refused");
    }

    @Test
    @DisplayName("Handle existent request with invalid status")
    public void handleJoinRequestWithInvalidStatus() throws Exception {
        mockMvc.perform(post(BASEURL + "/" + testJoinRequestId + "/joinRequest").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createBodyForJoinRequestHandling("asfas"))))
                .andExpect(status().is4xxClientError())
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", is("invalidStatus")))
        ;
    }

    @Test
    @DisplayName("Handle non-existent request")
    public void handleNonExistentJoinRequest() throws Exception {
        mockMvc.perform(post(BASEURL + "/" + 3214124 + "/joinRequest").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createBodyForJoinRequestHandling("accept"))))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("requestNotFound")));
    }

    private JsonNode createBodyForJoinRequestHandling(String status) {
        JsonNode returnObject = objectMapper.createObjectNode();
        ((ObjectNode) returnObject).put("status", status);
        return returnObject;
    }

    @Test
    @DisplayName("Update existent school with valid datas")
    public void updateExistentSchoolWithValidDatas() throws Exception {
        mockMvc.perform(put(BASEURL + "/" + testSchoolId).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createSchoolDto("updateName", "update@gmail.com", "06701234156", "updateCounty", "updateTown", "updateAddress", "updatePromoText", null))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(Integer.valueOf(testSchoolId + ""))))
                .andExpect(jsonPath("$.name", is("updateName")))
                .andExpect(jsonPath("$.email", is("update@gmail.com")))
                .andExpect(jsonPath("$.phone", is("06701234156")))
                .andExpect(jsonPath("$.country", is("updateCounty")))
                .andExpect(jsonPath("$.town", is("updateTown")))
                .andExpect(jsonPath("$.address", is("updateAddress")))
                .andExpect(jsonPath("$.promoText", is("updatePromoText")))
        ;
    }

    @Test
    @DisplayName("Update none existent school.")
    public void updateNonExistentSchool() throws Exception {
        mockMvc.perform(put(BASEURL + "/" + (testSchoolId + 1)).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createSchoolDto("updateName", "update@gmail.com", "06701234156", "updateCounty", "updateTown", "updateAddress", "updatePromoText", null))))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("schoolNotFound")));
    }

    @Test
    @DisplayName("Update existent school with invalid phone")
    public void updateExistentSchoolWithInvalidPhone() throws Exception {
        mockMvc.perform(put(BASEURL + "/" + testSchoolId).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createSchoolDto("updateName", "update@gmail.com", "06121234156", "updateCounty", "updateTown", "updateAddress", "updatePromoText", null))))
                .andExpect(status().is4xxClientError())
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", is("invalidPhone")));
        ;
    }

    @Test
    @DisplayName("Update existent school with invalid email")
    public void updateExistentSchoolWithInvalidEmail() throws Exception {
        mockMvc.perform(put(BASEURL + "/" + testSchoolId).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createSchoolDto("updateName", "updategmail.com", "06701234156", "updateCounty", "updateTown", "updateAddress", "updatePromoText", null))))
                .andExpect(status().is4xxClientError())
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", is("invalidEmail")));
        ;
    }

    @Test
    @DisplayName("Update existent school with duplicated name")
    public void updateExistentSchoolWithDuplicatedName() throws Exception {
        mockMvc.perform(put(BASEURL + "/" + testSchoolId).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createSchoolDto("schoolName2", "update@gmail.com", "06701234156", "updateCounty", "updateTown", "updateAddress", "updatePromoText", null))))
                .andExpect(status().is(409))
                .andExpect(jsonPath("$.statusText", is("duplicateName")));
        ;
    }

    @Test
    @DisplayName("Update existent school with duplicated phone")
    public void updateExistentSchoolWithDuplicatedPhone() throws Exception {
        mockMvc.perform(put(BASEURL + "/" + testSchoolId).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createSchoolDto("updateName", "update@gmail.com", "06706894711", "updateCounty", "updateTown", "updateAddress", "updatePromoText", null))))
                .andExpect(status().is4xxClientError())
                .andExpect(status().is(409))
                .andExpect(jsonPath("$.statusText", is("duplicatePhone")));
        ;
    }

    @Test
    @DisplayName("Update existent school with duplicated email")
    public void updateExistentSchoolWithDuplicatedEmail() throws Exception {
        mockMvc.perform(put(BASEURL + "/" + testSchoolId).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createSchoolDto("updateName", "schoolTest2@gmail.com", "06701234156", "updateCounty", "updateTown", "updateAddress", "updatePromoText", null))))
                .andExpect(status().is4xxClientError())
                .andExpect(status().is(409))
                .andExpect(jsonPath("$.statusText", is("duplicateEmail")));
        ;
    }

    SchoolDto createSchoolDto(String schoolName, String email, String phoneNumber, String county, String town, String address, String promoText, Long ownerId) {
        return new SchoolDto(schoolName, email, phoneNumber, county, town, address, promoText, ownerId);
    }

    //Kerelmek:
    @Test
    @DisplayName("Get all join request of existent school")
    public void getAllJoinRequestOfExistentSchool() throws Exception {
        mockMvc.perform(get(BASEURL + "/" + testSchoolId + "/joinRequests"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @DisplayName("Get all join request of non existent school")
    public void getAllJoinRequestOfNonExistentSchool() throws Exception {
        mockMvc.perform(get(BASEURL + "/" + (testSchoolId + 1) + "/joinRequests"))
                .andExpect(status().isNotFound());
    }

    //Iskola torlese
    @Test
    @DisplayName("Delete existent user by id")
    public void deleteExistentSchool() throws Exception {
        Long sizeBeforeDelete = schoolRepository.countNotDeletedSchool(false);

        mockMvc.perform(delete(BASEURL + "/" + testSchoolId).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createBodyForJoinRequestHandling("accept"))))
                .andExpect(status().isOk());

        Long sizeAfterDelete = schoolRepository.countNotDeletedSchool(false);
        assertEquals(sizeBeforeDelete - 1, sizeAfterDelete);
    }

    @Test
    @DisplayName("Delete non-existent user")
    public void deleteNonExistentSchool() throws Exception {
        Long sizeBeforeDelete = schoolRepository.countNotDeletedSchool(false);

        mockMvc.perform(delete(BASEURL + "/" + (testSchoolId + 1)).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createBodyForJoinRequestHandling("accept"))))
                .andExpect(status().isNotFound());

        Long sizeAfterDelete = schoolRepository.countNotDeletedSchool(false);
        assertEquals(sizeBeforeDelete, sizeAfterDelete);
    }

    //Kereses
    @Test
    @DisplayName("Search school by registered town")
    public void searchSchoolsByExistentTown() throws Exception {
        mockMvc.perform(get(BASEURL + "/search?town=Nagykónyi"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @DisplayName("Search school by non registered town")
    public void searchSchoolByNonExistentTown() throws Exception {
        mockMvc.perform(get(BASEURL + "/search?town=NemLetezoVaros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    //Id alapjan lekeres
    @Test
    @DisplayName("Get existent school by id")
    public void getExistentSchoolById() throws Exception {
        mockMvc.perform(get(BASEURL + "/" + testSchoolId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(Integer.valueOf(testSchoolId + ""))));
    }

    @Test
    @DisplayName("Get none existent school by id.")
    public void getNonExistentSchoolById() throws Exception {
        mockMvc.perform(get(BASEURL + "/" + (testSchoolId + 1)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Create school with valid datas.")
    public void createSchoolWithValidDatas() throws Exception {
        Users owner = userRepository.save(new Users("testUser1", "registerStudent1", "test10@gmail.com", "06702111111", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));
        SchoolDto newSchool = createSchoolDto("updateName", "update@gmail.com", "06701234156", "updateCounty", "updateTown", "updateAddress", "updatePromoText", owner.getId());

        mockMvc.perform(post(BASEURL).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newSchool)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Create school with non existent owner.")
    public void createSchoolWithNonExistentOwner() throws Exception {
        Users owner = userRepository.save(new Users("testUser1", "registerStudent1", "test10@gmail.com", "06702111111", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));
        SchoolDto newSchool = createSchoolDto("updateName", "update@gmail.com", "06701234156", "updateCounty", "updateTown", "updateAddress", "updatePromoText", owner.getId() + 1);

        mockMvc.perform(post(BASEURL).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newSchool)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Create school with invalid email")
    public void createSchoolWithInvalidEmail() throws Exception {
        Users owner = userRepository.save(new Users("testUser1", "registerStudent1", "test10@gmail.com", "06702111111", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));
        SchoolDto newSchool = createSchoolDto("updateName", "updategmail.com", "06701234156", "updateCounty", "updateTown", "updateAddress", "updatePromoText", owner.getId());

        mockMvc.perform(post(BASEURL).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newSchool)))
                .andExpect(status().is4xxClientError())
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", is("invalidEmail")));
    }

    @Test
    @DisplayName("Create school with invalid phone")
    public void createSchoolWithInvalidPhone() throws Exception {
        Users owner = userRepository.save(new Users("testUser1", "registerStudent1", "test10@gmail.com", "06702111111", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));
        SchoolDto newSchool = createSchoolDto("updateName", "update@gmail.com", "06101234156", "updateCounty", "updateTown", "updateAddress", "updatePromoText", owner.getId());

        mockMvc.perform(post(BASEURL).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newSchool)))
                .andExpect(status().is4xxClientError())
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", is("invalidPhone")));
    }

    @Test
    @DisplayName("Create school with duplicated name")
    public void createSchoolWithDuplicatedName() throws Exception {
        Users owner = userRepository.save(new Users("testUser1", "registerStudent1", "test10@gmail.com", "06702111111", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));
        SchoolDto newSchool = createSchoolDto("schoolName", "updat213e@gmail.com", "06201244156", "updateCounty", "updateTown", "updateAddress", "updatePromoText", owner.getId());

        mockMvc.perform(post(BASEURL).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newSchool)))
                .andExpect(status().is4xxClientError())
                .andExpect(status().is(409))
                .andExpect(jsonPath("$.statusText", is("duplicateName")));
    }

    @Test
    @DisplayName("Create school with duplicated email")
    public void createSchoolWithDuplicatedEmail() throws Exception {
        Users owner = userRepository.save(new Users("testUser1", "registerStudent1", "test10@gmail.com", "06702111111", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));
        SchoolDto newSchool = createSchoolDto("update312Name", "schoolTest2@gmail.com", "06201214156", "updateCounty", "updateTown", "updateAddress", "updatePromoText", owner.getId());

        mockMvc.perform(post(BASEURL).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newSchool)))
                .andExpect(status().is4xxClientError())
                .andExpect(status().is(409))
                .andExpect(jsonPath("$.statusText", is("duplicateEmail")));
    }

    @Test
    @DisplayName("Create school with duplicated phone")
    public void createSchoolWithDuplicatedPhone() throws Exception {
        Users owner = userRepository.save(new Users("testUser1", "registerStudent1", "test10@gmail.com", "06702111111", new Date(), "male", passwordEncoder.encode("test5.Asd"), new Education(1L, "Általános Iskola"), passwordEncoder.encode("aaaaaaaaaa")));
        SchoolDto newSchool = createSchoolDto("update312Name", "school2Test2@gmail.com", "06706894711", "updateCounty", "updateTown", "updateAddress", "updatePromoText", owner.getId());

        mockMvc.perform(post(BASEURL).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newSchool)))
                .andExpect(status().is4xxClientError())
                .andExpect(status().is(409))
                .andExpect(jsonPath("$.statusText", is("duplicatePhone")));
    }

    //
    @Test
    @DisplayName("Get all student of existent school")
    public void getAllStudentsOfExistentSchool() throws Exception {
        mockMvc.perform(get(BASEURL + "/users?role=students&schoolId=" + testSchoolId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @DisplayName("Get all instructor of existent school.")
    public void getAllInstructorOfExistentSchool() throws Exception {
        mockMvc.perform(get(BASEURL + "/users?role=instructors&schoolId=" + testSchoolId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @DisplayName("Get all members of non existent school")
    public void getAllMemberOfNonExistentSchool() throws Exception {
        mockMvc.perform(get(BASEURL + "/users?role=instructors&schoolId=" + (testSchoolId+1)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("schoolNotFound")));
    }

    @Test
    @DisplayName("Get all members of existent school with invalid role.")
    public void getAllMemberOfSchoolByInvalidRole() throws Exception {
        mockMvc.perform(get(BASEURL + "/users?role=asd&schoolId=" + testSchoolId))
                .andExpect(status().is(415))
                .andExpect(jsonPath("$", is("invalidUserType")));
    }

    @Test
    @DisplayName("Kick out existent instructor")
    public void kickoutExistentInstructor() throws Exception {
        mockMvc.perform(delete(BASEURL + "/kickout?instructorId=" + testInstructorId))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Kick out non existent instructor")
    public void kickoutNonExistentInstructor() throws Exception {
        mockMvc.perform(delete(BASEURL + "/kickout?instructorId=" + (testInstructorId + 1)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("instructorNotFound")));
    }

    @Test
    @DisplayName("Get all school")
    public void getAllSchool() throws Exception {
        Long allSchoolSize = schoolRepository.countNotDeletedSchool(false);
        mockMvc.perform(get(BASEURL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(Integer.valueOf(allSchoolSize + ""))));
    }
}

