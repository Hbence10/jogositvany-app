package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SchoolRepository extends JpaRepository<School, Integer> {

    @Procedure(name = "getAllSchool", procedureName = "getAllSchool")
    List<School> getAllSchool();

    @Procedure(name = "getSchool", procedureName = "getSchool")
    School getSchool(@Param("idIN") Integer id);

    @Procedure(name = "deleteSchool", procedureName = "deleteSchool")
    String deleteSchool(@Param("idIN") Integer id);

    @Procedure(name = "getSchoolBySearch", procedureName = "getSchoolBySearch")
    List<Integer> getSchoolBySearch(@Param("nameIN") String name, @Param("townnameIN") String townName);
}
