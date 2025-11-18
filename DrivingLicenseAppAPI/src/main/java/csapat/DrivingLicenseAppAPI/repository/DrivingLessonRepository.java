package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.DrivingLessons;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrivingLessonRepository extends JpaRepository<DrivingLessons, Integer> {
}
