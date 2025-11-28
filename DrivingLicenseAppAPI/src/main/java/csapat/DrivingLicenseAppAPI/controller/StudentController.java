package csapat.DrivingLicenseAppAPI.controller;

import csapat.DrivingLicenseAppAPI.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @Operation(summary = "", description = "")
    @GetMapping("/lessonDetails/{id}")
    public ResponseEntity<Map<String, Integer>> getLessonDetails(@PathVariable("id") int studentId){
        return studentService.getLessonDetails(studentId);
    }
}
