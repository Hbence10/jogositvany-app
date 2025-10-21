package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.SchoolJoinRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolJoinRequestRepository extends JpaRepository<SchoolJoinRequest, Integer> {
}
