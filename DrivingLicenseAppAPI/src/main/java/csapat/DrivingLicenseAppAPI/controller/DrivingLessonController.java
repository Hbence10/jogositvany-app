package csapat.DrivingLicenseAppAPI.controller;

import csapat.DrivingLicenseAppAPI.entity.DrivingLessons;
import csapat.DrivingLicenseAppAPI.service.DrivingLessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
            @ApiResponse(responseCode = "404", description = "Olyan diák adatait szeretné lekérni, amely nem létzik.", useReturnTypeSchema = false),
            @ApiResponse(responseCode = "200", description = "Sikeres adat lekérés", useReturnTypeSchema = true)
    })
    @GetMapping("/student/{id}")
    public ResponseEntity<List<DrivingLessons>> getLessonInformationByStudent(@PathVariable("id") Integer studentId) {
        return drivingLessonService.getLessonInformationByStudent(studentId);
    }

    @Operation(summary = "Óra lemondása", description = "")
    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<Object> cancelDrivingLesson(@PathVariable("id") Integer id) {
        return null;
    }
}
