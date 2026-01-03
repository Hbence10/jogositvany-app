package csapat.DrivingLicenseAppAPI.service;

import csapat.DrivingLicenseAppAPI.entity.*;
import csapat.DrivingLicenseAppAPI.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;

@Transactional(noRollbackFor = {DataIntegrityViolationException.class, ConstraintViolationException.class, SQLIntegrityConstraintViolationException.class, SQLException.class})
@Service
@RequiredArgsConstructor
public class DrivingLessonService {

    private final DrivingLessonRepository drivingLessonRepository;
    private final DrivingLessonTypeRepository drivingLessonTypeRepository;
    private final ReservedDateRepository reservedDateRepository;
    private final ReservedHourRepository reservedHourRepository;
    private final StudentRepository studentRepository;
    private final StatusRepository statusRepository;
    private final PaymentMethodRepository paymentMethodRepository;

    public ResponseEntity<Object> getAllDrivingLessonType(Integer schoolId) {
        try {
            return ResponseEntity.ok().body(drivingLessonTypeRepository.getAllDrivingLessonType());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<List<DrivingLessons>> getLessonInformationByStudent(Integer studentId) {
        try {
            if (studentId == null) {
                return ResponseEntity.status(422).build();
            }

            Students searchedStudent = studentRepository.getStudent(studentId).orElse(null);

            if (searchedStudent == null || searchedStudent.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok().body(searchedStudent.getDrivingLessons());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Object> cancelDrivingLesson(Integer drivingLessonId) {
        try {
            if (drivingLessonId == null) {
                return ResponseEntity.status(422).build();
            }

            DrivingLessons searchedDrivingLesson = drivingLessonRepository.getDrivingLessonByID(drivingLessonId).orElse(null);
            if (searchedDrivingLesson == null || searchedDrivingLesson.getIsCancelled()) {
                return ResponseEntity.notFound().build();
            } else {
                reservedHourRepository.deleteReservedHour(searchedDrivingLesson.getReservedHour().getId());
                drivingLessonRepository.deleteDrivingLesson(drivingLessonId);
                return ResponseEntity.ok().body(drivingLessonRepository.getDrivingLessonByID(drivingLessonId).get());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Object> getDrivingLessonInformationBetweenTwoDate(String startDateText, String endDateText) {
        try {
            if (startDateText == null || endDateText == null) {
                return ResponseEntity.status(422).build();
            }

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Object> updateDrivingLesson(DrivingLessons updatedDrivingLesson) {
        try {
            if (updatedDrivingLesson == null) {
                return ResponseEntity.status(422).build();
            }

            List<PaymentMethod> allPaymentMethod = paymentMethodRepository.getAllPaymentMethod();
            List<Status> allStatus = statusRepository.getAllStatus();

            if (updatedDrivingLesson.getId() == null) {
              return ResponseEntity.status(415).body("invalidObject");
            } else if (updatedDrivingLesson.getEndKm() <= updatedDrivingLesson.getStartKm()) {
                return ResponseEntity.status(415).body("invalidStartEndKm");
            } else if (updatedDrivingLesson.getLessonHourNumber() <= 0) {
                return ResponseEntity.status(415).body("invalidLessonHourNumber");
            } else if (!allPaymentMethod.contains(updatedDrivingLesson.getPaymentMethod())){
                return ResponseEntity.notFound().build();
            } else if (!allStatus.contains(updatedDrivingLesson.getDrivingLessonStatus())) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok().body(drivingLessonRepository.save(updatedDrivingLesson));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Object> rescheduleDrivingLesson(Integer lessonId, String newDateText, Integer newStart, Integer newEnd) {
        try {
            if (lessonId == null || newDateText == null || newStart == null || newEnd == null) {
                return ResponseEntity.status(422).build();
            }

            DrivingLessons searchedLesson = drivingLessonRepository.getDrivingLessonByID(lessonId).orElse(null);
            if (searchedLesson == null || searchedLesson.getIsCancelled()) {
                return ResponseEntity.notFound().build();
            } else {
                reservedHourRepository.deleteReservedHour(searchedLesson.getReservedHour().getId());


            }

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

}

/*
 * HTTP STATUS KODOK:
 *   - 200: Sikeres muvelet
 *   - 404: Not Found
 *   - 409: Mar foglalt nev
 *   - 415: Unsupported Media Type --> Ha az adott adat invalid
 *   - 422: Hianyzo parameter/response body
 *   - 500: Internal Server Error
 * */