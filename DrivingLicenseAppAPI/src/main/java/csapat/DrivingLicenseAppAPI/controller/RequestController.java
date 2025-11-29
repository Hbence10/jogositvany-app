package csapat.DrivingLicenseAppAPI.controller;

import csapat.DrivingLicenseAppAPI.entity.InstructorJoinRequest;
import csapat.DrivingLicenseAppAPI.entity.SchoolJoinRequest;
import csapat.DrivingLicenseAppAPI.service.RequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/request")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @Operation(summary = "Iskolához való csatlakozás", description = "Az iskolához való csatlakozási kérelem küldése.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "3db attributuma van a body-nak. A schoolId a kivánt iskolához tartozó id, a userId az adott felhasználóhoz tartozó id, a requestedRole meg a kivánt szerep (student vagy instructor).")
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Nem létező iskola/felhasználó vagy rossz role-t add meg a felhasználó"),
            @ApiResponse(responseCode = "200", description = "Sikeres kérelem küldés"),
    })
    @PostMapping("/school")
    private ResponseEntity<Object> sendSchoolJoinRequest(@RequestBody Map<String, String> requestBody){
        return requestService.sendSchoolJoinRequest(Integer.valueOf(requestBody.get("schoolId")), Integer.valueOf(requestBody.get("userId")), requestBody.get("requestedRole"));
    }

    @Operation(summary = "Oktatóhoz való csatlakozás", description = "Az oktatóhoz való csatlakozási kérelem küldése.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "2db attributuma van. A studentId az adott tanulóhoz tartozó id, az instructorId az adott instructorhoz (oktatóhoz) tartozó id.")
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Nem létező student küldi a kérelmet vagy egy nem létező instructor-hoz szeretne csatlakozni a student."),
            @ApiResponse(responseCode = "200", description = "Sikeres kérelem küldés"),
    })
    @PostMapping("/instructor")
    private ResponseEntity<Object> sendInstructorJoinRequest(@RequestBody Map<String, Integer> requestBody){
        return requestService.sendInstructorJoinRequest(requestBody.get("studentId"), requestBody.get("instructorId"));
    }

    @Operation(summary = "Iskolai csatlakozás törlése", description = "Az iskolához való csatlakozási kérelem törlése")
    @Parameter(name = "id", description = "Az adott kérelemhez tartozó id.", required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "A diák egy nem létező kérelmet szeretne törölni."),
            @ApiResponse(responseCode = "200", description = "Sikeres kérelem küldés"),
    })
    @DeleteMapping("/school/{id}")
    private ResponseEntity<Object> deleteSchoolJoinRequest(@PathVariable("id") Integer id){
        return requestService.deleteSchoolJoinRequest(id);
    }

    @Operation(summary = "Oktatói csatlakozás törlése", description = "")
    @Parameter(name = "id", description = "Az adott kérelemhez tartozó id.", required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "A diák egy nem létező kérelmet szeretne törölni."),
            @ApiResponse(responseCode = "200", description = "Sikeres kérelem küldés"),
    })
    @DeleteMapping("/instructor/{id}")
    private ResponseEntity<Object> deleteInstructorJoinRequest(@PathVariable("id") Integer id){
        return requestService.deleteInstructorJoinRequest(id);
    }

    @Operation(summary = "Iskalához tartozó kérelmek", description = "Az adott iskolához tartozó csatlakozás kérelmek lekérdezése")
    @Parameter(name = "id", description = "Az adott iskolához tartozó id.", required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Egy nem létező iskolához tartozó kérelmek lekérdezése"),
            @ApiResponse(responseCode = "200", description = "Sikeres kérelem küldés"),
    })
    @GetMapping("/school/{id}")
    private ResponseEntity<List<SchoolJoinRequest>> getAllJoinRequestBySchool(@PathVariable("id") Integer id){
        return requestService.getAllJoinRequestBySchool(id);
    }

    @Operation(summary = "Oktatóhoz tartozó kérelmek", description = "Az adott oktatóhoz tartozó kérelmek lekérdezése")
    @Parameter(name = "id", description = "Az adott oktatóhoz tartozó id.", required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Egy nem létező oktatóhoz tartozó kérelmek lekérdezése"),
            @ApiResponse(responseCode = "200", description = "Sikeres kérelem küldés"),
    })
    @GetMapping("/instructor/{id}")
    private ResponseEntity<List<InstructorJoinRequest>> getAllJoinRequestByInstructor(@PathVariable("id") Integer id){
        return requestService.getAllJoinRequestByInstructor(id);
    }
}
