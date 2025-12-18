package csapat.DrivingLicenseAppAPI.controller;

import csapat.DrivingLicenseAppAPI.entity.DrivingLessons;
import csapat.DrivingLicenseAppAPI.service.DrivingLessonService;
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

@RestController
@RequestMapping("/drivingLesson")
@RequiredArgsConstructor
public class DrivingLessonController {

    private final DrivingLessonService drivingLessonService;

    @Operation(summary = "Diák óráinak a megszerzése", description = "Kikeressi az összes vezetési órát amely az adott diákhoz tartozik")
    @Parameter(name = "id", description = "A diákhoz tartozó id.", required = true, in = ParameterIn.PATH)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres adat lekérés", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "Olyan diák adatait szeretné lekérni, amely nem létzik.", useReturnTypeSchema = false),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody"),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba."),
    })
    @GetMapping("/student/{id}")
    public ResponseEntity<List<DrivingLessons>> getLessonInformationByStudent(@PathVariable("id") Integer studentId) {
        return drivingLessonService.getLessonInformationByStudent(studentId);
    }

    @Operation(summary = "Óra lemondása", description = "")
    @Parameter(name = "id", description = "A vezetés órához tartozó id.", required = true, in = ParameterIn.PATH)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres óra lemondás", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "Olyan diák adatait szeretné lekérni, amely nem létzik.", useReturnTypeSchema = false),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody"),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba."),
    })
    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<Object> cancelDrivingLesson(@PathVariable("id") Integer id) {
        return drivingLessonService.cancelDrivingLesson(id);
    }

    @Operation(summary = "Az órák visszaszerzése két dátum között.", description = "")
    @Parameters({
            @Parameter(name = "startDate", description = "", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "endDate", description = "", in = ParameterIn.QUERY, required = true),
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres lekérdezés"),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghivása parameterek nélkul"),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.")

    })
    @GetMapping("")
    public ResponseEntity<Object> getDrivingLessonInformationsBetweenTwoDate(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
        return drivingLessonService.getDrivingLessonInformationBetweenTwoDate(startDate, endDate);
    }

    @Operation(summary = "Vezetési óra kérelem elfogadása")
    @Parameters({
            @Parameter(name = "id", description = "A vezetési óra kérelemhez tartozó id", in = ParameterIn.PATH, required = true),
            @Parameter(name = "status", description = "")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = ""),
            @ApiResponse(responseCode = "404", description = ""),
            @ApiResponse(responseCode = "415", description = ""),
            @ApiResponse(responseCode = "422", description = ""),
            @ApiResponse(responseCode = "500", description = "")
    })
    @PostMapping("/handleRequest/{id}")
    public ResponseEntity<Object> handleDrivingLessonRequest(@PathVariable("id") Integer id, @RequestParam("status") String status) {
        return drivingLessonService.handleDrivingLessonRequest(id, status);
    }

    @Operation(summary = "A vezetési óra frissitése", description = "A vezetési óra adatainak a frissitése, kivétel az időpont frissitése.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "", required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres frissités."),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghivása requestBody nélkül."),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.")
    })
    @PutMapping("")
    public ResponseEntity<Object> updateDrivingLessonsData(@RequestBody DrivingLessons updatedDrivingLesson) {
        return drivingLessonService.updateDrivingLessonRequest(updatedDrivingLesson);
    }

    @Operation(summary = "Időpont módositása", description = "")
    @Parameters({
            @Parameter(name = "lessonId", description = "", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "newDate", description = "", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "newStart", description = "", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "newEnd", description = "", required = true, in = ParameterIn.QUERY),
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = ""),
            @ApiResponse(responseCode = "422", description = ""),
            @ApiResponse(responseCode = "500", description = "")
    })
    @PutMapping("/{id}/reschedule")
    public ResponseEntity<Object> rescheduleDrivingLesson(@RequestParam("lessonId") Integer lessonId, @RequestParam("newDate") String newDateText, @RequestParam("newStart") Integer newStartHour, @RequestParam("newEnd") Integer newEndHour) {
        return drivingLessonService.rescheduleDrivingLesson(lessonId, newDateText, newStartHour, newEndHour);
    }

    @Operation(summary = "Befejezetté jelölés", description = "")
    @Parameter(name = "id", description = "", in = ParameterIn.PATH, required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = ""),
            @ApiResponse(responseCode = "422", description = ""),
            @ApiResponse(responseCode = "500", description = "")
    })
    @PatchMapping("/{id}/setEnd")
    public ResponseEntity<Object> setDrivingLessonEnd(@PathVariable("id") Integer id) {
        return drivingLessonService.setDrivingLessonEnd(id);
    }
}
