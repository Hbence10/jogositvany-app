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

import java.time.LocalDateTime;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final StudentRepository studentRepository;
    private final SchoolRepository schoolRepository;
    private final InstructorRepository instructorRepository;

    public ResponseEntity<List<Review>> getReviewsAboutSchool(Integer schoolId) {
        School searchedSchool = schoolRepository.findById(schoolId).get();
        if (searchedSchool.getId() == null) {
            return ResponseEntity.notFound().build();
        } else {
            List<Review> reviewList = searchedSchool.getReviewList();
            return ResponseEntity.ok().body(reviewList);
        }
    }

    public ResponseEntity<List<Review>> getReviewsAboutInstructor(Integer instructorId) {
        Instructors searchedInstructor = instructorRepository.findById(instructorId).get();
        if (searchedInstructor.getId() == null) {
            return ResponseEntity.notFound().build();
        } else {
            List<Review> reviewList = searchedInstructor.getReviewList();
            return ResponseEntity.ok().body(reviewList);
        }
    }

    //@PreAuthorize("hasRole('student')")
    public ResponseEntity<Review> createReview(Review newReview) {
        Students authorStudent = studentRepository.findById(newReview.getReviewAuthor().getId()).get();
        boolean validAboutObject = false;

        if (newReview.getId() != null) {
            return ResponseEntity.internalServerError().build();
        } else if (authorStudent.getId() == null) {
            return ResponseEntity.notFound().build();
        } else if (newReview.getAboutInstructor() == null && newReview.getAboutSchool() == null) {
            return ResponseEntity.notFound().build();
        }

        if (newReview.getAboutSchool() == null && newReview.getAboutInstructor() != null) {
            Instructors searchedInstructor = instructorRepository.findById(newReview.getAboutInstructor().getId()).get();
            if(searchedInstructor.getId() != null){
                validAboutObject = true;
            }

        } else if (newReview.getAboutSchool() != null && newReview.getAboutInstructor() == null) {
            School searchedSchool = schoolRepository.findById(newReview.getAboutSchool().getId()).get();
            if(searchedSchool.getId() != null){
               validAboutObject = true;
            }
        }

        if (validAboutObject){
            return ResponseEntity.ok().body(reviewRepository.save(newReview));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //@PreAuthorize("hasRole('student')")
    public ResponseEntity<Review> updateReview(Review updatedReview) {
        return null;
    }

    //@PreAuthorize("hasRole('student')")
    public ResponseEntity<Object> deleteReview(Integer id) {
        Review searchedReview = reviewRepository.findById(id).get();
        if(searchedReview.getId() == null){
            return ResponseEntity.notFound().build();
        } else {
            searchedReview.setDeleted(true);
            searchedReview.setDeletedAt(LocalDateTime.now());
            reviewRepository.save(searchedReview);
            return ResponseEntity.ok().build();
        }
    }
}
