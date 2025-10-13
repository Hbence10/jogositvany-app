package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.ReservedDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservedDateRepository extends JpaRepository<ReservedDate, Integer> {
}
