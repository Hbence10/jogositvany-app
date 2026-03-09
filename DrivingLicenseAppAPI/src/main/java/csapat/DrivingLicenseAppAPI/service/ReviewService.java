package csapat.DrivingLicenseAppAPI.service;

import csapat.DrivingLicenseAppAPI.dto.NewReview;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Transactional(noRollbackFor = {DataIntegrityViolationException.class, ConstraintViolationException.class, SQLIntegrityConstraintViolationException.class, SQLException.class})
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final StudentRepository studentRepository;
    private final SchoolRepository schoolRepository;
    private final InstructorRepository instructorRepository;

    @PreAuthorize("(hasRole('student') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> addReview(NewReview newReviewDto) {
        try {
            if (newReviewDto == null) {
                return ResponseEntity.status(422).build();
            }

            Students author = studentRepository.getStudent(newReviewDto.studentId()).orElse(null);
            if (author == null || author.getIsDeleted()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("studentNotFound");
            }

            if (newReviewDto.rating() < 0 || newReviewDto.rating() > 5) {
                return ResponseEntity.status(415).body("invalidRating");
            }

            Review newReview = new Review(newReviewDto.reviewText(), newReviewDto.rating(), author);
            if (newReviewDto.schoolId() == 0 && newReviewDto.instructorId() != 0) {
                Instructors searchedInstructor = instructorRepository.getInstructor(newReviewDto.instructorId()).orElse(null);
                if (searchedInstructor == null || searchedInstructor.getIsDeleted()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("instructorNotFound");
                }
                newReview.setAboutInstructor(searchedInstructor);
            } else if (newReviewDto.instructorId() == 0 && newReviewDto.schoolId() != 0) {
                School searchedSchool = schoolRepository.getSchool(newReviewDto.schoolId()).orElse(null);
                if (searchedSchool == null || searchedSchool.getIsDeleted()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("schoolNotFound");
                }
                newReview.setAboutSchool(searchedSchool);
            }
            newReview.setIsAnonymous(newReviewDto.isAnonymous());
            return ResponseEntity.ok().body(reviewRepository.save(newReview));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("(isAuthenticated() and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> getReviews(String about, Long aboutId) {
        try {
            if (about == null || aboutId == null) {
                return ResponseEntity.status(422).build();
            }

            if (!about.equals("school") && !about.equals("instructor")) {
                return ResponseEntity.status(415).body("invalidAbout");
            } else {
                List<Review> returnList = new ArrayList<>();
                if (about.equals("instructor")) {
                    Instructors searchedInstructor = instructorRepository.getInstructor(aboutId).orElse(null);
                    if (searchedInstructor == null) {
                        return ResponseEntity.notFound().build();
                    } else {
                        returnList = searchedInstructor.getReviewList();
                    }
                } else if (about.equals("school")) {
                    School searchedSchool = schoolRepository.getSchool(aboutId).orElse(null);
                    if (searchedSchool == null) {
                        return ResponseEntity.notFound().build();
                    } else {
                        returnList = searchedSchool.getReviewList();
                    }
                }
                return ResponseEntity.ok().body(returnList);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("(hasRole('student') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> deleteReview(Long id) {
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