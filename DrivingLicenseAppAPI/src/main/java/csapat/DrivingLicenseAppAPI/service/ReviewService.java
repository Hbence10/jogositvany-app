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

    public ResponseEntity<Review> createSchoolReview(Review newReview, Integer schoolId) {
        Students authorStudent = studentRepository.findById(newReview.getReviewAuthor().getId()).get();
        School searchedSchool = schoolRepository.findById(schoolId).get();

        if (authorStudent == null || authorStudent.getId() == null || authorStudent.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else if (searchedSchool == null || searchedSchool.getId() == null || searchedSchool.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else if (newReview.getId() != null) {
            return null;
        } else if (newReview.getRating() > 5.0 || newReview.getRating() < 0) {
            return null;
        } else {
            newReview.setAboutSchool(searchedSchool);
            newReview.setReviewAuthor(studentRepository.findById(newReview.getReviewAuthor().getId()).get());
            return ResponseEntity.ok().body(reviewRepository.save(newReview));
        }
    }

    public ResponseEntity<Review> createInstructorReview(Review newReview, Integer instructorId) {
        Students authorStudent = studentRepository.findById(newReview.getReviewAuthor().getId()).get();
        Instructors searchedInstructor = instructorRepository.findById(instructorId).get();

        if (authorStudent == null || authorStudent.getId() == null || authorStudent.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else if (searchedInstructor == null || searchedInstructor.getId() == null || searchedInstructor.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else if (newReview.getId() != null) {
            return null;
        } else if (newReview.getRating() > 5.0 || newReview.getRating() < 0) {
            return null;
        } else {
            newReview.setAboutInstructor(searchedInstructor);
            newReview.setReviewAuthor(studentRepository.findById(newReview.getReviewAuthor().getId()).get());
            return ResponseEntity.ok().body(reviewRepository.save(newReview));
        }
    }

    public ResponseEntity<Review> updateReview(Review updatedReview) {
        Review searchedReview = reviewRepository.findById(updatedReview.getId()).get();
        if (searchedReview == null || searchedReview.getId() == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(reviewRepository.save(updatedReview));
        }
    }

    public ResponseEntity<Object> deleteReview(Integer id) {
        Review searchedReview = reviewRepository.findById(id).get();
        if (searchedReview.getId() == null) {
            return ResponseEntity.notFound().build();
        } else {
            searchedReview.setDeleted(true);
            searchedReview.setDeletedAt(LocalDateTime.now());
            reviewRepository.save(searchedReview);
            return ResponseEntity.ok().build();
        }
    }
}
