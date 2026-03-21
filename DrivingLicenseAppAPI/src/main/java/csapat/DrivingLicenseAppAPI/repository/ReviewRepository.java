package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Procedure(name = "getAllReview", procedureName = "getAllReview")
    List<Review> getAllReview();

    @Procedure(name = "getReview", procedureName = "getReview")
    Optional<Review> getReview(@Param("idIN") Long id);

    @Procedure(name = "deleteReview", procedureName = "deleteReview")
    void deleteReview(@Param("idIN") Long id);

    @Query("select count(r) from Review r where r.isDeleted = ?1")
    Long countNotDeletedReview(Boolean isDeleted);
}
