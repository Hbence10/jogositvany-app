package csapat.DrivingLicenseAppAPI.service;

import csapat.DrivingLicenseAppAPI.dto.NewReview;
import csapat.DrivingLicenseAppAPI.entity.Instructors;
import csapat.DrivingLicenseAppAPI.entity.Review;
import csapat.DrivingLicenseAppAPI.entity.School;
import csapat.DrivingLicenseAppAPI.entity.Students;
import csapat.DrivingLicenseAppAPI.exception.InvalidDataException;
import csapat.DrivingLicenseAppAPI.exception.NotFoundException;
import csapat.DrivingLicenseAppAPI.repository.InstructorRepository;
import csapat.DrivingLicenseAppAPI.repository.ReviewRepository;
import csapat.DrivingLicenseAppAPI.repository.SchoolRepository;
import csapat.DrivingLicenseAppAPI.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final StudentRepository studentRepository;
    private final SchoolRepository schoolRepository;
    private final InstructorRepository instructorRepository;

    @PreAuthorize("(hasRole('student') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> addReview(NewReview newReviewDto) {
        Students author = studentRepository.getStudent(newReviewDto.studentId()).orElseThrow(() -> new NotFoundException("studentNotFound"));
        if (newReviewDto.rating() < 0 || newReviewDto.rating() > 5) {
            throw new InvalidDataException("invalidRating");
        }

        Review newReview = new Review(newReviewDto.reviewText(), newReviewDto.rating(), author);
        if (newReviewDto.schoolId() == 0 && newReviewDto.instructorId() != 0) {
            Instructors searchedInstructor = instructorRepository.getInstructor(newReviewDto.instructorId()).orElseThrow(() -> new NotFoundException("instructorNotFound"));
            newReview.setAboutInstructor(searchedInstructor);
        } else if (newReviewDto.instructorId() == 0 && newReviewDto.schoolId() != 0) {
            School searchedSchool = schoolRepository.getSchool(newReviewDto.schoolId()).orElseThrow(() -> new NotFoundException("schoolNotFound"));
            newReview.setAboutSchool(searchedSchool);
        }
        newReview.setIsAnonymous(newReviewDto.isAnonymous());
        return ResponseEntity.ok().body(reviewRepository.save(newReview));
    }

    @PreAuthorize("(isAuthenticated() and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> getReviews(String about, Long aboutId) {
        if (!about.equals("school") && !about.equals("instructor")) {
            throw new InvalidDataException("invalidAbout");
        } else {
            List<Review> returnList = new ArrayList<>();
            if (about.equals("instructor")) {
                Instructors searchedInstructor = instructorRepository.getInstructor(aboutId).orElseThrow(() -> new NotFoundException("instructorNotFound"));
                returnList = searchedInstructor.getReviewList();
            } else if (about.equals("school")) {
                School searchedSchool = schoolRepository.getSchool(aboutId).orElseThrow(() -> new NotFoundException("schoolNotFound"));
                returnList = searchedSchool.getReviewList();
            }
            return ResponseEntity.ok().body(returnList);
        }
    }

    @PreAuthorize("(hasRole('student') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> deleteReview(Long id) {
        Review searchedReview = reviewRepository.findById(id).orElseThrow(() -> new NotFoundException("reviewNotFound"));
        reviewRepository.deleteReview(id);
        return ResponseEntity.ok().build();
    }
}