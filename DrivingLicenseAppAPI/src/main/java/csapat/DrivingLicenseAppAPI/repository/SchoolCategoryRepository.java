package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.SchoolCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolCategoryRepository extends JpaRepository<SchoolCategory, Long> {
}
