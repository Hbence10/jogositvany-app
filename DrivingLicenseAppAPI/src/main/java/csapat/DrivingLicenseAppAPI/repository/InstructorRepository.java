package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.Instructors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InstructorRepository extends JpaRepository<Instructors, Integer> {

    @Procedure(name = "getAllIntructor", procedureName = "getAllIntructor")
    List<Instructors> getAllInstructor();

    @Procedure(name = "getInstructor", procedureName = "getInstructor")
    Optional<Instructors> getInstructor(@Param("idIN") Integer id);

    @Procedure(name = "deleteInstructor", procedureName = "deleteInstructor")
    String deleteInstructor(@Param("idIN") Integer id);

    @Procedure(name = "getInstructorBySearch", procedureName = "getInstructorBySearch")
    List<Integer> getInstructorBySearch(@Param("fuelTypeIdIN") Integer fuelTypeId, @Param("schoolIdIN") Integer schoolId);
}
