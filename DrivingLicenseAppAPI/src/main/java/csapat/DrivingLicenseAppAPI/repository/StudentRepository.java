package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Students, Long> {

    @Procedure(name = "getAllStudent", procedureName = "getAllStudent")
    List<Students> getAllStudent();

    @Procedure(name = "getStudent", procedureName = "getStudent")
    Optional<Students> getStudent(@Param("idIN") Long id);

    @Procedure(name = "deleteStudent", procedureName = "deleteStudent")
    void deleteStudent(@Param("idIN") Long id);

    @Query("select count(s) from Students s where s.isDeleted = ?1")
    Long countNotDeletedStudents(Boolean isDeleted);
}
