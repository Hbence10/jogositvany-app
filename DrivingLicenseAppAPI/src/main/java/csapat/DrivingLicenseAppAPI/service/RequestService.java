package csapat.DrivingLicenseAppAPI.service;

import csapat.DrivingLicenseAppAPI.entity.*;
import csapat.DrivingLicenseAppAPI.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    //Jelentkezesi kerelmek
    public ResponseEntity<Object> sendSchoolJoinRequest(Integer schoolId, Integer userId, String requestedRole) {
        School searchedSchool = schoolRepository.findById(schoolId).get();
        User searchedUser = userRepository.findById(userId).get();
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
        SchoolJoinRequest searchedRequest = schoolJoinRequestRepository.findById(id).get();

        if (searchedRequest.getId() == null || searchedRequest.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else {
            searchedRequest.setIsDeleted(true);
            searchedRequest.setDeletedAt(LocalDateTime.now());
            return ResponseEntity.ok().body(schoolJoinRequestRepository.save(searchedRequest));
        }
    }

    public ResponseEntity<Object> deleteInstructorJoinRequest(Integer id) {
        InstructorJoinRequest searchedRequest = instructorJoinRequestRepository.findById(id).get();

        if (searchedRequest.getId() == null || searchedRequest.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else {
            searchedRequest.setIsDeleted(true);
            searchedRequest.setDeletedAt(LocalDateTime.now());
            return ResponseEntity.ok().body(instructorJoinRequestRepository.save(searchedRequest));
        }
    }

    public ResponseEntity<List<SchoolJoinRequest>> getAllJoinRequestBySchool(Integer id){
        School searchedSchool = schoolRepository.findById(id).get();
        if (searchedSchool == null || searchedSchool.getId() == null || searchedSchool.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(searchedSchool.getSchoolJoinRequestList().stream().filter(request -> !request.getIsDeleted() && request.getIsAccepted() != null).toList());
        }
    }

    public ResponseEntity<List<InstructorJoinRequest>> getAllJoinRequestByInstructor(Integer id){
        Instructors searchedInstructor = instructorRepository.findById(id).get();
        if (searchedInstructor == null || searchedInstructor.getId() == null || searchedInstructor.getIsDeleted()){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(searchedInstructor.getInstructorJoinRequestList().stream().filter(request -> !request.getIsDeleted() && request.getIsAccepted() != null).toList());
        }

    }

}
