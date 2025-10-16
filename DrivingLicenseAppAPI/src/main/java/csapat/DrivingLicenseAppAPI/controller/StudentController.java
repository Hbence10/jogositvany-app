package csapat.DrivingLicenseAppAPI.controller;

import csapat.DrivingLicenseAppAPI.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/search")
    public ResponseEntity<Object> getStudentByName(){
        return null;
    }

    @GetMapping("/lessonDetails/{id}")
    public ResponseEntity<Object> getLessonDetails(){
        return null;
    }
}
