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
import java.util.Date;
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

    public ResponseEntity<Object> getDrivingLicenseCategoriesBySchool(Integer schoolId) {
        try {
            if (schoolId == null) {
                return ResponseEntity.status(422).build();
            }
            School searchedSchool = schoolRepository.getSchool(schoolId).orElse(null);
            if (searchedSchool == null || searchedSchool.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(searchedSchool.getLicenseCategoryList());
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

    public ResponseEntity<Object> updateDrivingLesson(Integer id, Integer startKm, Integer endKm, String location, String pickUpPlace, String dropOffPlace, Integer lessonHourNumber, Boolean isPaid, Integer statusId, Integer paymentMethodId) {
        try {
            DrivingLessons searchedDrivingLesson = drivingLessonRepository.getDrivingLesson(id).orElse(null);
            if (searchedDrivingLesson == null) {
                return ResponseEntity.status(404).body("lessonNotFound");
            } else {
                PaymentMethod searchedPayment = paymentMethodRepository.getPaymentMethod(paymentMethodId).orElse(null);
                Status searchedStatus = statusRepository.getStatus(statusId).orElse(null);

                if (endKm <= startKm) {
                    return ResponseEntity.status(415).body("invalidStartEndKm");
                } else if (lessonHourNumber <= 0) {
                    return ResponseEntity.status(415).body("invalidLessonHourNumber");
                } else if (searchedPayment == null) {
                    return ResponseEntity.status(404).body("");
                } else if (searchedStatus == null) {
                    return ResponseEntity.status(404).body("");
                }  else {
                    searchedDrivingLesson.setStartKm(startKm);
                    searchedDrivingLesson.setEndKm(endKm);
                    searchedDrivingLesson.setLocation(location);
                    searchedDrivingLesson.setPickUpPlace(pickUpPlace);
                    searchedDrivingLesson.setDropOffPlace(dropOffPlace);
                    searchedDrivingLesson.setLessonHourNumber(lessonHourNumber);
                    searchedDrivingLesson.setIsPaid(isPaid);
                    searchedDrivingLesson.setPaymentMethod(searchedPayment);
                    searchedDrivingLesson.setDrivingLessonStatus(searchedStatus);
                    return ResponseEntity.ok().body(drivingLessonRepository.save(searchedDrivingLesson));
                }
            }
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
            List<ReservedHour> reservedHours = new ArrayList<ReservedHour>();
            for (Integer i : reservedHourIdList) {
                reservedHours.add(reservedHourRepository.findById(i).get());
            }

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