package csapat.DrivingLicenseAppAPI.service;

import csapat.DrivingLicenseAppAPI.entity.*;
import csapat.DrivingLicenseAppAPI.repository.*;
import csapat.DrivingLicenseAppAPI.service.other.ValidatorCollection;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Transactional
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
            School searchedSchool = schoolRepository.findById(schoolId).get();
            Users searchedUser = userRepository.findById(userId).get();
            ArrayList<String> roleList = new ArrayList<String>(Arrays.asList("student", "instructor"));

            if (searchedSchool.getId() == null || searchedSchool.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else if (searchedUser.getId() == null || searchedUser.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else if (!roleList.contains(requestedRole)) {
                return ResponseEntity.notFound().build();
            } else {
                SchoolJoinRequest newSchoolJoinRequest = new SchoolJoinRequest(requestedRole, searchedUser, searchedSchool);
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
            Students searchedStudent = studentRepository.findById(studentId).get();
            Instructors searchedInstructor = instructorRepository.findById(instructorId).get();

            if (searchedInstructor.getId() == null || searchedInstructor.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else if (searchedStudent.getId() == null || searchedStudent.getIsDeleted()) {
                return ResponseEntity.notFound().build();
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

    public ResponseEntity<Object> deleteSchoolJoinRequest(Integer id) {
        try {
            SchoolJoinRequest searchedRequest = schoolJoinRequestRepository.getSchoolJoinRequest(id).orElse(null);

            if (searchedRequest.getId() == null || searchedRequest.getIsDeleted()) {
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
            InstructorJoinRequest searchedRequest = instructorJoinRequestRepository.getInstructorJoinRequest(id).orElse(null);

            if (searchedRequest.getId() == null || searchedRequest.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok().body(instructorJoinRequestRepository.deleteInstructorJoinRequest(id));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Object> addDrivingLessonRequest(DrivingLessonRequest addedDrivingLessonRequest) {
        try {
            if (!ValidatorCollection.startEndValidator(addedDrivingLessonRequest.getStartHour(), addedDrivingLessonRequest.getStartMin(), addedDrivingLessonRequest.getEndHour(), addedDrivingLessonRequest.getEndMin())) {
                return ResponseEntity.status(417).build();
            } else {
                Instructors searchedInstructor = instructorRepository.getInstructor(addedDrivingLessonRequest.getDLessonInstructor().getId()).orElse(null);
                Students searchedStudent = studentRepository.getStudent(addedDrivingLessonRequest.getDLessonRequestStudent().getId()).orElse(null);

                if (searchedInstructor == null || searchedInstructor.getId() == null || searchedInstructor.getIsDeleted()) {
                    return ResponseEntity.notFound().build();
                } else if (searchedStudent == null || searchedStudent.getId() == null || searchedStudent.getIsDeleted()) {
                    return ResponseEntity.notFound().build();
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

    public ResponseEntity<Object> addExamRequest(ExamRequest addedExamRequest) {
        try {
            Instructors searchedInstructor = instructorRepository.getInstructor(addedExamRequest.getExamRequesterInstructor().getId()).orElse(null);
            Students searchedStudent = studentRepository.getStudent(addedExamRequest.getExamStudent().getId()).orElse(null);
            School searchedSchool = schoolRepository.getSchool(addedExamRequest.getExamSchool().getId()).orElse(null);

            if (searchedInstructor == null || searchedInstructor.getId() == null || searchedInstructor.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else if (searchedStudent == null || searchedStudent.getId() == null || searchedStudent.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                examRequestRepository.save(addedExamRequest);
                return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Object> deleteDrivingLessonRequest(Integer requestId) {
        try {
            DrivingLessonRequest searchedRequest = drivingLessonRequestRepository.getDrivingLessonRequest(requestId).orElse(null);

            if (searchedRequest == null || searchedRequest.getId() == null || searchedRequest.getIsDeleted()) {
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
            ExamRequest searchedRequest = examRequestRepository.getExamRequest(requestId).orElse(null);
            if (searchedRequest == null || searchedRequest.getId() == null || searchedRequest.getIsDeleted()) {
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
