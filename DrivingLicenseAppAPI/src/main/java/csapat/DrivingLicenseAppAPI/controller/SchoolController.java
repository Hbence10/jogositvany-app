package csapat.DrivingLicenseAppAPI.controller;

import csapat.DrivingLicenseAppAPI.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
