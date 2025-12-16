package csapat.DrivingLicenseAppAPI.controller;

import com.fasterxml.jackson.databind.JsonNode;
import csapat.DrivingLicenseAppAPI.entity.DrivingLessonRequest;
import csapat.DrivingLicenseAppAPI.entity.ExamRequest;
import csapat.DrivingLicenseAppAPI.entity.InstructorJoinRequest;
import csapat.DrivingLicenseAppAPI.entity.SchoolJoinRequest;
import csapat.DrivingLicenseAppAPI.service.RequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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
            @ApiResponse(responseCode = "200", description = "Sikeres kérelem küldés"),
            @ApiResponse(responseCode = "404", description = "Nem létező iskola/felhasználó vagy rossz role-t add meg a felhasználó"),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody"),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba."),
    })
    @PostMapping("/school")
    private ResponseEntity<Object> sendSchoolJoinRequest(@RequestBody JsonNode requestBody){
        return requestService.sendSchoolJoinRequest(requestBody.get("schoolId").asInt(), requestBody.get("userId").asInt(), requestBody.get("requestedRole").asText());
    }

    @Operation(summary = "Oktatóhoz való csatlakozás", description = "Az oktatóhoz való csatlakozási kérelem küldése.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "2db attributuma van. A studentId az adott tanulóhoz tartozó id, az instructorId az adott instructorhoz (oktatóhoz) tartozó id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres kérelem küldés"),
            @ApiResponse(responseCode = "404", description = "Nem létező student küldi a kérelmet vagy egy nem létező instructor-hoz szeretne csatlakozni a student."),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody"),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba."),
    })
    @PostMapping("/instructor")
    private ResponseEntity<Object> sendInstructorJoinRequest(@RequestBody JsonNode requestBody){
        return requestService.sendInstructorJoinRequest(requestBody.get("studentId").asInt(), requestBody.get("instructorId").asInt());
    }

    @Operation(summary = "Iskolai csatlakozás törlése", description = "Az iskolához való csatlakozási kérelem törlése")
    @Parameter(name = "id", description = "Az adott kérelemhez tartozó id.", required = true, in = ParameterIn.PATH)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres kérelem küldés"),
            @ApiResponse(responseCode = "404", description = "A diák egy nem létező kérelmet szeretne törölni."),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody"),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba."),
    })
    @DeleteMapping("/school/{id}")
    private ResponseEntity<Object> deleteSchoolJoinRequest(@PathVariable("id") Integer id){
        return requestService.deleteSchoolJoinRequest(id);
    }

    @Operation(summary = "Oktatói csatlakozás törlése", description = "")
    @Parameter(name = "id", description = "Az adott kérelemhez tartozó id.", required = true, in = ParameterIn.PATH)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres kérelem küldés"),
            @ApiResponse(responseCode = "404", description = "A diák egy nem létező kérelmet szeretne törölni."),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody"),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba."),
    })
    @DeleteMapping("/instructor/{id}")
    private ResponseEntity<Object> deleteInstructorJoinRequest(@PathVariable("id") Integer id){
        return requestService.deleteInstructorJoinRequest(id);
    }

    @Operation(summary = "Órához való kérelem küldése", description = "Vezetési óra igénylése az adott oktatótol.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = ""),
            @ApiResponse(responseCode = "404", description = ""),
            @ApiResponse(responseCode = "415", description = ""),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody"),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba."),
    })
    @PostMapping("/drivingLesson")
    private ResponseEntity<Object> addDrivingLessonRequest(@RequestBody DrivingLessonRequest addedDrivingLessonRequest){
        return requestService.addDrivingLessonRequest(addedDrivingLessonRequest);
    }

    @Operation(summary = "Vizsga kérelem küldése", description = "")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = ""),
            @ApiResponse(responseCode = "404", description = ""),
            @ApiResponse(responseCode = "415", description = ""),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody"),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba."),
    })
    @PostMapping("/exam")
    private ResponseEntity<Object> addExamRequest(@RequestBody ExamRequest addedExamRequest){
        return requestService.addExamRequest(addedExamRequest);
    }

    @Operation(summary = "Órához való kérelem törlése", description = "Vezetési óra törlése id alapján.")
    @Parameter(name = "id", description = "A kérelem object-éhez tartozó id.", required = true, in = ParameterIn.PATH)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres törlés."),
            @ApiResponse(responseCode = "404", description = "Nem létező kérelem törlése."),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody"),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba."),
    })
    @DeleteMapping("/drivingLesson/{id}")
    private ResponseEntity<Object> deleteDrivingLessonRequest(@PathVariable("id") Integer id){
        return requestService.deleteDrivingLessonRequest(id);
    }

    @Operation(summary = "Vizsga kérelem törlése", description = "Vizsga kérelem törlése id alapján.")
    @Parameter(name = "id", description = "A kérelem object-éhez tartozó id.", required = true, in = ParameterIn.PATH)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres törlés."),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody"),
            @ApiResponse(responseCode = "404", description = "Nem létező kérelem törlése."),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba."),
    })
    @DeleteMapping("/exam/{id}")
    private ResponseEntity<Object> deleteExamRequest(@PathVariable("id") Integer id){
        return requestService.deleteExamRequest(id);
    }
}
