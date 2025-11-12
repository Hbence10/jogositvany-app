package csapat.DrivingLicenseAppAPI.controller;

import csapat.DrivingLicenseAppAPI.service.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/instructor")
@RequiredArgsConstructor
public class InstructorController {

    private final InstructorService instructorService;

    //diakok kezelese:
    @PostMapping("/students")
    public ResponseEntity<Object> addStudent() {
        return instructorService.addStudent();
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable("id") Long id) {
        return instructorService.deleteStudent(id);
    }
}
