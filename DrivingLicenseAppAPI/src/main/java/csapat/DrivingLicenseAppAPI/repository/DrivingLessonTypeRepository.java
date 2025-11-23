package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.DrivingLessonType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DrivingLessonTypeRepository extends JpaRepository<DrivingLessonType, Integer> {

    @Procedure(name = "getAllDrivingLessonType", procedureName = "getAllDrivingLessonType")
    List<DrivingLessonType> getAllDrivingLessonType();

    @Procedure(name = "getDrivingLessonType", procedureName = "getDrivingLessonType")
    DrivingLessonType getDrivingLessonType(@Param("idIN") Integer id);

    @Procedure(name = "deleteDrivingLessonType", procedureName = "deleteDrivingLessonType")
    String deleteDrivingLessonType(@Param("idIN") Integer id);
}
