package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.DrivingLessonRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DrivingLessonRequestRepository extends JpaRepository<DrivingLessonRequest, Integer> {

    @Procedure(name = "getAllDrivingLessonRequest", procedureName = "getAllDrivingLessonRequest")
    List<DrivingLessonRequest> getAllDrivingLessonRequest();

    @Procedure(name = "getDrivingLessonRequest", procedureName = "getDrivingLessonRequest")
    DrivingLessonRequest getDrivingLessonRequest(@Param("idIN") Integer id);

    @Procedure(name = "deleteDrivingLessonRequest", procedureName = "deleteDrivingLessonRequest")
    String deleteDrivingLessonRequest(@Param("idIN") Integer id);
}
