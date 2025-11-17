package csapat.DrivingLicenseAppAPI.controller;

import csapat.DrivingLicenseAppAPI.entity.Review;
import csapat.DrivingLicenseAppAPI.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/school/{id}")
    public ResponseEntity<List<Review>> getReviewsAboutSchool(@PathVariable("id") Integer schoolId) {
        return reviewService.getReviewsAboutSchool(schoolId);
    }

    @GetMapping("/instructor/{id}")
    public ResponseEntity<List<Review>> getReviewsAboutInstructor(@PathVariable("id") Integer instructorId) {
        return reviewService.getReviewsAboutInstructor(instructorId);
    }

    @PostMapping("/school/{id}")
    public ResponseEntity<Review> createSchoolReview(@RequestBody Review newReview, @PathVariable("id") Integer schoolId) {
        return reviewService.createSchoolReview(newReview, schoolId);
    }

    @PostMapping("/instructor/{id}")
    public ResponseEntity<Review> createInstructorReview (@RequestBody Review newReview, @PathVariable("id") Integer instructorId) {
        return reviewService.createInstructorReview(newReview, instructorId);
    }

    @PutMapping("/updateReview")
    public ResponseEntity<Review> updateReview(@RequestBody Review updatedReview) {
        return reviewService.updateReview(updatedReview);
    }

    @DeleteMapping("/deleteReview/{id}")
    public ResponseEntity<Object> deleteReview(@PathVariable("id") int id) {
        return reviewService.deleteReview(id);
    }
}
