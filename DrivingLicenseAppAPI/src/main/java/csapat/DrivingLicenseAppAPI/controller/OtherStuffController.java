package csapat.DrivingLicenseAppAPI.controller;

import csapat.DrivingLicenseAppAPI.entity.DrivingLicenseCategory;
import csapat.DrivingLicenseAppAPI.entity.Education;
import csapat.DrivingLicenseAppAPI.entity.FuelType;
import csapat.DrivingLicenseAppAPI.entity.PaymentMethod;
import csapat.DrivingLicenseAppAPI.service.OtherStuffService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "", description = "")
    @GetMapping("/paymentMethod")
    public ResponseEntity<List<PaymentMethod>> getAllPaymentMethod(){
        return otherStuffService.getAllPaymentMethod();
    }

    @Operation(summary = "", description = "")
    @GetMapping("/licenseCategory")
    public ResponseEntity<List<DrivingLicenseCategory>> getAllCategory(){
        return otherStuffService.getAllCategory();
    }

    @Operation(summary = "", description = "")
    @GetMapping("/fuelType")
    public ResponseEntity<List<FuelType>> getAllFuelType(){
        return otherStuffService.getAllFuelType();
    }

    @Operation(summary = "", description = "")
    @GetMapping("/education")
    public ResponseEntity<List<Education>> getAllEducation(){
        return otherStuffService.getAllEducation();
    }
}
