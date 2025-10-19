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

    //kereses
    @GetMapping("/search")
    public ResponseEntity<Object> getInstructorBySeach(){
        return null;
    }

    //
    @GetMapping("/{id}")
    public ResponseEntity<Object> getInstructor(@PathVariable("id") int id){
        return null;
    }
}
