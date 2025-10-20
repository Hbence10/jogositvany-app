package csapat.DrivingLicenseAppAPI.service;

import csapat.DrivingLicenseAppAPI.entity.Instructors;
import csapat.DrivingLicenseAppAPI.entity.Review;
import csapat.DrivingLicenseAppAPI.entity.School;
import csapat.DrivingLicenseAppAPI.entity.Students;
import csapat.DrivingLicenseAppAPI.repository.InstructorRepository;
import csapat.DrivingLicenseAppAPI.repository.ReviewRepository;
import csapat.DrivingLicenseAppAPI.repository.SchoolRepository;
import csapat.DrivingLicenseAppAPI.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final StudentRepository studentRepository;
    private final SchoolRepository schoolRepository;
    private final InstructorRepository instructorRepository;

    public ResponseEntity<List<Review>> getReviewsAboutSchool(Integer schoolId){
        School searchedSchool = schoolRepository.findById(schoolId).get();
        if(searchedSchool.getId() == null){
            return ResponseEntity.notFound().build();
        } else {
            List<Review> reviewList = searchedSchool.getReviewList();
            return ResponseEntity.ok().body(reviewList);
        }
    }

    public ResponseEntity<List<Review>> getReviewsAboutInstructor(Integer instructorId){
        Instructors searchedInstructor = instructorRepository.findById(instructorId).get();
        if(searchedInstructor.getId() == null){
            return ResponseEntity.notFound().build();
        } else {
            List<Review> reviewList = searchedInstructor.getReviewList();
            return ResponseEntity.ok().body(reviewList);
        }
    }

    //@PreAuthorize("hasRole('student')")
    public ResponseEntity<Review> createReview(Review newReview) {
        Students authorStudent = studentRepository.findById(newReview.getReviewAuthor().getId()).get();

        if (newReview.getId() != null){
            return ResponseEntity.internalServerError().build();
        } else if(authorStudent.getId() == null){
            return ResponseEntity.notFound().build();
        }

        return null;
    }

    //@PreAuthorize("hasRole('student')")
    public ResponseEntity<Review> updateReview(Review updatedReview) {
        return null;
    }

    //@PreAuthorize("hasRole('student')")
    public ResponseEntity<Object> deleteReview(Integer id) {
        return null;
    }
}
