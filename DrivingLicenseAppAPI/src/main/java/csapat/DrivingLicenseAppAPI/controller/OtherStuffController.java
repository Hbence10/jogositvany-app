package csapat.DrivingLicenseAppAPI.controller;

import csapat.DrivingLicenseAppAPI.entity.*;
import csapat.DrivingLicenseAppAPI.service.OtherStuffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OtherStuffController {

    private final OtherStuffService otherStuffService;

    @Operation(summary = "Fizetési módszerek lekérdezése", description = "Az összes fizetési módszer lekérdezése.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres lekérés", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = PaymentMethod.class))
            )),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),
    })
    @GetMapping("/paymentMethod")
    public ResponseEntity<List<PaymentMethod>> getAllPaymentMethod() {
        return otherStuffService.getAllPaymentMethod();
    }

    @Operation(summary = "Jogosítvány kategóriák lekérdezése", description = "Az összes jogosítvány kategória lekérdezése.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres lekérdezés", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = DrivingLicenseCategory.class))
            )),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba", content = @Content)
    })
    @GetMapping("/licenseCategory")
    public ResponseEntity<List<DrivingLicenseCategory>> getAllCategory() {
        return otherStuffService.getAllCategory();
    }

    @Operation(summary = "Üzemanyag tipusok lekérdezése", description = "Az összes üzemanyag tipus lekérdezése.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres lekérdezés", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = FuelType.class))
            )),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba", content = @Content)
    })
    @GetMapping("/fuelType")
    public ResponseEntity<List<FuelType>> getAllFuelType() {
        return otherStuffService.getAllFuelType();
    }

    @Operation(summary = "Iskolai végzettségek lekérdezése", description = "Az összes iskolai végzettség lekérdezése.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres lekérdezés", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Education.class))
            )),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba", content = @Content)
    })
    @GetMapping("/education")
    public ResponseEntity<List<Education>> getAllEducation() {
        return otherStuffService.getAllEducation();
    }

    @Operation(summary = "Városok lekérdezése", description = "Az összes magyar város lekérdezése")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres lekérdezés", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = String.class))
            )),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba", content = @Content)
    })
    @GetMapping("/town")
    public ResponseEntity<List<String>> getAllTown() {
        return otherStuffService.getAllTown();
    }

    @Operation(summary = "Státuszok lekérdezése", description = "Az összes státusz lekérdezése.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres lekérdezés", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Status.class))
            )),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba", content = @Content)
    })
    @GetMapping("/status")
    public ResponseEntity<List<Status>> getAllStatus() {
        return otherStuffService.getAllStatus();
    }

    @Operation(summary = "Jármű típusok lekérdezése", description = "Az összes jármű típus lekérdezése")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres lekérdezés", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = VehicleType.class))
            )),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba", content = @Content)
    })
    @GetMapping("/vehicleType")
    public ResponseEntity<List<VehicleType>> getAllVehicleType() {
        return otherStuffService.getAllVehicleType();
    }
}
