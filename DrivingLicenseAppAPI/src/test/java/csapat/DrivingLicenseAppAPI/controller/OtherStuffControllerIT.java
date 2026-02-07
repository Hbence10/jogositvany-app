package csapat.DrivingLicenseAppAPI.controller;

import csapat.DrivingLicenseAppAPI.repository.*;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

//6db
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource("classpath:test-application.properties")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class OtherStuffControllerIT {

    private PaymentMethodRepository paymentMethodRepository;
    private EducationRepository educationRepository;
    private FuelTypeRepository fuelTypeRepository;
    private StatusRepository statusRepository;
    private VehicleTypeRepository vehicleTypeRepository;
    private final String BASE_URL = "http://localhost:8080";

    @Autowired
    public OtherStuffControllerIT(PaymentMethodRepository paymentMethodRepository, EducationRepository educationRepository, FuelTypeRepository fuelTypeRepository, StatusRepository statusRepository, VehicleTypeRepository vehicleTypeRepository, MockMvc mockMvc) {
        this.paymentMethodRepository = paymentMethodRepository;
        this.educationRepository = educationRepository;
        this.fuelTypeRepository = fuelTypeRepository;
        this.statusRepository = statusRepository;
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.mockMvc = mockMvc;
    }

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Get all payment method.")
    public void getAllPaymentMethod() throws Exception {
        long sizeOfPaymentMethods = paymentMethodRepository.count();

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL+"/paymentMethod"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", IsCollectionWithSize.hasSize(Integer.valueOf(sizeOfPaymentMethods+""))));
    }

    @Test
    @DisplayName("Get all fuel type.")
    public void getAllFuelType() throws Exception {
        long sizeOfFuelTypes = fuelTypeRepository.count();

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL+"/fuelType"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", IsCollectionWithSize.hasSize(Integer.valueOf(sizeOfFuelTypes+""))));
    }

    @Test
    @DisplayName("Get all education.")
    public void getAllEducation() throws Exception {
        long sizeOfEducations = educationRepository.count();

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL+"/education"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", IsCollectionWithSize.hasSize(Integer.valueOf(sizeOfEducations+""))));
    }

    @Test
    @DisplayName("Get all town.")
    public void getAllTown() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL+"/town"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", IsCollectionWithSize.hasSize(Integer.valueOf(213))));
    }

    @Test
    @DisplayName("Get all status.")
    public void getAllStatus() throws Exception {
        long sizeOfAllStatus = statusRepository.count();
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL+"/status"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", IsCollectionWithSize.hasSize(Integer.valueOf(sizeOfAllStatus+""))));
    }

    @Test
    @DisplayName("Get all vehicle type.")
    public void getAllVehicleType() throws Exception {
        long sizeOfAllVehicleType = vehicleTypeRepository.count();
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL+"/vehicleType"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", IsCollectionWithSize.hasSize(Integer.valueOf(sizeOfAllVehicleType+""))));
    }
}
