package csapat.DrivingLicenseAppAPI.controller;

import csapat.DrivingLicenseAppAPI.entity.DrivingLicenseCategory;
import csapat.DrivingLicenseAppAPI.entity.Education;
import csapat.DrivingLicenseAppAPI.entity.FuelType;
import csapat.DrivingLicenseAppAPI.entity.PaymentMethod;
import csapat.DrivingLicenseAppAPI.service.OtherStuffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class OtherStuffController {

    private final OtherStuffService otherStuffService;

    @GetMapping("/paymentMethod")
    public ResponseEntity<List<PaymentMethod>> getAllPaymentMethod(){
        return otherStuffService.getAllPaymentMethod();
    }

    @GetMapping("/licenseCategory")
    public ResponseEntity<List<DrivingLicenseCategory>> getAllCategory(){
        return otherStuffService.getAllCategory();
    }

    @GetMapping("/fuelType")
    public ResponseEntity<List<FuelType>> getAllFuelType(){
        return otherStuffService.getAllFuelType();
    }

    @GetMapping("/education")
    public ResponseEntity<List<Education>> getAllEducation(){
        return otherStuffService.getAllEducation();
    }
}
