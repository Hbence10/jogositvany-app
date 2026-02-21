package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.DrivingLessonRequest;
import csapat.DrivingLicenseAppAPI.entity.InstructorJoinRequest;
import csapat.DrivingLicenseAppAPI.entity.Instructors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DrivingLessonRequestRepository extends JpaRepository<DrivingLessonRequest, Integer> {

    @Procedure(name = "getAllDrivingLessonRequest", procedureName = "getAllDrivingLessonRequest")
    List<DrivingLessonRequest> getAllDrivingLessonRequest();

    @Procedure(name = "getDrivingLessonRequest", procedureName = "getDrivingLessonRequest")
    Optional<DrivingLessonRequest> getDrivingLessonRequest(@Param("idIN") Integer id);

    @Procedure(name = "deleteDrivingLessonRequest", procedureName = "deleteDrivingLessonRequest")
    void deleteDrivingLessonRequest(@Param("idIN") Integer id);

    Page<DrivingLessonRequest> findBydLessonInstructorAndIsAcceptedAndIsDeleted(Instructors wantedInstructor, Boolean isAccepted, Boolean isDeleted, Pageable pageable);
}
