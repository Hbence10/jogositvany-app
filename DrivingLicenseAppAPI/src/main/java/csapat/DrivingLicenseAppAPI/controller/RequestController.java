package csapat.DrivingLicenseAppAPI.controller;

import com.fasterxml.jackson.databind.JsonNode;
import csapat.DrivingLicenseAppAPI.entity.DrivingLessonRequest;
import csapat.DrivingLicenseAppAPI.service.RequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

@RestController
@RequestMapping("/request")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @Operation(summary = "Iskolához való csatlakozás", description = "Az iskolához való csatlakozási kérelem küldése.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "3db attributuma van a body-nak. A schoolId a kivánt iskolához tartozó id, a userId az adott felhasználóhoz tartozó id, a requestedRole meg a kivánt szerep (student vagy instructor).", content = @Content(
            mediaType = "application/json",
            schemaProperties = {
                    @SchemaProperty(name = "schoolId", schema = @Schema(implementation = Integer.class, description = "A csatlakozandó iskola id-ja")),
                    @SchemaProperty(name = "userId", schema = @Schema(implementation = Integer.class, description = "A felhasználóhoz tartozó id.")),
                    @SchemaProperty(name = "categoryId", schema = @Schema(implementation = Integer.class, description = "A kiválasztott jogsi kategóriához tartozó id.")),
            }
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres kérelem küldés", content = @Content),
            @ApiResponse(responseCode = "404", description = "Nem létező iskola/felhasználó vagy rossz role-t add meg a felhasználó", content = @Content),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),
    })
    @PostMapping("/school")
    private ResponseEntity<Object> sendSchoolJoinRequest(@RequestBody JsonNode requestBody) {
        return requestService.sendSchoolJoinRequest(requestBody.get("schoolId").asInt(), requestBody.get("userId").asInt(), requestBody.get("categoryId").asInt());
    }

    @Operation(summary = "Oktatóhoz való csatlakozás", description = "Az oktatóhoz való csatlakozási kérelem küldése.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "2db attributuma van. A studentId az adott tanulóhoz tartozó id, az instructorId az adott instructorhoz (oktatóhoz) tartozó id.", content = @Content(
            mediaType = "application/json",
            schemaProperties = {
                    @SchemaProperty(name = "studentId", schema = @Schema(implementation = Integer.class, description = "A diákhoz tartozó id")),
                    @SchemaProperty(name = "instructorId", schema = @Schema(implementation = Integer.class, description = "Az oktatóhoz tartozó id"))
            }
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres kérelem küldés", content = @Content),
            @ApiResponse(responseCode = "404", description = "Nem létező student küldi a kérelmet vagy egy nem létező instructor-hoz szeretne csatlakozni a student.", content = @Content),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),
    })
    @PostMapping("/instructor")
    private ResponseEntity<Object> sendInstructorJoinRequest(@RequestBody JsonNode requestBody) {
        return requestService.sendInstructorJoinRequest(requestBody.get("studentId").asInt(), requestBody.get("instructorId").asInt());
    }

    @Operation(summary = "Órához való kérelem küldése", description = "Vezetési óra igénylése az adott oktatótol.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "A kérelem object-je", required = true, content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = DrivingLessonRequest.class)
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres kérelem küldése", content = @Content),
            @ApiResponse(responseCode = "404", description = "Nem létező oktató vagy diák megadása", content = @Content),
            @ApiResponse(responseCode = "415", description = "Vagy a küldött object id-ja null, vagy rossz a kezdő és a vég idő, vagy rossz oktató megadása.", content = @Content),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),
    })
    @PostMapping("/drivingLesson")
    private ResponseEntity<Object> sendDrivingLessonRequest(@RequestBody JsonNode requestBody) {
        DateFormat dateWithTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.GERMAN);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMAN);

        try {
            return requestService.sendDrivingLessonRequest(
                    requestBody.get("msg").asText(),
                    dateFormat.parse((requestBody.get("date").asText())),
                    dateWithTimeFormat.parse((requestBody.get("startTime").asText())),
                    dateWithTimeFormat.parse((requestBody.get("endTime").asText())),
                    requestBody.get("studentId").asInt(),
                    requestBody.get("instructorId").asInt());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

}
