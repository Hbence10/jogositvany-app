package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.DrivingLessons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DrivingLessonRepository extends JpaRepository<DrivingLessons, Integer> {

    @Procedure(name = "getAllDrivingLesson", procedureName = "getAllDrivingLesson")
    List<DrivingLessons> getAllDrivingLesson();

    @Procedure(name = "getDrivingLesson", procedureName = "getDrivingLesson")
    Optional<DrivingLessons> getDrivingLesson(@Param("idIN") Integer id);

    @Procedure(name = "deleteDrivingLesson", procedureName = "deleteDrivingLesson")
    void deleteDrivingLesson(@Param("idIN") Integer id);

    @Procedure(name = "getDrivingLessonBetweenHour", procedureName = "getDrivingLessonBetweenHour")
    List<Integer> getDrivingLessonBetweenHour(@Param("dateIN") Date date, @Param("startHourIN") Date startHour, @Param("endHourIN") Date endHour, @Param("instructorIDIN") Integer instructorId);
}
