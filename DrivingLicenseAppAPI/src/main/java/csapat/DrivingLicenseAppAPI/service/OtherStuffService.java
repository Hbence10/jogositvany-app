package csapat.DrivingLicenseAppAPI.service;

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

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class OtherStuffService {

    private final PaymentMethodRepository paymentMethodRepository;
    private final DrivingLicenseCategoryRepository drivingLicenseCategoryRepository;
    private final EducationRepository educationRepository;
    private final FuelTypeRepository fuelTypeRepository;

    public ResponseEntity<List<PaymentMethod>> getAllPaymentMethod(){
        return ResponseEntity.ok().body(paymentMethodRepository.findAll().stream().filter(paymentMethod -> !paymentMethod.getIsDeleted()).toList());
    }

    public ResponseEntity<List<DrivingLicenseCategory>> getAllCategory(){
        return ResponseEntity.ok().body(drivingLicenseCategoryRepository.findAll());
    }

    public ResponseEntity<List<FuelType>> getAllFuelType(){
        return ResponseEntity.ok().body(fuelTypeRepository.findAll());
    }

    public ResponseEntity<List<Education>> getAllEducation(){
        return ResponseEntity.ok().body(educationRepository.findAll());
    }
}
