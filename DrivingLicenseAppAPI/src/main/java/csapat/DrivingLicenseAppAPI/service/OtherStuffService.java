package csapat.DrivingLicenseAppAPI.service;

import com.opencsv.CSVReader;
import csapat.DrivingLicenseAppAPI.entity.*;
import csapat.DrivingLicenseAppAPI.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class OtherStuffService {

    private final PaymentMethodRepository paymentMethodRepository;
    private final EducationRepository educationRepository;
    private final FuelTypeRepository fuelTypeRepository;
    private final StatusRepository statusRepository;
    private final VehicleTypeRepository vehicleTypeRepository;

    public ResponseEntity<List<PaymentMethod>> getAllPaymentMethod() {
        try {
            return ResponseEntity.ok().body(paymentMethodRepository.getAllPaymentMethod());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().build();
        }
    }

    public ResponseEntity<List<FuelType>> getAllFuelType() {
        try {
            return ResponseEntity.ok().body(fuelTypeRepository.getAllFuelType());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<List<Education>> getAllEducation() {
        try {
            return ResponseEntity.ok().body(educationRepository.getAllEducation());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<List<Status>> getAllStatus() {
        try {
            return ResponseEntity.ok().body(statusRepository.getAllStatus());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<List<String>> getAllTown() {
        try {
            FileReader fileReader = new FileReader(new File("src/main/java/csapat/DrivingLicenseAppAPI/service/other/townList.csv"));
            CSVReader reader = new CSVReader(fileReader);

            List<String[]> allRecords = reader.readAll();
            List<String> townName = new ArrayList<String>();
            for (int i = 1; i < allRecords.size(); i++) {
                townName.add(allRecords.get(i)[0]);
            }

            return ResponseEntity.ok().body(townName);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<List<VehicleType>> getAllVehicleType() {
        try {
            return ResponseEntity.ok().body(vehicleTypeRepository.getAllVehicleType());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}

/*
 * HTTP STATUS KODOK:
 *   - 200: Sikeres muvelet
 *   - 404: Not Found
 *   - 409: Mar foglalt nev
 *   - 415: Unsupported Media Type --> Ha az adott adat invalid
 *   - 422: Hianyzo parameter/response body
 *   - 500: Internal Server Error
 * */