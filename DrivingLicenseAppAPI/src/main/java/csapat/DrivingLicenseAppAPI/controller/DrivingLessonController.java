package csapat.DrivingLicenseAppAPI.controller;

import csapat.DrivingLicenseAppAPI.entity.DrivingLessonType;
import csapat.DrivingLicenseAppAPI.entity.DrivingLessons;
import csapat.DrivingLicenseAppAPI.service.DrivingLessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/drivingLesson")
@RequiredArgsConstructor
public class DrivingLessonController {

    private final DrivingLessonService drivingLessonService;

    @Operation(summary = "Vezetési")
    @GetMapping("/type")
    public ResponseEntity<Object> getAllDrivingLessonType(@RequestParam("school") Integer schoolId) {
        return drivingLessonService.getAllDrivingLessonType(schoolId);
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
    @PutMapping("")
    public ResponseEntity<Object> updateDrivingLessonsData(@RequestBody DrivingLessons updatedDrivingLesson) {
        return drivingLessonService.updateDrivingLesson(updatedDrivingLesson);
    }

    @Operation(summary = "Időpont módositása", description = "Az adott óra dátumának változtatása")
    @Parameters({
            @Parameter(name = "lessonId", description = "Az adott órához tartozó id.", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "newDate", description = "Az új dátum", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "newStart", description = "Az új kezdési idő", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "newEnd", description = "Az új végzési idő", required = true, in = ParameterIn.QUERY),
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres frissités", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DrivingLessons.class, description = "Az áthelyezett óra object-je")
            )),
            @ApiResponse(responseCode = "404", description = "Nem létező vezetési óra frissitése", content = @Content),
            @ApiResponse(responseCode = "415", description = "A kezdő dátum hátrébb van mint a vég dátum", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghívása hiányos requestBody-val.", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content)
    })
    @PutMapping("/{id}/reschedule")
    public ResponseEntity<Object> rescheduleDrivingLesson(@RequestParam("lessonId") Integer lessonId, @RequestParam("newDate") String newDateText, @RequestParam("newStart") Integer newStartHour, @RequestParam("newEnd") Integer newEndHour) {
        return drivingLessonService.rescheduleDrivingLesson(lessonId, newDateText, newStartHour, newEndHour);
    }

    @GetMapping("/reservedHour")
    public ResponseEntity<Object> getReservedHoursByDate (@RequestParam("instructorId") Integer instructorId, @RequestParam("date") String date) {
        System.out.println(date);
        return drivingLessonService.getReservedHoursByDate(instructorId, date);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getDrivingLessonById(@PathVariable("id") Integer id) {
        return drivingLessonService.getDrivingLessonById(id);
    }
}
