package csapat.DrivingLicenseAppAPI.controller;

import csapat.DrivingLicenseAppAPI.entity.Review;
import csapat.DrivingLicenseAppAPI.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "", description = "")
    @GetMapping("/school/{id}")
    public ResponseEntity<List<Review>> getReviewsAboutSchool(@PathVariable("id") Integer schoolId) {
        return reviewService.getReviewsAboutSchool(schoolId);
    }

    @Operation(summary = "", description = "")
    @GetMapping("/instructor/{id}")
    public ResponseEntity<List<Review>> getReviewsAboutInstructor(@PathVariable("id") Integer instructorId) {
        return reviewService.getReviewsAboutInstructor(instructorId);
    }

    @Operation(summary = "", description = "")
    @PostMapping("/school/{id}")
    public ResponseEntity<Review> createSchoolReview(@RequestBody Review newReview, @PathVariable("id") Integer schoolId) {
        return reviewService.createSchoolReview(newReview, schoolId);
    }

    @Operation(summary = "", description = "")
    @PostMapping("/instructor/{id}")
    public ResponseEntity<Review> createInstructorReview (@RequestBody Review newReview, @PathVariable("id") Integer instructorId) {
        return reviewService.createInstructorReview(newReview, instructorId);
    }

    @Operation(summary = "", description = "")
    @PutMapping("/updateReview")
    public ResponseEntity<Review> updateReview(@RequestBody Review updatedReview) {
        return reviewService.updateReview(updatedReview);
    }

    @Operation(summary = "", description = "")
    @DeleteMapping("/deleteReview/{id}")
    public ResponseEntity<Object> deleteReview(@PathVariable("id") int id) {
        return reviewService.deleteReview(id);
    }
}
