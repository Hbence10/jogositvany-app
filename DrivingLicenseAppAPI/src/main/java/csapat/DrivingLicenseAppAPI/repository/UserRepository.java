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

    @Procedure(name = "getAllUser", procedureName = "getAllUser")
    List<User> getAllUser();

    @Procedure(name = "getUser", procedureName = "getUser")
    User getUser(@Param("idIN") Integer id);

    @Procedure(name = "deleteUser", procedureName = "deleteUser")
    String deleteUser(@Param("idIN") Integer id);
}
