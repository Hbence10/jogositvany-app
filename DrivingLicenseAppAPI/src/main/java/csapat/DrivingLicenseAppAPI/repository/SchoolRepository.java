package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.Instructors;
import csapat.DrivingLicenseAppAPI.entity.School;
import csapat.DrivingLicenseAppAPI.entity.Students;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SchoolRepository extends JpaRepository<School, Integer> {

    @Procedure(name = "getAllSchool", procedureName = "getAllSchool")
    List<School> getAllSchool();

    @Procedure(name = "getSchool", procedureName = "getSchool")
    Optional<School> getSchool(@Param("idIN") Integer id);

    @Procedure(name = "deleteSchool", procedureName = "deleteSchool")
    String deleteSchool(@Param("idIN") Integer id);

    @Procedure(name = "getSchoolBySearch", procedureName = "getSchoolBySearch")
    List<Integer> getSchoolBySearch( @Param("townnameIN") String townName);

    @Query("select st from School s JOIN s.studentsList st where s.id = ?1 and st.isDeleted = false")
    Page<Students> getAllStudents(Integer id, Pageable pageable);

    @Query("select i from School s JOIN s.instructorsList i where s.id = ?1 and i.isDeleted = false")
    Page<Instructors> getAllInstructor(Integer id, Pageable pageable);
}
