package csapat.DrivingLicenseAppAPI.controller;

import com.fasterxml.jackson.databind.JsonNode;
import csapat.DrivingLicenseAppAPI.entity.DrivingLessons;
import csapat.DrivingLicenseAppAPI.entity.SchoolCategory;
import csapat.DrivingLicenseAppAPI.service.DrivingLessonService;
import csapat.DrivingLicenseAppAPI.service.other.HourCard;
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

@RestController
@RequestMapping("/drivingLesson")
@RequiredArgsConstructor
public class DrivingLessonController {

    private final DrivingLessonService drivingLessonService;

    @Operation(summary = "Jogositvány ketegóriák iskola szerint", description = "Az adott iskolához tartozó kategóriák lekérdezése. (Az árlistához használjuk)")
    @Parameter(name = "id", description = "Az iskolához tartozó id.", in = ParameterIn.PATH, required = true, schema = @Schema(implementation = Integer.class))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres lekérdezés", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = SchoolCategory.class))
            )),
            @ApiResponse(responseCode = "404", description = "Nem létező iskolához tartozó id megadása.", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghívása parameter nélkül.", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content)
    })
    @GetMapping("/categories/school/{id}")
    public ResponseEntity<Object> getDrivingLicenseCategoriesBySchool(@PathVariable("id") Integer schoolId) {
        return drivingLessonService.getDrivingLicenseCategoriesBySchool(schoolId);
    }

    @Operation(summary = "Óra lemondása", description = "Óra lemondása id alapján")
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
    @Parameter(name = "id", description = "A vezetési órához tartozó id.", required = true, in = ParameterIn.PATH, schema = @Schema(implementation = Integer.class))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "A frissitett vezetési óra adatait tartalmazó object.", required = true, content = @Content(
            mediaType = "application/json",
            schemaProperties = {
                    @SchemaProperty(name = "startKm", schema = @Schema(implementation = Integer.class, description = "A kilométer óra állása az óra kezdetekor")),
                    @SchemaProperty(name = "endKm", schema = @Schema(implementation = Integer.class, description = "A kilométer óra állása az óra végekor")),
                    @SchemaProperty(name = "location", schema = @Schema(implementation = String.class, description = "")),
                    @SchemaProperty(name = "pickUpPlace", schema = @Schema(implementation = String.class, description = "")),
                    @SchemaProperty(name = "dropOffPlace", schema = @Schema(implementation = String.class, description = "")),
                    @SchemaProperty(name = "lessonHourNumber", schema = @Schema(implementation = Integer.class, description = "Az adott vezetési alkalom mennyi órának felel meg.")),
                    @SchemaProperty(name = "isPaid", schema = @Schema(implementation = Boolean.class, description = "Azt mutatja, hogy fizetve van-e az óra.")),
                    @SchemaProperty(name = "statusId", schema = @Schema(implementation = Integer.class, description = "Az óra állapotának az id-ja")),
                    @SchemaProperty(name = "paymentMethodId", schema = @Schema(implementation = Integer.class, description = "A fizetési tipus id-ja"))
            }
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

    @Operation(summary = "A lefoglalt órák lekérdezése", description = "A lefoglalt órák lekérdezése az adott oktatónak az adott dátum alapján")
    @Parameters({
            @Parameter(name = "instructoroId", description = "Az adott oktatóhoz tartozó id", in = ParameterIn.QUERY, required = true, schema = @Schema(implementation = Integer.class)),
            @Parameter(name = "date", description = "A dátum amelyikről tudni szeretnénk a lefoglalt órákat.", in = ParameterIn.QUERY, required = true, schema = @Schema(implementation = String.class))
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres lekérdezés", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = HourCard.class))
            )),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghivása parameterek nélkül", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content)
    })
    @GetMapping("/reservedHour")
    public ResponseEntity<Object> getReservedHoursByDate(@RequestParam("instructorId") Integer instructorId, @RequestParam("date") String date) {
        return drivingLessonService.getReservedHoursByDate(instructorId, date);
    }

    @Operation(summary = "Vezetési óra lekérdezése id alapján", description = "")
    @Parameter(name = "id", description = "A vezetési órához tartozó id.", in = ParameterIn.PATH, required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres lekérdezés", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DrivingLessons.class)
            )),
            @ApiResponse(responseCode = "404", description = "Nem létező vezetési órához tartozó id megadása.", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghivása parameter nélkül", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> getDrivingLessonById(@PathVariable("id") Integer id) {
        return drivingLessonService.getDrivingLessonById(id);
    }
}
