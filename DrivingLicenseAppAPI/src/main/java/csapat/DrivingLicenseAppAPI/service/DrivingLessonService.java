package csapat.DrivingLicenseAppAPI.service;

import csapat.DrivingLicenseAppAPI.entity.*;
import csapat.DrivingLicenseAppAPI.repository.*;
import csapat.DrivingLicenseAppAPI.service.other.HourCard;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Transactional(noRollbackFor = {DataIntegrityViolationException.class, ConstraintViolationException.class, SQLIntegrityConstraintViolationException.class, SQLException.class})
@Service
@RequiredArgsConstructor
public class DrivingLessonService {

    private final DrivingLessonRepository drivingLessonRepository;
    private final ReservedHourRepository reservedHourRepository;
    private final SchoolRepository schoolRepository;
    private final StatusRepository statusRepository;
    private final PaymentMethodRepository paymentMethodRepository;

    public ResponseEntity<Object> getAllDrivingLessonType(Integer schoolId) {
        try {
            if (schoolId == null) {
                return ResponseEntity.status(422).build();
            }
            School searchedSchool = schoolRepository.getSchool(schoolId).orElse(null);
            if (searchedSchool == null || searchedSchool.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(searchedSchool.getDrivingLessonsType());
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

            DrivingLessons searchedDrivingLesson = drivingLessonRepository.getDrivingLesson(drivingLessonId).orElse(null);
            if (searchedDrivingLesson == null || searchedDrivingLesson.getIsCancelled()) {
                return ResponseEntity.notFound().build();
            } else {
                reservedHourRepository.deleteReservedHour(searchedDrivingLesson.getReservedHour().getId());
                drivingLessonRepository.deleteDrivingLesson(drivingLessonId);
                return ResponseEntity.ok().body(drivingLessonRepository.getDrivingLesson(drivingLessonId).get());
            }
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
            } else if (!allPaymentMethod.contains(updatedDrivingLesson.getPaymentMethod())) {
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

            DrivingLessons searchedLesson = drivingLessonRepository.getDrivingLesson(lessonId).orElse(null);
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

    public ResponseEntity<Object> getReservedHoursByDate(Integer instructorId, String wantedDate) {
        try {
            if (instructorId == null || wantedDate == null) {
                return ResponseEntity.status(422).build();
            }

            List<Integer> reservedHourIdList = reservedHourRepository.getReservedHourIdByDateAndInstructor(LocalDate.parse(wantedDate), instructorId);
            List<ReservedHour> reservedHours = reservedHourRepository.findAllById(reservedHourIdList);

            List<HourCard> returnList = new ArrayList<>();
            for (ReservedHour i : reservedHours) {
                returnList.add(new HourCard(i.getStartTime(), i.getEndTime(), i.getDrivingLessons().getDstudent().getStudentUser().getFirstName() + i.getDrivingLessons().getDstudent().getStudentUser().getLastName(), i.getDrivingLessons().getId()));
            }

            return ResponseEntity.ok().body(returnList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Object> getDrivingLessonById(Integer id) {
        try {
            if (id == null) {
                return ResponseEntity.status(422).build();
            }

            DrivingLessons searchedDrivingLesson = drivingLessonRepository.getDrivingLesson(id).orElse(null);
            if (searchedDrivingLesson == null) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok().body(searchedDrivingLesson);
            }

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