package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.DrivingLicenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrivingLicenseCategoryRepository extends JpaRepository<DrivingLicenseCategory, Integer> {
}
