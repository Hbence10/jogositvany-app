package csapat.DrivingLicenseAppAPI.service;

import csapat.DrivingLicenseAppAPI.entity.DrivingLessonRequest;
import csapat.DrivingLicenseAppAPI.entity.InstructorJoinRequest;
import csapat.DrivingLicenseAppAPI.entity.Instructors;
import csapat.DrivingLicenseAppAPI.entity.Students;
import csapat.DrivingLicenseAppAPI.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class InstructorService {

    private final InstructorRepository instructorRepository;
    private final VehicleRepository vehicleRepository;
    private final VehicleTypeRepository vehicleTypeRepository;
    private final InstructorJoinRequestRepository instructorJoinRequestRepository;
    private final StudentRepository studentRepository;

    public ResponseEntity<Object> handleRequest(Integer requestId, String status) {
        InstructorJoinRequest searchedJoinRequest = instructorJoinRequestRepository.getInstructorJoinRequest(requestId);

        if (searchedJoinRequest == null || searchedJoinRequest.getId() == null || searchedJoinRequest.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else if (!status.equals("accept") || !status.equals("refuse")) {
            return null;
        } else {
            if (status.equals("accept")) {
                Students student = searchedJoinRequest.getInstructorJoinRequestStudent();
                student.setStudentInstructor(searchedJoinRequest.getInstructorJoinRequestInstructor());

                studentRepository.save(student);
                searchedJoinRequest.setIsAccepted(true);
            } else {
                searchedJoinRequest.setIsAccepted(false);
            }
            searchedJoinRequest.setAcceptedAt(LocalDateTime.now());
            return ResponseEntity.ok().body(instructorJoinRequestRepository.save(searchedJoinRequest));
        }
    }

    public ResponseEntity<List<InstructorJoinRequest>> getAllJoinRequestByInstructor(Integer id) {
        Instructors searchedInstructor = instructorRepository.getInstructor(id);
        if (searchedInstructor == null || searchedInstructor.getId() == null || searchedInstructor.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(searchedInstructor.getInstructorJoinRequestList().stream().filter(request -> !request.getIsDeleted() && request.getIsAccepted() != null).toList());
        }
    }

    public ResponseEntity<Object> deleteInstructor(Integer instructorId) {
        return null;
    }

    public ResponseEntity<List<DrivingLessonRequest>> getDrivingLessonRequestByInstructor(Integer instructorId) {
        return null;
    }

    public ResponseEntity<Instructors> updateInstructor(Instructors updatedInstructor) {
        return null;
    }

    public ResponseEntity<Object> addStudent(Integer requestId) {
        return null;
    }

    public ResponseEntity<Object> handleDrivingLessonRequest(){
        return null;
    }
}
