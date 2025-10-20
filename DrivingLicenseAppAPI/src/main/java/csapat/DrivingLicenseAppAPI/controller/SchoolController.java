package csapat.DrivingLicenseAppAPI.controller;

import csapat.DrivingLicenseAppAPI.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/school")
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolService schoolService;

    //kereses
    @GetMapping("/search")
    public ResponseEntity<Object> getSchoolBySearch(){
        return null;
    }

    //
    @GetMapping("/{id}")
    public ResponseEntity<Object> getSchool(@PathVariable("id") int id){
        return null;
    }

    //Iskola tagjainak a kezelese
    //Jelentkezesek:
    @PostMapping("/student")
    public ResponseEntity<Object> addStudent(){
        return schoolService.addStudent();
    }

    @PostMapping("/instructor")
    public ResponseEntity<Object> addInstructor(){
        return schoolService.addInstructor();
    }

    @PostMapping("/admin")
    public ResponseEntity<Object> addSchoolAdmin(){
        return schoolService.addSchoolAdmin();
    }

    //Tagok torlese
    @DeleteMapping("/student/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable("id") Integer studentId){
        return schoolService.deleteStudent(studentId);
    }

    @DeleteMapping("/instructor/{id}")
    public ResponseEntity<Object> deleteInstructor(@PathVariable("id") Integer instructorId){
        return schoolService.deleteInstructor(instructorId);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Object> deleteAdmin(@PathVariable("id") Integer adminId){
        return schoolService.deleteAdmin(adminId);
    }
}
