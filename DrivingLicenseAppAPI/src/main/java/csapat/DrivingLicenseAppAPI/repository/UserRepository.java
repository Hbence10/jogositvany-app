package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.Role;
import csapat.DrivingLicenseAppAPI.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    @Procedure(name = "getAllEmail", procedureName = "getAllEmail")
    List<String> getAllEmail();

    @Procedure(name = "getAllUser", procedureName = "getAllUser")
    List<Users> getAllUser();

    @Procedure(name = "getUser", procedureName = "getUser")
    Optional<Users> getUser(@Param("idIN") Long id);

    @Procedure(name = "deleteUser", procedureName = "deleteUser")
    void deleteUser(@Param("idIN") Long id);

    Optional<Users> findByEmail(String email);

    @Procedure(name = "setRoleOfUser", procedureName = "setRoleOfUser")
    void setRoleOfUser(@Param("userIdIN") Long userId, @Param("roleIdIN") Long roleId);

    @Query("select count(u) from Users u where u.isDeleted = ?1")
    Long countNotDeletedUsers(Boolean isDeleted);

    List<Users> findByRoleAndIsDeleted(Role role, Boolean isDeleted);
}
