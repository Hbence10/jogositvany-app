package csapat.DrivingLicenseAppAPI.service;

import com.opencsv.CSVReader;
import csapat.DrivingLicenseAppAPI.dto.UserCard;
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
    private final UserRepository userRepository;
    private final DrivingLicenseCategoryRepository drivingLicenseCategoryRepository;

    public ResponseEntity<List<PaymentMethod>> getAllPaymentMethod() {
        return ResponseEntity.ok().body(paymentMethodRepository.getAllPaymentMethod());
    }

    public ResponseEntity<List<FuelType>> getAllFuelType() {
        return ResponseEntity.ok().body(fuelTypeRepository.getAllFuelType());
    }

    public ResponseEntity<List<Education>> getAllEducation() {
        return ResponseEntity.ok().body(educationRepository.getAllEducation());
    }

    public ResponseEntity<List<Status>> getAllStatus() {
        return ResponseEntity.ok().body(statusRepository.getAllStatus());
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
        return ResponseEntity.ok().body(vehicleTypeRepository.getAllVehicleType());
    }

    public ResponseEntity<List<UserCard>> getAllUser() {
        List<Users> users = userRepository.findByRoleAndIsDeleted(new Role(1L, "ROLE_user"), false);
        List<UserCard> returnList = new ArrayList<>();
        for (Users i : users) {
            returnList.add(new UserCard(i.getId(), i.getEmail(), i.getFirstName() + " " + i.getLastName()));
        }
        return ResponseEntity.ok(returnList);
    }

    public ResponseEntity<List<DrivingLicenseCategory>> getAllCategories() {
        return ResponseEntity.ok().body(drivingLicenseCategoryRepository.findAll());
    }
}