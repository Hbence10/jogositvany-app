package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.Instructors;
import csapat.DrivingLicenseAppAPI.entity.Students;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InstructorRepository extends JpaRepository<Instructors, Long> {

    @Procedure(name = "getAllIntructor", procedureName = "getAllIntructor")
    List<Instructors> getAllInstructor();

    @Procedure(name = "getInstructor", procedureName = "getInstructor")
    Optional<Instructors> getInstructor(@Param("idIN") Long id);

    @Procedure(name = "deleteInstructor", procedureName = "deleteInstructor")
    void deleteInstructor(@Param("idIN") Long id);

    @Procedure(name = "getInstructorBySearch", procedureName = "getInstructorBySearch")
    List<Long> getInstructorBySearch(@Param("fuelTypeIdIN") Long fuelTypeId, @Param("schoolIdIN") Long schoolId,  @Param("categoryIdIN") Long categoryId);

    @Query("select s from Instructors i JOIN i.students s where i.id = ?1 and s.isDeleted = false")
    Page<Students> getAllStudents(Long id, Pageable pageable);
}
