package csapat.DrivingLicenseAppAPI.controller;

import csapat.DrivingLicenseAppAPI.entity.DrivingLessons;
import csapat.DrivingLicenseAppAPI.service.DrivingLessonService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/drivingLesson")
@RequiredArgsConstructor
public class DrivingLessonController {

    private final DrivingLessonService drivingLessonService;

    @GetMapping("/student/{id}")
    public ResponseEntity<List<DrivingLessons>> getLessonInformationByStudent(@PathVariable("id") Integer studentId) {
        return drivingLessonService.getLessonInformationByStudent(studentId);
    }
}
