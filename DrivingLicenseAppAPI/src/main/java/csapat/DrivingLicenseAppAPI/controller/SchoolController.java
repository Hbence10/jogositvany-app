package csapat.DrivingLicenseAppAPI.controller;

import csapat.DrivingLicenseAppAPI.entity.Students;
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

    @Operation(summary = "Iskolához való jelentkezés", description = "Az iskolához való jelentkezési kérelem eldöntése, hogy elfogadja-e a felhasználó jelentkezését vagy nem.")
    @PostMapping("/request/{id}")
    public ResponseEntity<Object> handleJoinRequest(@PathVariable("id") Integer joinRequestId, @RequestBody Map<String, String> requestBody){
        return schoolService.handleJoinRequest(joinRequestId, requestBody.get("status"));
    }
}
