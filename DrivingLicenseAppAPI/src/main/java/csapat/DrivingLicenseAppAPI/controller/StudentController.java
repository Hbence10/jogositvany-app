package csapat.DrivingLicenseAppAPI.controller;

import csapat.DrivingLicenseAppAPI.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @Operation(summary = "A tanuló óráiról adatok.", description = "Az adott tanuló óráinak összegzése és azokról információ visszaadása.")
    @Parameter(name = "id", description = "A tanulóhoz tartozó id.", required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Nem létező diák adatainak a lekérése."),
            @ApiResponse(responseCode = "200", description = "Sikeres lekérés", useReturnTypeSchema = true)
    })
    @GetMapping("/lessonDetails/{id}")
    public ResponseEntity<Map<String, Integer>> getLessonDetails(@PathVariable("id") int studentId) {
        return studentService.getLessonDetails(studentId);
    }
}
