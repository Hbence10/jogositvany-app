package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Procedure(name = "getAllRole", procedureName = "getAllRole")
    List<Role> getAllRole();

    @Procedure(name = "getRole", procedureName = "getRole")
    Role getRole(@Param("idIN") Integer id);

    @Procedure(name = "deleteRole", procedureName = "deleteRole")
    String deleteRole(@Param("idIN") Integer id);
}
