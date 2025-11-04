package csapat.DrivingLicenseAppAPI.controller;

import csapat.DrivingLicenseAppAPI.entity.Students;
import csapat.DrivingLicenseAppAPI.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    @PostMapping("/request/{id}")
    public ResponseEntity<Object> handleJoinRequest(@PathVariable("id") Integer joinRequestId, @RequestBody Map<String, String> requestBody){
        return schoolService.handleJoinRequest(joinRequestId, requestBody.get("status"));
    }
}
