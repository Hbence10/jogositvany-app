package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.School;
import csapat.DrivingLicenseAppAPI.entity.SchoolJoinRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SchoolJoinRequestRepository extends JpaRepository<SchoolJoinRequest, Long> {

    @Procedure(name = "getAllSchoolJoinRequest", procedureName = "getAllSchoolJoinRequest")
    List<SchoolJoinRequest> getAllSchoolJoinRequest();

    @Procedure(name = "getSchoolJoinRequest", procedureName = "getSchoolJoinRequest")
    Optional<SchoolJoinRequest> getSchoolJoinRequest(@Param("idIN") Long id);

    @Procedure(name = "deleteSchoolJoinRequest", procedureName = "deleteSchoolJoinRequest")
    void deleteSchoolJoinRequest(@Param("idIN") Long id);

    Page<SchoolJoinRequest> findBySchoolJoinRequestSchoolAndIsAcceptedAndIsDeleted(School wantedInstructor, Boolean isAccepted, Boolean isDeleted, Pageable pageable);

    @Query("select count(s) from SchoolJoinRequest s where s.isDeleted = ?1")
    Long countNotDeletedSchoolJoinRequest(Boolean isDeleted);
}
