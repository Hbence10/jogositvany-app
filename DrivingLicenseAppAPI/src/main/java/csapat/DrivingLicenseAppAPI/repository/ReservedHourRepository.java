package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.ReservedHour;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservedHourRepository extends JpaRepository<ReservedHour, Integer> {
}
