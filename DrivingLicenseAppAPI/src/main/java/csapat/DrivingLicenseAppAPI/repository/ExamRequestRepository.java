package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.ExamRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExamRequestRepository extends JpaRepository<ExamRequest, Integer> {

    @Procedure(name = "getAllExamRequest", procedureName = "getAllExamRequest")
    List<ExamRequest> getAllExamRequest();

    @Procedure(name = "getExamRequest", procedureName = "getExamRequest")
    ExamRequest getExamRequest(@Param("idIN") Integer id);

    @Procedure(name = "deleteExamRequest", procedureName = "deleteExamRequest")
    String deleteExamRequest(@Param("idIN") Integer id);
}
