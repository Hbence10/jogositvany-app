package csapat.DrivingLicenseAppAPI.controller;

import csapat.DrivingLicenseAppAPI.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @Operation(summary = "A tanuló óráiról adatok.", description = "Az adott tanuló óráinak összegzése és azokról információ visszaadása.")
    @Parameter(name = "id", description = "A tanulóhoz tartozó id.", required = true, in = ParameterIn.PATH)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres lekérés", content = @Content(
                    mediaType = "application/json",
                    schemaProperties = {
                            @SchemaProperty(name = "drivenLessons", schema = @Schema(implementation = Integer.class)),
                            @SchemaProperty(name = "plusLessons", schema = @Schema(implementation = Integer.class)),
                            @SchemaProperty(name = "paidLessons", schema = @Schema(implementation = Integer.class)),
                            @SchemaProperty(name = "unPaidLessons", schema = @Schema(implementation = Integer.class)),
                    }
            )),
            @ApiResponse(responseCode = "404", description = "Nem létező diák adatainak a lekérése.", content = @Content),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),
    })
    @GetMapping("/lessonDetails/{id}")
    public ResponseEntity<Map<String, Integer>> getLessonDetails(@PathVariable("id") int studentId) {
        return studentService.getLessonDetails(studentId);
    }

    @Operation(summary = "Tanuló törlése", description = "Tanuló törlése id alapján")
    @Parameter(name = "id", description = "A tanulóhoz tartozó id.", required = true, in = ParameterIn.PATH)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres törlés", content = @Content),
            @ApiResponse(responseCode = "404", description = "Nem létező diák törlése.", content = @Content),
            @ApiResponse(responseCode = "422", description = "Hiányzó parameter vagy requestBody", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),
    })
    @DeleteMapping("/{id}")
    private ResponseEntity<Object> deleteStudent(@PathVariable("id") Integer id) {
        return studentService.deleteStudent(id);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Object> getStudentById(@PathVariable("id") Integer id) {
        return studentService.getStudentById(id);
    }
}
