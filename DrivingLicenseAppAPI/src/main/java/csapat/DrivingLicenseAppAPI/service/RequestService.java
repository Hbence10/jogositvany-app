package csapat.DrivingLicenseAppAPI.service;

import csapat.DrivingLicenseAppAPI.entity.*;
import csapat.DrivingLicenseAppAPI.repository.*;
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
    }

    public ResponseEntity<Object> sendInstructorJoinRequest(Integer studentId, Integer instructorId) {
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
    }

    public ResponseEntity<Object> deleteSchoolJoinRequest(Integer id) {
        SchoolJoinRequest searchedRequest = schoolJoinRequestRepository.getSchoolJoinRequest(id);

        if (searchedRequest.getId() == null || searchedRequest.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(schoolJoinRequestRepository.deleteSchoolJoinRequest(id));
        }
    }

    public ResponseEntity<Object> deleteInstructorJoinRequest(Integer id) {
        InstructorJoinRequest searchedRequest = instructorJoinRequestRepository.getInstructorJoinRequest(id);

        if (searchedRequest.getId() == null || searchedRequest.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(instructorJoinRequestRepository.deleteInstructorJoinRequest(id));
        }
    }

    public ResponseEntity<Object> addDrivingLessonRequest(DrivingLessonRequest addedDrivingLessonRequest) {
        return null;
    }

    public ResponseEntity<Object> addExamRequest(ExamRequest addedExamRequest) {
        return null;
    }

    public ResponseEntity<Object> deleteDrivingLessonRequest(Integer requestId){
        return null;
    }

    public ResponseEntity<Object> deleteExamRequest(Integer requestId){
        return null;
    }
}
