package csapat.DrivingLicenseAppAPI.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

//6db
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource("classpath:test-application.properties")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class OtherStuffControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getAllPaymentMethod() throws Exception {
    }

    @Test
    public void getAllFuelType() throws Exception {
    }

    @Test
    public void getAllEducation() throws Exception {
    }

    @Test
    public void getAllTown() throws Exception {
    }

    @Test
    public void getAllStatus() throws Exception {
    }

    @Test
    public void getAllVehicleType() throws Exception {
    }
}
