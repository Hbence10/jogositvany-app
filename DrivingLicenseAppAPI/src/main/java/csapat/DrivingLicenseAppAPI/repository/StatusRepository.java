package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatusRepository extends JpaRepository<Status, Integer> {

    @Procedure(name = "getAllStatus", procedureName = "getAllStatus")
    List<Status> getAllStatus();

    @Procedure(name = "getStatus", procedureName = "getStatus")
    Status getStatus(@Param("idIN") Integer id);

    @Procedure(name = "deleteStatus", procedureName = "deleteStatus")
    String deleteStatus(@Param("idIN") Integer id);
}
