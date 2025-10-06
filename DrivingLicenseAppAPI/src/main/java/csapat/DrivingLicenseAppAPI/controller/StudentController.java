package csapat.DrivingLicenseAppAPI.controller;

import csapat.DrivingLicenseAppAPI.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    //Kezdolap:
    @GetMapping("/instructorDetails")
    public ResponseEntity<Object> getInstructorDetails(@RequestParam("studentId") Integer studentId) {
        return studentService.getInstructorDetails(studentId);
    }

    @GetMapping("/hourDetails")
    public ResponseEntity<Object> getHoursDetails(@RequestParam("studentId") Integer studentId) {
        return studentService.getHoursDetails(studentId);
    }

    //
}
