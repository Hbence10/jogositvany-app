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

public interface SchoolRepository extends JpaRepository<School, Long> {

    @Procedure(name = "getAllSchool", procedureName = "getAllSchool")
    List<School> getAllSchool();

    @Procedure(name = "getSchool", procedureName = "getSchool")
    Optional<School> getSchool(@Param("idIN") Long id);

    @Procedure(name = "deleteSchool", procedureName = "deleteSchool")
    void deleteSchool(@Param("idIN") Long id);

    @Procedure(name = "getSchoolBySearch", procedureName = "getSchoolBySearch")
    List<Long> getSchoolBySearch(@Param("townnameIN") String townName);

    @Query("select st from School s JOIN s.studentsList st where s.id = ?1 and st.isDeleted = false")
    Page<Students> getAllStudents(Long id, Pageable pageable);

    @Query("select i from School s JOIN s.instructorsList i where s.id = ?1 and i.isDeleted = false")
    Page<Instructors> getAllInstructor(Long id, Pageable pageable);

    @Query("select count(s) from School s where s.isDeleted = ?1")
    Long countNotDeletedSchool(Boolean isDeleted);
}
