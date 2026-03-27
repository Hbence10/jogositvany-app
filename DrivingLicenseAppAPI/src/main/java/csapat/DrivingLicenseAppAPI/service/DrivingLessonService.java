package csapat.DrivingLicenseAppAPI.service;

import csapat.DrivingLicenseAppAPI.config.email.EmailSender;
import csapat.DrivingLicenseAppAPI.dto.DrivingLessonUpdate;
import csapat.DrivingLicenseAppAPI.dto.HourCard;
import csapat.DrivingLicenseAppAPI.entity.*;
import csapat.DrivingLicenseAppAPI.exception.InvalidDataException;
import csapat.DrivingLicenseAppAPI.exception.NotFoundException;
import csapat.DrivingLicenseAppAPI.repository.*;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Transactional
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
        if (schoolId == null) {
            return ResponseEntity.status(422).build();
        }
        School searchedSchool = schoolRepository.getSchool(schoolId).orElseThrow(() -> new NotFoundException("schoolNotFound"));
        return ResponseEntity.ok().body(searchedSchool.getLicenseCategoryList());
    }

    @PreAuthorize("(hasRole('instructor') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> cancelDrivingLesson(Long drivingLessonId) {
        DrivingLessons searchedDrivingLesson = drivingLessonRepository.getDrivingLesson(drivingLessonId).orElseThrow(() -> new NotFoundException("drivingLessonNotFound"));
        if (searchedDrivingLesson.getReservedHour().getReservedDate().getDate().before(new Date())) {
            throw new InvalidDataException("invalidDate");
        } else {
            reservedHourRepository.deleteReservedHour(searchedDrivingLesson.getReservedHour().getId());
            drivingLessonRepository.deleteDrivingLesson(drivingLessonId);
            try {
                emailSender.sendEmailAboutDrivingLessonCanceled(searchedDrivingLesson.getDstudent().getStudentUser().getEmail(), searchedDrivingLesson);
            } catch (MessagingException e) {
            }
            return ResponseEntity.ok().body(drivingLessonRepository.getDrivingLesson(drivingLessonId).get());
        }
    }

    @PreAuthorize("(hasRole('instructor') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> updateDrivingLesson(Long id, DrivingLessonUpdate updatedLesson) {
        DrivingLessons searchedDrivingLesson = drivingLessonRepository.getDrivingLesson(id).orElseThrow(() -> new NotFoundException("drivingLessonNotFound"));
        PaymentMethod searchedPayment = paymentMethodRepository.getPaymentMethod(updatedLesson.paymentMethodId()).orElseThrow(() -> new NotFoundException("paymentMethodNotFound"));
        Status searchedStatus = statusRepository.getStatus(updatedLesson.statusId()).orElseThrow(() -> new NotFoundException("statusNotFound"));

        if (updatedLesson.startKm() >= updatedLesson.endKm()) {
            throw new InvalidDataException("invalidStartEndKm");
        } else if (updatedLesson.lessonHourNumber() <= 0) {
            throw new InvalidDataException("invalidLessonHourNumber");
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

    @PreAuthorize("(hasAnyRole('instructor', 'student') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> getReservedHoursByDate(Long instructorId, String wantedDate) {
        List<Long> reservedHourIdList = reservedHourRepository.getReservedHourIdByDateAndInstructor(LocalDate.parse(wantedDate), instructorId);
        List<ReservedHour> reservedHours = reservedHourRepository.findAllById(reservedHourIdList);

        List<HourCard> returnList = new ArrayList<>();
        for (ReservedHour i : reservedHours) {
            returnList.add(new HourCard(i.getStartTime(), i.getEndTime(), i.getDrivingLessons().getDstudent().getStudentUser().getFirstName() + i.getDrivingLessons().getDstudent().getStudentUser().getLastName(), i.getDrivingLessons().getId(), i.getReservedDate().getDate()));
        }

        return ResponseEntity.ok().body(returnList);
    }

    @PreAuthorize("(hasAnyRole('instructor', 'student') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> getDrivingLessonById(Long id) {
        DrivingLessons searchedDrivingLesson = drivingLessonRepository.getDrivingLesson(id).orElseThrow(() -> new NotFoundException("drivingLessonNotFound"));
        return ResponseEntity.ok().body(searchedDrivingLesson);
    }

    @PreAuthorize("(hasAnyRole('instructor', 'student') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> getReservedHoursBetweenDates(Long instructorId, String start, String end) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMAN);
            List<Long> idList = reservedHourRepository.getReservedHoursBetweenTwoDate(instructorId, dateFormat.parse(start), dateFormat.parse(end));
            List<ReservedHour> reservedHours = reservedHourRepository.findAllById(idList);

            List<HourCard> returnList = new ArrayList<>();
            for (ReservedHour i : reservedHours) {
                returnList.add(new HourCard(i.getStartTime(), i.getEndTime(), i.getDrivingLessons().getDstudent().getStudentUser().getFirstName() + i.getDrivingLessons().getDstudent().getStudentUser().getLastName(), i.getDrivingLessons().getId(), i.getReservedDate().getDate()));
            }

            return ResponseEntity.ok().body(returnList);
        } catch (ParseException e) {
            throw new InvalidDataException("invalidDateFormat");
        }
    }
}