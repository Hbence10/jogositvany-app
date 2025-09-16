package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface UserRepository extends JpaRepository<User, Long> {

    @Procedure(name = "Login", procedureName = "Login")
    User login(@Param("emailIN") String username, @Param("passwordIN") String password);
}
