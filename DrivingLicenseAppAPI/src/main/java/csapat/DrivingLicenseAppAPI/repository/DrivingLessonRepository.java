package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.DrivingLessonRequest;
import csapat.DrivingLicenseAppAPI.entity.DrivingLessons;
import csapat.DrivingLicenseAppAPI.entity.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DrivingLessonRepository extends JpaRepository<DrivingLessons, Long> {

    @Procedure(name = "getAllDrivingLesson", procedureName = "getAllDrivingLesson")
    List<DrivingLessons> getAllDrivingLesson();

    @Procedure(name = "getDrivingLesson", procedureName = "getDrivingLesson")
    Optional<DrivingLessons> getDrivingLesson(@Param("idIN") Long id);

    @Procedure(name = "deleteDrivingLesson", procedureName = "deleteDrivingLesson")
    void deleteDrivingLesson(@Param("idIN") Long id);

    @Procedure(name = "getDrivingLessonBetweenHour", procedureName = "getDrivingLessonBetweenHour")
    List<Long> getDrivingLessonBetweenHour(@Param("dateIN") Date date, @Param("startHourIN") Date startHour, @Param("endHourIN") Date endHour, @Param("instructorIDIN") Long instructorId);

    @Procedure(name = "getDrivingLessonByStudentId", procedureName = "getDrivingLessonByStudentId")
    List<DrivingLessons> getDrivingLessonByStudentId(@Param("idIN") Long id);
}
