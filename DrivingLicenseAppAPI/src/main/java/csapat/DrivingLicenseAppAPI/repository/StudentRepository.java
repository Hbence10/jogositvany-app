package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.Students;
import csapat.DrivingLicenseAppAPI.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Students, Integer> {

    @Procedure(name = "getStudentByUserId", procedureName = "getStudentByUserId")
    Students getUserByUserId(@Param("userIdIN") Integer userId);
}
