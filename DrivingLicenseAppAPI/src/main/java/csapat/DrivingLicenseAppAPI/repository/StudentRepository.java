package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Students, Integer> {

    @Procedure(name = "getAllStudent", procedureName = "getAllStudent")
    List<Students> getAllStudent();

    @Procedure(name = "getStudent", procedureName = "getStudent")
    Optional<Students> getStudent(@Param("idIN") Integer id);

    @Procedure(name = "deleteStudent", procedureName = "deleteStudent")
    String deleteStudent(@Param("idIN") Integer id);
}
