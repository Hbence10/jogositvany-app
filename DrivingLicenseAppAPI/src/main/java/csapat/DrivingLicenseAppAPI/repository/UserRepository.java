package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Procedure(name = "getAllEmail", procedureName = "getAllEmail")
    List<String> getAllEmail();

    @Procedure(name = "getUserByEmail", procedureName = "getUserByEmail")
    User getUserByEmail(@Param("emailIN") String email);
}
