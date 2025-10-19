package csapat.DrivingLicenseAppAPI.service;

import csapat.DrivingLicenseAppAPI.entity.Review;
import csapat.DrivingLicenseAppAPI.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    //
    public ResponseEntity<Review> createReview(Review newReview) {
        return null;
    }

    //
    public ResponseEntity<Review> updateReview(Review updatedReview) {
        return null;
    }

    //
    public ResponseEntity<Object> deleteReview(Integer id) {
        return null;
    }
}
