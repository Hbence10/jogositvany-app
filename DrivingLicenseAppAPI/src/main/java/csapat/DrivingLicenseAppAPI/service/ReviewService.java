package csapat.DrivingLicenseAppAPI.service;

import csapat.DrivingLicenseAppAPI.entity.Instructors;
import csapat.DrivingLicenseAppAPI.entity.Review;
import csapat.DrivingLicenseAppAPI.entity.School;
import csapat.DrivingLicenseAppAPI.entity.Students;
import csapat.DrivingLicenseAppAPI.repository.InstructorRepository;
import csapat.DrivingLicenseAppAPI.repository.ReviewRepository;
import csapat.DrivingLicenseAppAPI.repository.SchoolRepository;
import csapat.DrivingLicenseAppAPI.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;

@Transactional(noRollbackFor = {DataIntegrityViolationException.class, ConstraintViolationException.class, SQLIntegrityConstraintViolationException.class, SQLException.class})
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

    public ResponseEntity<Object> createSchoolReview(Review newReview, Integer schoolId) {
        try {
            if (newReview == null || schoolId == null) {
                return ResponseEntity.status(422).build();
            }

            Students authorStudent = studentRepository.getStudent(newReview.getReviewAuthor().getId()).orElse(null);
            School searchedSchool = schoolRepository.getSchool(schoolId).orElse(null);

            if (authorStudent == null || authorStudent.getIsDeleted()) {
                return ResponseEntity.status(404).body("studentNotFound");
            } else if (searchedSchool == null || searchedSchool.getIsDeleted()) {
                return ResponseEntity.status(404).body("schoolNotFound");
            } else if (newReview.getId() != null) {
                return ResponseEntity.status(415).body("invalidObject");
            } else if (newReview.getRating() > 5.0 || newReview.getRating() < 0) {
                return ResponseEntity.status(415).body("invalidRatingNumber");
            } else {
                newReview.setText(newReview.getText().trim());
                newReview.setAboutSchool(searchedSchool);
                newReview.setReviewAuthor(authorStudent);
                return ResponseEntity.ok().body(reviewRepository.save(newReview));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Object> createInstructorReview(Review newReview, Integer instructorId) {
        try {
            if (newReview == null || instructorId == null) {
                return ResponseEntity.status(422).build();
            }

            Students authorStudent = studentRepository.getStudent(newReview.getReviewAuthor().getId()).orElse(null);
            Instructors searchedInstructor = instructorRepository.getInstructor(instructorId).orElse(null);

            if (authorStudent == null || authorStudent.getIsDeleted()) {
                return ResponseEntity.status(404).body("studentNotFound");
            } else if (searchedInstructor == null|| searchedInstructor.getIsDeleted()) {
                return ResponseEntity.status(404).body("instructorNotFound");
            } else if (newReview.getId() != null) {
                return ResponseEntity.status(415).body("invalidObject");
            } else if (newReview.getRating() > 5.0 || newReview.getRating() < 0) {
                return ResponseEntity.status(415).body("invalidRatingNumber");
            } else {
                newReview.setAboutInstructor(searchedInstructor);
                newReview.setReviewAuthor(authorStudent);
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

            Review searchedReview = reviewRepository.getReview(updatedReview.getId()).orElse(null);
            if (searchedReview == null || searchedReview.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                searchedReview.setText(searchedReview.getText().trim());
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
            if (searchedReview == null || searchedReview.getIsDeleted()) {
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

/*
 * HTTP STATUS KODOK:
 *   - 200: Sikeres muvelet
 *   - 404: Not Found
 *   - 409: Mar foglalt nev
 *   - 415: Unsupported Media Type --> Ha az adott adat invalid
 *   - 422: Hianyzo parameter/response body
 *   - 500: Internal Server Error
 * */