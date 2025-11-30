package csapat.DrivingLicenseAppAPI.controller;

import csapat.DrivingLicenseAppAPI.entity.School;
import csapat.DrivingLicenseAppAPI.entity.Students;
import csapat.DrivingLicenseAppAPI.repository.SchoolRepository;
import csapat.DrivingLicenseAppAPI.service.SchoolService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/school")
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolService schoolService;
    private final SchoolRepository schoolRepository;

    @Operation(summary = "Iskolához való jelentkezés", description = "Az iskolához való jelentkezési kérelem eldöntése, hogy elfogadja-e a felhasználó jelentkezését vagy nem.")
    @PostMapping("/request/{id}")
    public ResponseEntity<Object> handleJoinRequest(@PathVariable("id") Integer joinRequestId, @RequestBody Map<String, String> requestBody){
        return schoolService.handleJoinRequest(joinRequestId, requestBody.get("status"));
    }

    @Operation(summary = "Iskola frissitése", description = "Az iskola adatainak a frissitése. Minden adat frissitése kivéve a boritókép és a nyitvatartás.")
    @PutMapping("")
    public ResponseEntity<School> updateSchool(@RequestBody School updatedSchool){
        return null;
    }

    @Operation(summary = "Iskola boritókép csere.")
    @PatchMapping("/coverImg/{id}")
    public ResponseEntity<School> changeCoverImg(){
        return null;
    }

    @Operation(summary = "Nyitvatartás hozzáadása.")
    @PostMapping("/{id}/openingDetails")
    public ResponseEntity<School> addOpeningDetails(){
        return null;
    }

    @Operation(summary = "Nyitvatartás modósitása.")
    @PutMapping("/{id}/openingDetails")
    public ResponseEntity<School> updateOpeningDetails(){
        return null;
    }

    //
    @GetMapping("{id}")
    public School getSchoolById(@PathVariable("id") Integer id){
        return schoolRepository.findById(id).get();
    }
}
