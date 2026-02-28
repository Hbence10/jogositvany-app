package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status, Long> {

    @Procedure(name = "getAllStatus", procedureName = "getAllStatus")
    List<Status> getAllStatus();

    @Procedure(name = "getStatus", procedureName = "getStatus")
    Optional<Status> getStatus(@Param("idIN") Long id);

}
