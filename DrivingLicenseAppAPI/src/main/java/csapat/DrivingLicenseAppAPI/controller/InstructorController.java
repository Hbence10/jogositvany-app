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

    @GetMapping("/searchByName")
    public ResponseEntity<Object> getInstructorByName(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName){
        return null;
    }

    @GetMapping("/searchByFillter")
    public ResponseEntity<Object> getInstructorByFillter(){
        return null;
    }
}
