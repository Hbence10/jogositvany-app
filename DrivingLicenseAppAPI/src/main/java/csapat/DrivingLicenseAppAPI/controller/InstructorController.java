package csapat.DrivingLicenseAppAPI.controller;

import csapat.DrivingLicenseAppAPI.service.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/instructor")
@RequiredArgsConstructor
public class InstructorController {

    private final InstructorService instructorService;

    @PostMapping("/handleRequest")
    private ResponseEntity<Object> handleJoinRequest(@RequestBody Map<String, String> body){
        return instructorService.handleRequest(Integer.valueOf(body.get("requestId")), body.get("status"));
    }
}
