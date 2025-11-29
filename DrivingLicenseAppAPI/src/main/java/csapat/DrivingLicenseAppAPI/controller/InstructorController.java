package csapat.DrivingLicenseAppAPI.controller;

import csapat.DrivingLicenseAppAPI.service.InstructorService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/instructor")
@RequiredArgsConstructor
public class InstructorController {

    private final InstructorService instructorService;

    @Operation(summary = "Oktatóhoz való jelentkezés", description = "Az oktatóhoz való jelentkezési kérelem eldöntése, hogy elfogadja-e a diák jelentkezését vagy nem.")
    @PostMapping("/handleRequest")
    private ResponseEntity<Object> handleJoinRequest(@RequestBody Map<String, String> body){
        return instructorService.handleRequest(Integer.valueOf(body.get("requestId")), body.get("status"));
    }
}
