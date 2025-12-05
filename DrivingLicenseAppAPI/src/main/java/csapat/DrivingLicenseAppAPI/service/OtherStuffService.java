package csapat.DrivingLicenseAppAPI.service;

import com.opencsv.CSVReader;
import csapat.DrivingLicenseAppAPI.entity.DrivingLicenseCategory;
import csapat.DrivingLicenseAppAPI.entity.Education;
import csapat.DrivingLicenseAppAPI.entity.FuelType;
import csapat.DrivingLicenseAppAPI.entity.PaymentMethod;
import csapat.DrivingLicenseAppAPI.repository.DrivingLicenseCategoryRepository;
import csapat.DrivingLicenseAppAPI.repository.EducationRepository;
import csapat.DrivingLicenseAppAPI.repository.FuelTypeRepository;
import csapat.DrivingLicenseAppAPI.repository.PaymentMethodRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class OtherStuffService {

    private final PaymentMethodRepository paymentMethodRepository;
    private final DrivingLicenseCategoryRepository drivingLicenseCategoryRepository;
    private final EducationRepository educationRepository;
    private final FuelTypeRepository fuelTypeRepository;

    public ResponseEntity<List<PaymentMethod>> getAllPaymentMethod() {
        return ResponseEntity.ok().body(paymentMethodRepository.getAllPayMethod());
    }

    public ResponseEntity<List<DrivingLicenseCategory>> getAllCategory() {
        return ResponseEntity.ok().body(drivingLicenseCategoryRepository.getAllDrivingLicenseCategory());
    }

    public ResponseEntity<List<FuelType>> getAllFuelType() {
        return ResponseEntity.ok().body(fuelTypeRepository.getAllFuelType());
    }

    public ResponseEntity<List<Education>> getAllEducation() {
        return ResponseEntity.ok().body(educationRepository.getAllEducation());
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
}
