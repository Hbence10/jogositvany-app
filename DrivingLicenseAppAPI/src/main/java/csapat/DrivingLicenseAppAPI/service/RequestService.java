package csapat.DrivingLicenseAppAPI.service;

import csapat.DrivingLicenseAppAPI.entity.*;
import csapat.DrivingLicenseAppAPI.repository.*;
import csapat.DrivingLicenseAppAPI.service.other.ValidatorCollection;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@Transactional(noRollbackFor = {DataIntegrityViolationException.class, ConstraintViolationException.class, SQLIntegrityConstraintViolationException.class, SQLException.class})
@Service
@RequiredArgsConstructor
public class RequestService {

    private final DrivingLessonRequestRepository drivingLessonRequestRepository;
    private final ExamRequestRepository examRequestRepository;
    private final InstructorJoinRequestRepository instructorJoinRequestRepository;
    private final SchoolJoinRequestRepository schoolJoinRequestRepository;
    private final SchoolRepository schoolRepository;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;
    private final UserRepository userRepository;

    public ResponseEntity<Object> sendSchoolJoinRequest(Integer schoolId, Integer userId, String requestedRole) {
        try {
            if (schoolId == null || userId == null || requestedRole == null) {
                return ResponseEntity.status(422).build();
            }

            School searchedSchool = schoolRepository.getSchool(schoolId).orElse(null);
            Users searchedUser = userRepository.getUser(userId).orElse(null);

            if (searchedSchool== null || searchedSchool.getIsDeleted()) {
                return ResponseEntity.status(404).body("schoolNotFound");
            } else if (searchedUser == null || searchedUser.getIsDeleted()) {
                return ResponseEntity.status(404).body("userNotFound");
            } else if (!requestedRole.trim().equals("student") && !requestedRole.trim().equals("instructor")) {
                return ResponseEntity.status(415).body("invalidRole");
            } else {
                SchoolJoinRequest newSchoolJoinRequest = new SchoolJoinRequest(requestedRole.trim(), searchedUser, searchedSchool);
                schoolJoinRequestRepository.save(newSchoolJoinRequest);
                return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Object> sendInstructorJoinRequest(Integer studentId, Integer instructorId) {
        try {
            if (studentId == null || instructorId == null) {
                return ResponseEntity.status(422).build();
            }

            Students searchedStudent = studentRepository.getStudent(studentId).orElse(null);
            Instructors searchedInstructor = instructorRepository.getInstructor(instructorId).orElse(null);

            if (searchedInstructor == null || searchedInstructor.getIsDeleted()) {
                return ResponseEntity.status(404).body("instructorNotFound");
            } else if (searchedStudent == null || searchedStudent.getIsDeleted()) {
                return ResponseEntity.status(404).body("studentNotFound");
            } else if (searchedStudent.getStudentSchool().getId() != searchedInstructor.getInstructorSchool().getId()){
                return ResponseEntity.status(415).body("invalidInstructor");
            } else {
                InstructorJoinRequest instructorJoinRequest = new InstructorJoinRequest(searchedStudent, searchedInstructor);
                instructorJoinRequestRepository.save(instructorJoinRequest);
                return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Object> sendDrivingLessonRequest(DrivingLessonRequest addedDrivingLessonRequest) {
        try {
            if (addedDrivingLessonRequest == null) {
                return ResponseEntity.status(422).build();
            }

            if (!ValidatorCollection.startEndValidator(addedDrivingLessonRequest.getStartTime().getHours(), addedDrivingLessonRequest.getStartTime().getMinutes(), addedDrivingLessonRequest.getEndTime().getHours(), addedDrivingLessonRequest.getEndTime().getMinutes())) {
                return ResponseEntity.status(415).body("invalidStartEndRange");
            } else {
                Instructors searchedInstructor = instructorRepository.getInstructor(addedDrivingLessonRequest.getDLessonInstructor().getId()).orElse(null);
                Students searchedStudent = studentRepository.getStudent(addedDrivingLessonRequest.getDLessonRequestStudent().getId()).orElse(null);

                if (searchedInstructor == null || searchedInstructor.getIsDeleted()) {
                    return ResponseEntity.status(404).body("instructorNotFound");
                } else if (searchedStudent == null || searchedStudent.getIsDeleted()) {
                    return ResponseEntity.status(404).body("studentNotFound");
                } else if (searchedStudent.getStudentInstructor().getId() != searchedInstructor.getId()){
                    return ResponseEntity.status(415).body("invalidInstructor");
                } else if (searchedStudent.getStudentSchool().getId() != searchedInstructor.getInstructorSchool().getId()){
                    return ResponseEntity.status(415).body("invalidInstructor");
                } else {
                    drivingLessonRequestRepository.save(addedDrivingLessonRequest);
                    return ResponseEntity.ok().build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Object> sendExamRequest(ExamRequest addedExamRequest) {
        try {
            if (addedExamRequest == null) {
                return ResponseEntity.status(422).build();
            }

            Instructors searchedInstructor = instructorRepository.getInstructor(addedExamRequest.getExamRequesterInstructor().getId()).orElse(null);
            Students searchedStudent = studentRepository.getStudent(addedExamRequest.getExamStudent().getId()).orElse(null);
            School searchedSchool = schoolRepository.getSchool(addedExamRequest.getExamSchool().getId()).orElse(null);

            if (searchedInstructor == null || searchedInstructor.getIsDeleted()) {
                return ResponseEntity.status(404).body("instructorNotFound");
            } else if (searchedStudent == null || searchedStudent.getIsDeleted()) {
                return ResponseEntity.status(404).body("studentNotFound");
            } else if (searchedSchool == null || searchedSchool.getIsDeleted()) {
                return ResponseEntity.status(404).body("schoolNotFound");
            } else {
                examRequestRepository.save(addedExamRequest);
                return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Object> deleteSchoolJoinRequest(Integer id) {
        try {
            if (id == null) {
                return ResponseEntity.status(422).build();
            }

            SchoolJoinRequest searchedRequest = schoolJoinRequestRepository.getSchoolJoinRequest(id).orElse(null);

            if (searchedRequest == null || searchedRequest.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok().body(schoolJoinRequestRepository.deleteSchoolJoinRequest(id));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Object> deleteInstructorJoinRequest(Integer id) {
        try {
            if (id == null) {
                return ResponseEntity.status(422).build();
            }

            InstructorJoinRequest searchedRequest = instructorJoinRequestRepository.getInstructorJoinRequest(id).orElse(null);

            if (searchedRequest == null || searchedRequest.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok().body(instructorJoinRequestRepository.deleteInstructorJoinRequest(id));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Object> deleteDrivingLessonRequest(Integer requestId) {
        try {
            if (requestId == null) {
                return ResponseEntity.status(422).build();
            }

            DrivingLessonRequest searchedRequest = drivingLessonRequestRepository.getDrivingLessonRequest(requestId).orElse(null);

            if (searchedRequest == null || searchedRequest.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                drivingLessonRequestRepository.deleteDrivingLessonRequest(requestId);
                return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Object> deleteExamRequest(Integer requestId) {
        try {
            if (requestId == null) {
                return ResponseEntity.status(422).build();
            }

            ExamRequest searchedRequest = examRequestRepository.getExamRequest(requestId).orElse(null);
            if (searchedRequest == null || searchedRequest.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                examRequestRepository.deleteExamRequest(requestId);
                return ResponseEntity.ok().build();
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