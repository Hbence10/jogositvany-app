package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.ExamRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRequestRepository extends JpaRepository<ExamRequest, Integer> {
}
