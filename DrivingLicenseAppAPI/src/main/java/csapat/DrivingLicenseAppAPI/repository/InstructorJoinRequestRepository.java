package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.InstructorJoinRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorJoinRequestRepository extends JpaRepository<InstructorJoinRequest, Integer> {
}
