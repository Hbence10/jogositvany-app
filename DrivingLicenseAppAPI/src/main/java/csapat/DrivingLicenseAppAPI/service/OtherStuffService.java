package csapat.DrivingLicenseAppAPI.service;

import csapat.DrivingLicenseAppAPI.dto.UserCard;
import csapat.DrivingLicenseAppAPI.entity.*;
import csapat.DrivingLicenseAppAPI.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
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
        List<String> townName = new ArrayList<String>(Arrays.asList("Abony", "Abaújszántó", "Ajka", "Albertirsa", "Baja", "Balassagyarmat", "Balatonalmádi", "Balatonboglár", "Balatonfüred", "Békés", "Békéscsaba", "Berettyóújfalu", "Bicske", "Bonyhád", "Budapest", "Cegléd", "Celldömölk", "Csorna", "Debrecen", "Dombóvár", "Dunaújváros", "Eger", "Érd", "Esztergom", "Győr", "Gyula", "Hódmezővásárhely", "Jászberény", "Kaposvár", "Kecskemét", "Komárom", "Miskolc", "Mohács", "Nagykanizsa", "Nyíregyháza", "Orosháza", "Paks", "Pécs", "Szeged", "Székesfehérvár", "Szolnok", "Szombathely", "Tatabánya", "Veszprém", "Zalaegerszeg"));
        return ResponseEntity.ok().body(townName);
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