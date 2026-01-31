package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Integer> {
    @Procedure(name = "getAllEmail", procedureName = "getAllEmail")
    List<String> getAllEmail();

//    @Procedure(name = "getUserByEmail", procedureName = "getUserByEmail")
//    Optional<Users> getUserByEmail(@Param("emailIN") String email);

    @Procedure(name = "getAllUser", procedureName = "getAllUser")
    List<Users> getAllUser();

    @Procedure(name = "getUser", procedureName = "getUser")
    Optional<Users> getUser(@Param("idIN") Integer id);

    @Procedure(name = "deleteUser", procedureName = "deleteUser")
    String deleteUser(@Param("idIN") Integer id);

    Optional<Users> findByEmail(String email);

    @Procedure(name = "setRoleOfUser", procedureName = "setRoleOfUser")
    void setRoleOfUser(@Param("userIdIN") Integer userId, @Param("roleIdIN") Integer roleId);
}
