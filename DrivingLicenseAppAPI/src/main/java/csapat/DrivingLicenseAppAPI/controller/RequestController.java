package csapat.DrivingLicenseAppAPI.controller;

import csapat.DrivingLicenseAppAPI.entity.InstructorJoinRequest;
import csapat.DrivingLicenseAppAPI.entity.SchoolJoinRequest;
import csapat.DrivingLicenseAppAPI.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    private ResponseEntity<Object> sendInstructorJoinRequest(@RequestBody Map<String, Integer> requestBody){
        return requestService.sendInstructorJoinRequest(requestBody.get("studentId"), requestBody.get("instructorId"));
    }

    @DeleteMapping("/school/{id}")
    private ResponseEntity<Object> deleteSchoolJoinRequest(@PathVariable("id") Integer id){
        return requestService.deleteSchoolJoinRequest(id);
    }

    @DeleteMapping("/instructor/{id}")
    private ResponseEntity<Object> deleteInstructorJoinRequest(@PathVariable("id") Integer id){
        return requestService.deleteInstructorJoinRequest(id);
    }

    @GetMapping("/school/{id}")
    private ResponseEntity<List<SchoolJoinRequest>> getAllJoinRequestBySchool(@PathVariable("id") Integer id){
        return requestService.getAllJoinRequestBySchool(id);
    }

    @GetMapping("/instructor/{id}")
    private ResponseEntity<List<InstructorJoinRequest>> getAllJoinRequestByInstructor(@PathVariable("id") Integer id){
        return requestService.getAllJoinRequestByInstructor(id);
    }
}
