package csapat.DrivingLicenseAppAPI.controller;

import com.fasterxml.jackson.databind.JsonNode;
import csapat.DrivingLicenseAppAPI.entity.DrivingLessonRequest;
import csapat.DrivingLicenseAppAPI.entity.InstructorJoinRequest;
import csapat.DrivingLicenseAppAPI.entity.Instructors;
import csapat.DrivingLicenseAppAPI.service.InstructorService;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/instructor")
@RequiredArgsConstructor
public class InstructorController {

    private final InstructorService instructorService;

    @Operation(summary = "Oktatóhoz való jelentkezés", description = "Az oktatóhoz való jelentkezési kérelem eldöntése, hogy elfogadja-e a diák jelentkezését vagy nem.")
    @PostMapping("/handleRequest")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Az adott kérelem id-át (requestId) és a kérelemre adott választ (status) tartalmazza. A válasz lehet accept (elfogadva) vagy refuse (elutasitott).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres kérelem kezelés."),
            @ApiResponse(responseCode = "404", description = "Nem létező kérelem elfogadása vagy nem megfelelő válasz adása.")
    })
    private ResponseEntity<Object> handleJoinRequest(@RequestBody JsonNode requestBody) {
        return instructorService.handleRequest(requestBody.get("requestId").asInt(), requestBody.get("status").asText());
    }

    @Operation(summary = "Oktatóhoz tartozó csatlakozási kérelmek", description = "Az adott oktatóhoz tartozó kérelmek lekérdezése")
    @Parameter(name = "id", description = "Az adott oktatóhoz tartozó id.", required = true, in = ParameterIn.PATH)
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Egy nem létező oktatóhoz tartozó kérelmek lekérdezése"),
            @ApiResponse(responseCode = "200", description = "Sikeres kérelem küldés"),
    })
    @GetMapping("/{id}/request")
    private ResponseEntity<List<InstructorJoinRequest>> getAllJoinRequestByInstructor(@PathVariable("id") Integer id){
        return instructorService.getAllJoinRequestByInstructor(id);
    }

    @Operation(summary = "Oktató törlése", description = "Az oktató törlése id alapján.")
    @Parameter(name = "id", description = "Az oktatóhoz tartozó id.", in = ParameterIn.PATH)
    @DeleteMapping("/{id}")
    private ResponseEntity<Object> deleteInstructor(@PathVariable("id") Integer instructorId) {
        return instructorService.deleteInstructor(instructorId);
    }

    @Operation(summary = "Óra kérelmek lekérdezése", description = "Az adott oktatóhoz tartozó órakérelmek megszerzése")
    @Parameter(name = "id", description = "Az oktatóhoz tartozó id.", in = ParameterIn.PATH)
    @GetMapping("/{id}/drivingLessonRequest")
    private ResponseEntity<List<DrivingLessonRequest>> getDrivingLessonRequestByInstructor(@PathVariable("id") Integer instructorId){
        return instructorService.getDrivingLessonRequestByInstructor(instructorId);
    }

    @Operation(summary = "Oktató frissitése", description = "Oktató adatainak frissitése.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "A frissitet oktatóhoz tartozó object.")
    @PutMapping("")
    private ResponseEntity<Instructors> updateInstructor(@RequestBody Instructors updatedInstructor){
        return instructorService.updateInstructor(updatedInstructor);
    }

    @Operation(summary = "Tanuló felvétele", description = "Tanuló felvétele az oktatóhoz az által, hogy az oktató elfogadja-e a diák csatlakozási kérelmet.")
    @PostMapping("/addStudent/{id}")
    private ResponseEntity<Object> addStudent(@PathVariable("id") Integer joinRequestId){
        return instructorService.addStudent(joinRequestId);
    }

    @Operation(summary = "Vezetési óra kérelem kezelés", description = "Az oktató eldöntheti, hogy elfogadja vagy elutasitja a diák vezetési óra kérelmét.")
    @PostMapping("/handleDrivingLessonRequest")
    private ResponseEntity<Object> handleDrivingLessonRequest(@RequestBody JsonNode requestBody){
        return instructorService.handleDrivingLessonRequest(requestBody.get("requestId").asInt(), requestBody.get("status").asText());
    }

    @Operation(summary = "Oktatók keresése", description = "")
    @Parameters({

    })
    @ApiResponses({

    })
    @GetMapping("")
    private ResponseEntity<List<Instructors>> getInstructorsBySearch(@RequestParam(value = "name", defaultValue = "") String name, @RequestParam(value = "fuelTypeId", defaultValue = "1") Integer fuelTypeId){
        return instructorService.getInstructorsBySearch(name, fuelTypeId);
    }
}
