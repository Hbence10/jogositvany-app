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
import java.util.Date;

@Transactional(noRollbackFor = {DataIntegrityViolationException.class, ConstraintViolationException.class, SQLIntegrityConstraintViolationException.class, SQLException.class})
@Service
@RequiredArgsConstructor
public class RequestService {

    private final DrivingLessonRequestRepository drivingLessonRequestRepository;
    private final DrivingLicenseCategoryRepository drivingLicenseCategoryRepository;
    private final InstructorJoinRequestRepository instructorJoinRequestRepository;
    private final SchoolJoinRequestRepository schoolJoinRequestRepository;
    private final SchoolRepository schoolRepository;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;
    private final UserRepository userRepository;

    public ResponseEntity<Object> sendSchoolJoinRequest(Integer schoolId, Integer userId, Integer categoryId) {
        try {
            if (schoolId == null || userId == null || categoryId == null) {
                return ResponseEntity.status(422).build();
            }

            School searchedSchool = schoolRepository.getSchool(schoolId).orElse(null);
            Users searchedUser = userRepository.getUser(userId).orElse(null);
            DrivingLicenseCategory searchedCategory = drivingLicenseCategoryRepository.getDrivingLicenseCategory(categoryId).orElse(null);

            if (searchedSchool== null || searchedSchool.getIsDeleted()) {
                return ResponseEntity.status(404).body("schoolNotFound");
            } else if (searchedUser == null || searchedUser.getIsDeleted()) {
                return ResponseEntity.status(404).body("userNotFound");
            } else if (searchedCategory == null || searchedCategory.getIsDeleted()) {
                return ResponseEntity.status(404).body("categoryNotFound");
            } else {
                SchoolJoinRequest newSchoolJoinRequest = new SchoolJoinRequest(searchedUser, searchedSchool, searchedCategory);
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

    public ResponseEntity<Object> sendDrivingLessonRequest(String msg, Date date, Date startTime, Date endTime, Integer studentId, Integer instructorId) {
        try {
            Students searchedStudent = studentRepository.getStudent(studentId).orElse(null);
            Instructors searchedInstructor = instructorRepository.getInstructor(instructorId).orElse(null);

            if (searchedStudent == null) {
                return ResponseEntity.status(404).body("studentNotFound");
            } else if (searchedInstructor == null) {
                return ResponseEntity.status(404).body("instructorNotFound");
            } else {
                DrivingLessonRequest newRequest = new DrivingLessonRequest(msg, date, startTime, endTime, searchedStudent, searchedInstructor);
                drivingLessonRequestRepository.save(newRequest);
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