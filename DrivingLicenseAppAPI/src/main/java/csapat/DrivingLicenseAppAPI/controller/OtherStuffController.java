package csapat.DrivingLicenseAppAPI.controller;

import csapat.DrivingLicenseAppAPI.entity.DrivingLicenseCategory;
import csapat.DrivingLicenseAppAPI.entity.Education;
import csapat.DrivingLicenseAppAPI.entity.FuelType;
import csapat.DrivingLicenseAppAPI.entity.PaymentMethod;
import csapat.DrivingLicenseAppAPI.service.OtherStuffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@RequestMapping("")
@RequiredArgsConstructor
public class OtherStuffController {

    private final OtherStuffService otherStuffService;

    @Operation(summary = "Fizetési módszerek", description = "Az összes fizetési módszer megszerzése.")
    @ApiResponses({
            @ApiResponse(responseCode = "500", description = "A server okozta hiba."),
            @ApiResponse(responseCode = "200", description = "Sikeres lekérés")
    })
    @GetMapping("/paymentMethod")
    public ResponseEntity<List<PaymentMethod>> getAllPaymentMethod() {
        return otherStuffService.getAllPaymentMethod();
    }

    @Operation(summary = "Jogosítvány kategóriák", description = "Az összes jogosítvány kategória megszerzése.")
    @ApiResponses({
            @ApiResponse(responseCode = "500", description = "A server okozta hiba."),
            @ApiResponse(responseCode = "200", description = "Sikeres lekérés")
    })
    @GetMapping("/licenseCategory")
    public ResponseEntity<List<DrivingLicenseCategory>> getAllCategory() {
        return otherStuffService.getAllCategory();
    }

    @Operation(summary = "Üzemanyag tipusok", description = "Az összes üzemanyag tipus megszerzése.")
    @ApiResponses({
            @ApiResponse(responseCode = "500", description = "A server okozta hiba."),
            @ApiResponse(responseCode = "200", description = "Sikeres lekérés")
    })
    @GetMapping("/fuelType")
    public ResponseEntity<List<FuelType>> getAllFuelType() {
        return otherStuffService.getAllFuelType();
    }

    @Operation(summary = "Iskolai végzettségek", description = "Az összes iskolai végzettség megszerzése.")
    @ApiResponses({
            @ApiResponse(responseCode = "500", description = "A server okozta hiba."),
            @ApiResponse(responseCode = "200", description = "Sikeres lekérés")
    })
    @GetMapping("/education")
    public ResponseEntity<List<Education>> getAllEducation() {
        return otherStuffService.getAllEducation();
    }

    @Operation(summary = "Városok megszerzése", description = "Az összes magyar város visszaszerzése")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres lekérdezés."),
            @ApiResponse(responseCode = "500", description = "Hiba történt a városok kiolvasäsakor.")
    })
    @GetMapping("/town")
    public ResponseEntity<List<String>> getAllTown() {
        return otherStuffService.getAllTown();
    }
}
