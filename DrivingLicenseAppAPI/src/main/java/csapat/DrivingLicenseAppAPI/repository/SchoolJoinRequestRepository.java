package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.DrivingLessons;
import csapat.DrivingLicenseAppAPI.entity.SchoolJoinRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SchoolJoinRequestRepository extends JpaRepository<SchoolJoinRequest, Integer> {

    @Procedure(name = "getAllSchoolJoinRequest", procedureName = "getAllSchoolJoinRequest")
    List<DrivingLessons> getAllSchoolJoinRequest();

    @Procedure(name = "getSchoolJoinRequest", procedureName = "getSchoolJoinRequest")
    DrivingLessons getSchoolJoinRequest(@Param("idIN") Integer id);

    @Procedure(name = "deleteSchoolJoinRequest", procedureName = "deleteSchoolJoinRequest")
    String deleteSchoolJoinRequest(@Param("idIN") Integer id);
}
