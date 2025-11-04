package csapat.DrivingLicenseAppAPI.controller;

import csapat.DrivingLicenseAppAPI.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/request")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @PostMapping("/school")
    private ResponseEntity<Object> sendSchoolJoinRequest(@RequestBody Map<String, String> requestBody){
        return requestService.sendSchoolJoinRequest(Integer.valueOf(requestBody.get("schoolId")), Integer.valueOf(requestBody.get("userId")), requestBody.get("requestedRole"));
    }

    @PostMapping("/instructor")
    private ResponseEntity<Object> sendInstructorJoinRequest(){
        return requestService.sendInstructorJoinRequest();
    }
}
