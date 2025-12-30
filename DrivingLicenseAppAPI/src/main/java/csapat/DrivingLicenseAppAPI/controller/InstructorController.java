package csapat.DrivingLicenseAppAPI.controller;

import com.fasterxml.jackson.databind.JsonNode;
import csapat.DrivingLicenseAppAPI.entity.DrivingLessonRequest;
import csapat.DrivingLicenseAppAPI.entity.InstructorJoinRequest;
import csapat.DrivingLicenseAppAPI.entity.Instructors;
import csapat.DrivingLicenseAppAPI.entity.Vehicle;
import csapat.DrivingLicenseAppAPI.service.InstructorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instructor")
@RequiredArgsConstructor
public class InstructorController {

    private final InstructorService instructorService;

    @Operation(summary = "Oktatóhoz való jelentkezés", description = "Az oktatóhoz való jelentkezési kérelem eldöntése, hogy elfogadja-e a diák jelentkezését vagy nem.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Az adott kérelem id-át (requestId) és a kérelemre adott választ (status) tartalmazza. A válasz lehet accept (elfogadva) vagy refuse (elutasitott).", required = true, content = @Content(
            mediaType = "application/json",
            schemaProperties = {
                    @SchemaProperty(name = "requestId", schema = @Schema(implementation = Integer.class, description = "A kérelemhez tartozó id.")),
                    @SchemaProperty(name = "status", schema = @Schema(implementation = String.class, description = "A kérelemre adott válasz, csak accept és refuse lehet."))
            }
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres kérelem kezelés.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Nem létező kérelem elfogadása vagy nem megfelelő válasz adása.", content = @Content),
            @ApiResponse(responseCode = "415", description = "Nem megfelelő status megadása", content = @Content),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),
    })
    @PostMapping("/handleJoinRequest")
    private ResponseEntity<Object> handleJoinRequest(@RequestBody JsonNode requestBody) {
        return instructorService.handleRequest(requestBody.get("requestId").asInt(), requestBody.get("status").asText());
    }

    @Operation(summary = "Oktatóhoz tartozó csatlakozási kérelmek", description = "Az adott oktatóhoz tartozó kérelmek lekérdezése")
    @Parameter(name = "id", description = "Az adott oktatóhoz tartozó id.", required = true, in = ParameterIn.PATH)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres kérelemek lekérdezése", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = InstructorJoinRequest.class))
            )),
            @ApiResponse(responseCode = "404", description = "Egy nem létező oktatóhoz tartozó kérelmek lekérdezése", content = @Content),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),
    })
    @GetMapping("/{id}/request")
    private ResponseEntity<List<InstructorJoinRequest>> getAllJoinRequestByInstructor(@PathVariable("id") Integer id) {
        return instructorService.getAllJoinRequestByInstructor(id);
    }

    @Operation(summary = "Oktató törlése", description = "Az oktató törlése id alapján.")
    @Parameter(name = "id", description = "Az oktatóhoz tartozó id.", in = ParameterIn.PATH)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres törlés", content = @Content),
            @ApiResponse(responseCode = "404", description = "Nem létező oktató törlése", content = @Content),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),
    })
    @DeleteMapping("/{id}")
    private ResponseEntity<Object> deleteInstructor(@PathVariable("id") Integer instructorId) {
        return instructorService.deleteInstructor(instructorId);
    }

    @Operation(summary = "Óra kérelmek lekérdezése", description = "Az adott oktatóhoz tartozó órakérelmek megszerzése")
    @Parameter(name = "id", description = "Az oktatóhoz tartozó id.", in = ParameterIn.PATH)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres lekérdezés", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = DrivingLessonRequest.class))
            )),
            @ApiResponse(responseCode = "404", description = "Nem létező oktató megadása", content = @Content),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),
    })
    @GetMapping("/{id}/drivingLessonRequest")
    private ResponseEntity<List<DrivingLessonRequest>> getDrivingLessonRequestByInstructor(@PathVariable("id") Integer instructorId) {
        return instructorService.getDrivingLessonRequestByInstructor(instructorId);
    }

    @Operation(summary = "Oktató frissitése", description = "Oktató adatainak frissitése.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "A frissitet oktatóhoz tartozó object.", required = true, content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Instructors.class)
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres frissités", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Instructors.class, description = "A frissitett oktató object-je")
            )),
            @ApiResponse(responseCode = "404", description = "Nem létező oktató frissitése", content = @Content),
            @ApiResponse(responseCode = "409", description = "Duplikált adat regisztrálása", content = @Content),
            @ApiResponse(responseCode = "415", description = "Nem megfelelő adatok megadása az autónál", content = @Content),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),
    })
    @PutMapping("")
    private ResponseEntity<Object> updateInstructor(@RequestBody Instructors updatedInstructor) {
        return instructorService.updateInstructor(updatedInstructor);
    }

    @Operation(summary = "Vezetési óra kérelem kezelés", description = "Az oktató eldöntheti, hogy elfogadja vagy elutasitja a diák vezetési óra kérelmét.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "", required = true, content = @Content(
            mediaType = "application/json",
            schemaProperties = {
                    @SchemaProperty(name = "requestId", schema = @Schema(implementation = Integer.class, description = "A kérelemhez tartozó id.")),
                    @SchemaProperty(name = "status", schema = @Schema(implementation = String.class, description = "A kérelemre adott válasz, csak accept és refuse lehet.")),
            }
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres kérelem kezelés", content = @Content),
            @ApiResponse(responseCode = "404", description = "Nem létező kérelem megadása", content = @Content),
            @ApiResponse(responseCode = "415", description = "Nem megfelelő status megadása", content = @Content),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),
    })
    @PostMapping("/handleDrivingLessonRequest")
    private ResponseEntity<Object> handleDrivingLessonRequest(@RequestBody JsonNode requestBody) {
        return instructorService.handleDrivingLessonRequest(requestBody.get("requestId").asInt(), requestBody.get("status").asText());
    }

    @Operation(summary = "Oktatók keresése", description = "")
    @Parameters({
            @Parameter(name = "fuelTypeId", description = "A keresett tankolási tipusnak az id-ja", required = false, in = ParameterIn.QUERY),
            @Parameter(name = "schoolId", description = "A keresett oktatóhoz tartozó iskolának az id-ja", required = false, in = ParameterIn.QUERY),
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres lekérdezés", content = @Content(
                    mediaType = "application/json"
            )),
            @ApiResponse(responseCode = "404", description = "Nem létező fuelType megadása", content = @Content),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),
    })
    @GetMapping("")
    private ResponseEntity<Object> getInstructorsBySearch(@RequestParam(name = "fuelType", defaultValue = "1") Integer fuelTypeId, @RequestParam("school") Integer schoolId) {
        return instructorService.getInstructorsBySearch(fuelTypeId, schoolId);
    }

    @Operation(summary = "Oktató id alapján.", description = "Oktató lekérdezése id alapján.")
    @Parameter(name = "id", description = "Az oktatóhoz tartozó id", required = true, in = ParameterIn.PATH)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres lekérdezés", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Instructors.class)
            )),
            @ApiResponse(responseCode = "404", description = "Egy nem létező oktató lekérése.", content = @Content),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),
    })
    @GetMapping("/{id}")
    private ResponseEntity<Instructors> getInstructorById(@PathVariable("id") Integer id) {
        return instructorService.getInstructorById(id);
    }

//    @Operation(summary = "Jármű hozzáadása.", description = "Az oktató tud járművet magához adni")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = ""),
//            @ApiResponse(responseCode = "404", description = ""),
//            @ApiResponse(responseCode = "409", description = ""),
//            @ApiResponse(responseCode = "415", description = ""),
//            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody"),
//            @ApiResponse(responseCode = "500", description = "A server okozta hiba."),
//    })
//    @PostMapping("/{id}/vehicle")
//    private ResponseEntity<Object> addVehicle(@RequestBody Vehicle addedVehicle, @PathVariable("id") Integer instructorId) {
//        return instructorService.addVehicle(addedVehicle, instructorId);
//    }
}
