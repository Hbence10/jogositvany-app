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
    private final DrivingLessonRequestRepository drivingLessonRequestRepository;
    private final DrivingLessonTypeRepository drivingLessonTypeRepository;
    private final ReservedDateRepository reservedDateRepository;
    private final ReservedHourRepository reservedHourRepository;
    private final StudentRepository studentRepository;

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
                drivingLessonRepository.deleteDrivingLesson(drivingLessonId);
                return ResponseEntity.ok().build();
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

    public ResponseEntity<Object> handleDrivingLessonRequest(Integer requestId, String status) {
        try {
            if (requestId == null || status == null) {
                return ResponseEntity.status(422).build();
            } else if (!status.trim().equals("accepted") && !status.trim().equals("refuse")) {
                return ResponseEntity.status(415).build();
            }

            DrivingLessonRequest searchedDRequest = drivingLessonRequestRepository.getDrivingLessonRequest(requestId).orElse(null);
            if (searchedDRequest == null || searchedDRequest.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                searchedDRequest.setAcceptedAt(new Date());
                if (status.trim().equals("accepted")) {
                    ReservedHour newReservedHour = new ReservedHour(searchedDRequest.getStartTime(), searchedDRequest.getEndTime());
                    ReservedDate searchedReservedDate = reservedDateRepository.getReservedDateByDate(searchedDRequest.getDate()).orElse(new ReservedDate(searchedDRequest.getDate()));
                    newReservedHour.setReservedDate(searchedReservedDate);

                    DrivingLessons newDrivingLesson = new DrivingLessons(newReservedHour, searchedDRequest.getDLessonRequestStudent(), searchedDRequest.getDLessonInstructor(), searchedDRequest.getDLessonRequestType());
                    drivingLessonRepository.save(newDrivingLesson);
                    searchedDRequest.setIsAccepted(true);
                } else {
                    searchedDRequest.setIsAccepted(false);
                }

                drivingLessonRequestRepository.save(searchedDRequest);
                return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Object> updateDrivingLessonRequest(DrivingLessons updatedDrivingLesson) {
        try {
            if (updatedDrivingLesson == null) {
                return ResponseEntity.status(422).build();
            }

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Object> rescheduleDrivingLesson(String newDateText, Integer newStart, Integer newEnd) {
        try {
            if (newDateText == null || newStart == null || newEnd == null) {
                return ResponseEntity.status(422).build();
            }

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Object> setDrivingLessonEnd(Integer id) {
        try {
            if (id == null) {
                return ResponseEntity.status(422).build();
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