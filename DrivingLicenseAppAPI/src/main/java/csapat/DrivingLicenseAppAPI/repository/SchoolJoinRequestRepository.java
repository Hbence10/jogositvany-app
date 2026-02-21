package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.School;
import csapat.DrivingLicenseAppAPI.entity.SchoolJoinRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SchoolJoinRequestRepository extends JpaRepository<SchoolJoinRequest, Integer> {

    @Procedure(name = "getAllSchoolJoinRequest", procedureName = "getAllSchoolJoinRequest")
    List<SchoolJoinRequest> getAllSchoolJoinRequest();

    @Procedure(name = "getSchoolJoinRequest", procedureName = "getSchoolJoinRequest")
    Optional<SchoolJoinRequest> getSchoolJoinRequest(@Param("idIN") Integer id);

    @Procedure(name = "deleteSchoolJoinRequest", procedureName = "deleteSchoolJoinRequest")
    void deleteSchoolJoinRequest(@Param("idIN") Integer id);

    Page<SchoolJoinRequest> findBySchoolJoinRequestSchoolAndIsAcceptedAndIsDeleted(School wantedInstructor, Boolean isAccepted, Boolean isDeleted, Pageable pageable);
}
