package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.InstructorJoinRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InstructorJoinRequestRepository extends JpaRepository<InstructorJoinRequest, Integer> {

    @Procedure(name = "getAllInstructorJoinRequest", procedureName = "getAllInstructorJoinRequest")
    List<InstructorJoinRequest> getAllInstructorJoinRequest();

    @Procedure(name = "getInstructorJoinRequest", procedureName = "getInstructorJoinRequest")
    Optional<InstructorJoinRequest> getInstructorJoinRequest(@Param("idIN") Integer id);

    @Procedure(name = "deleteInstructorJoinRequest", procedureName = "deleteInstructorJoinRequest")
    String deleteInstructorJoinRequest(@Param("idIN") Integer id);
}
