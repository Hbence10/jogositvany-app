package csapat.DrivingLicenseAppAPI.controller;

import com.fasterxml.jackson.databind.JsonNode;
import csapat.DrivingLicenseAppAPI.entity.DrivingLessons;
import csapat.DrivingLicenseAppAPI.service.DrivingLessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/drivingLesson")
@RequiredArgsConstructor
public class DrivingLessonController {

    private final DrivingLessonService drivingLessonService;

    @Operation(summary = "Vezetési")
    @GetMapping("/categories/school/{id}")
    public ResponseEntity<Object> getDrivingLicenseCategoriesBySchool(@PathVariable("id") Integer schoolId) {
        return drivingLessonService.getDrivingLicenseCategoriesBySchool(schoolId);
    }

    @Operation(summary = "Óra lemondása", description = "")
    @Parameter(name = "id", description = "A vezetés órához tartozó id.", required = true, in = ParameterIn.PATH)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres óra lemondás", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DrivingLessons.class, description = "A lemondott órának a \"frissitett\" objectje")
            )),
            @ApiResponse(responseCode = "404", description = "Olyan diák adatait szeretné lekérni, amely nem létzik.", content = @Content),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),
    })
    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<Object> cancelDrivingLesson(@PathVariable("id") Integer id) {
        return drivingLessonService.cancelDrivingLesson(id);
    }

    @Operation(summary = "A vezetési óra frissitése", description = "A vezetési óra adatainak a frissitése, kivétel az időpont frissitése.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "A frissitett vezetési óra object-je", required = true, content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = DrivingLessons.class)
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres frissités.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DrivingLessons.class, description = "A frissitett óra object-je")
            )),
            @ApiResponse(responseCode = "404", description = "Nem létező status vagy fizetési módszer megadása", content = @Content),
            @ApiResponse(responseCode = "415", description = "Invalid adatok megadása: az object id-ja egyenlő nullal, a startKm nagyobb mint az endKm vagy a lessonHourNumber egyenlő 0-val", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghivása requestBody nélkül.", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateDrivingLessonsData(@RequestBody JsonNode updatedDrivingLesson, @PathVariable("id") Integer id) {
        return drivingLessonService.updateDrivingLesson(id, updatedDrivingLesson.get("startKm").asInt(0), updatedDrivingLesson.get("endKm").asInt(0), updatedDrivingLesson.get("location").asText(null), updatedDrivingLesson.get("pickUpPlace").asText(null), updatedDrivingLesson.get("dropOffPlace").asText(null), updatedDrivingLesson.get("lessonHourNumber").asInt(0), updatedDrivingLesson.get("isPaid").asBoolean(), updatedDrivingLesson.get("statusId").asInt(0), updatedDrivingLesson.get("paymentMethodId").asInt(0));
    }

    @GetMapping("/reservedHour")
    public ResponseEntity<Object> getReservedHoursByDate(@RequestParam("instructorId") Integer instructorId, @RequestParam("date") String date) {
        System.out.println(date);
        return drivingLessonService.getReservedHoursByDate(instructorId, date);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getDrivingLessonById(@PathVariable("id") Integer id) {
        return drivingLessonService.getDrivingLessonById(id);
    }
}
