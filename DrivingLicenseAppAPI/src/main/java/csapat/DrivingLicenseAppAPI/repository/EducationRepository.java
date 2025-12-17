package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EducationRepository extends JpaRepository<Education, Integer> {
    @Procedure(name = "getAllEducation", procedureName = "getAllEducation")
    List<Education> getAllEducation();

    @Procedure(name = "getEducation", procedureName = "getEducation")
    Optional<Education> getEducation(@Param("idIN") Integer id);

    @Procedure(name = "deleteEducation", procedureName = "deleteEducation")
    String deleteEducation(@Param("idIN") Integer id);
}
