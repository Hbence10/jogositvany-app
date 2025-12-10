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
import org.springframework.http.HttpStatus;
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
        try {
            if (schoolId == null) {
                return ResponseEntity.status(422).build();
            }

            School searchedSchool = schoolRepository.getSchool(schoolId).orElse(null);
            if (searchedSchool == null || searchedSchool.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                List<Review> reviewList = searchedSchool.getReviewList();
                return ResponseEntity.ok().body(reviewList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<List<Review>> getReviewsAboutInstructor(Integer instructorId) {
        try {
            if (instructorId == null) {
                return ResponseEntity.status(422).build();
            }

            Instructors searchedInstructor = instructorRepository.getInstructor(instructorId).orElse(null);
            if (searchedInstructor == null || searchedInstructor.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                List<Review> reviewList = searchedInstructor.getReviewList();
                return ResponseEntity.ok().body(reviewList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Review> createSchoolReview(Review newReview, Integer schoolId) {
        try {
            if (newReview == null || schoolId == null) {
                return ResponseEntity.status(422).build();
            }

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
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Review> createInstructorReview(Review newReview, Integer instructorId) {
        try {
            if (newReview == null || instructorId == null) {
                return ResponseEntity.status(422).build();
            }

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
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Review> updateReview(Review updatedReview) {
        try {
            if (updatedReview == null) {
                return ResponseEntity.status(422).build();
            }

            Review searchedReview = reviewRepository.findById(updatedReview.getId()).get();
            if (searchedReview == null || searchedReview.getId() == null) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok().body(reviewRepository.save(updatedReview));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Object> deleteReview(Integer id) {
        try {
            if (id == null) {
                return ResponseEntity.status(422).build();
            }

            Review searchedReview = reviewRepository.getReview(id).orElse(null);
            if (searchedReview == null) {
                return ResponseEntity.notFound().build();
            } else {
                reviewRepository.deleteReview(id);
                return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
