package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.OpeningDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpeningDetailRepository extends JpaRepository<OpeningDetails, Integer> {
}
