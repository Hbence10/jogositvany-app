package csapat.DrivingLicenseAppAPI.service;

import csapat.DrivingLicenseAppAPI.config.email.EmailSender;
import csapat.DrivingLicenseAppAPI.dto.DrivingLessonUpdate;
import csapat.DrivingLicenseAppAPI.entity.*;
import csapat.DrivingLicenseAppAPI.repository.*;
import csapat.DrivingLicenseAppAPI.dto.HourCard;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Transactional(noRollbackFor = {DataIntegrityViolationException.class, ConstraintViolationException.class, SQLIntegrityConstraintViolationException.class, SQLException.class})
@Service
@RequiredArgsConstructor
public class DrivingLessonService {

    private final DrivingLessonRepository drivingLessonRepository;
    private final ReservedHourRepository reservedHourRepository;
    private final SchoolRepository schoolRepository;
    private final StatusRepository statusRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final EmailSender emailSender;

    public ResponseEntity<Object> getDrivingLicenseCategoriesBySchool(Long schoolId) {
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

    @PreAuthorize("(hasRole('instructor') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> cancelDrivingLesson(Long drivingLessonId) {
        try {
            if (drivingLessonId == null) {
                return ResponseEntity.status(422).build();
            }

            DrivingLessons searchedDrivingLesson = drivingLessonRepository.getDrivingLesson(drivingLessonId).orElse(null);
            if (searchedDrivingLesson == null || searchedDrivingLesson.getIsCancelled()) {
                return ResponseEntity.notFound().build();
            } else if (searchedDrivingLesson.getReservedHour().getReservedDate().getDate().before(new Date())) {
              return ResponseEntity.status(415).body("invalidDate");
            } else {
                reservedHourRepository.deleteReservedHour(searchedDrivingLesson.getReservedHour().getId());
                drivingLessonRepository.deleteDrivingLesson(drivingLessonId);
                try {
                    emailSender.sendEmailAboutDrivingLessonCanceled(searchedDrivingLesson.getDstudent().getStudentUser().getEmail(), searchedDrivingLesson);
                } catch (MessagingException e) {
                }
                return ResponseEntity.ok().body(drivingLessonRepository.getDrivingLesson(drivingLessonId).get());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("(hasRole('instructor') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> updateDrivingLesson(Long id, DrivingLessonUpdate updatedLesson) {
        try {
            DrivingLessons searchedDrivingLesson = drivingLessonRepository.getDrivingLesson(id).orElse(null);
            if (searchedDrivingLesson == null) {
                return ResponseEntity.status(404).body("lessonNotFound");
            } else {
                PaymentMethod searchedPayment = paymentMethodRepository.getPaymentMethod(updatedLesson.paymentMethodId()).orElse(null);
                Status searchedStatus = statusRepository.getStatus(updatedLesson.statusId()).orElse(null);

                if (updatedLesson.startKm() >= updatedLesson.endKm()) {
                    return ResponseEntity.status(415).body("invalidStartEndKm");
                } else if (updatedLesson.lessonHourNumber() <= 0) {
                    return ResponseEntity.status(415).body("invalidLessonHourNumber");
                } else if (searchedPayment == null) {
                    return ResponseEntity.status(404).body("paymentMethodNotFound");
                } else if (searchedStatus == null) {
                    return ResponseEntity.status(404).body("statusNotFound");
                } else {
                    searchedDrivingLesson.setStartKm(updatedLesson.startKm());
                    searchedDrivingLesson.setEndKm(updatedLesson.endKm());
                    searchedDrivingLesson.setLocation(updatedLesson.location());
                    searchedDrivingLesson.setPickUpPlace(updatedLesson.pickUpPlace());
                    searchedDrivingLesson.setDropOffPlace(updatedLesson.dropOffPlace());
                    searchedDrivingLesson.setLessonHourNumber(updatedLesson.lessonHourNumber());
                    searchedDrivingLesson.setIsPaid(updatedLesson.isPaid());
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

    @PreAuthorize("(hasAnyRole('instructor', 'student') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> getReservedHoursByDate(Long instructorId, String wantedDate) {
        try {
            if (instructorId == null || wantedDate == null) {
                return ResponseEntity.status(422).build();
            }

            List<Long> reservedHourIdList = reservedHourRepository.getReservedHourIdByDateAndInstructor(LocalDate.parse(wantedDate), instructorId);
            List<ReservedHour> reservedHours = reservedHourRepository.findAllById(reservedHourIdList);

            List<HourCard> returnList = new ArrayList<>();
            for (ReservedHour i : reservedHours) {
                returnList.add(new HourCard(i.getStartTime(), i.getEndTime(), i.getDrivingLessons().getDstudent().getStudentUser().getFirstName() + i.getDrivingLessons().getDstudent().getStudentUser().getLastName(), i.getDrivingLessons().getId(), i.getReservedDate().getDate()));
            }

            return ResponseEntity.ok().body(returnList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("(hasAnyRole('instructor', 'student') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> getDrivingLessonById(Long id) {
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

    @PreAuthorize("(hasAnyRole('instructor', 'student') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> getReservedHoursBetweenDates(Long instructorId, String start, String end) {
        try {
            if (instructorId == null || start == null || end == null) {
                return ResponseEntity.status(422).build();
            }

            //validaciok:
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMAN);
            List<Long> idList = reservedHourRepository.getReservedHoursBetweenTwoDate(instructorId, dateFormat.parse(start), dateFormat.parse(end));
            List<ReservedHour> reservedHours = reservedHourRepository.findAllById(idList);

            List<HourCard> returnList = new ArrayList<>();
            for (ReservedHour i : reservedHours) {
                returnList.add(new HourCard(i.getStartTime(), i.getEndTime(), i.getDrivingLessons().getDstudent().getStudentUser().getFirstName() + i.getDrivingLessons().getDstudent().getStudentUser().getLastName(), i.getDrivingLessons().getId(), i.getReservedDate().getDate()));
            }

            return ResponseEntity.ok().body(returnList);
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