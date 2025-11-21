package csapat.DrivingLicenseAppAPI.service;

import csapat.DrivingLicenseAppAPI.entity.InstructorJoinRequest;
import csapat.DrivingLicenseAppAPI.entity.Students;
import csapat.DrivingLicenseAppAPI.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
        InstructorJoinRequest searchedJoinRequest = instructorJoinRequestRepository.findById(requestId).get();

        if (searchedJoinRequest == null || searchedJoinRequest.getId() == null || searchedJoinRequest.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        }  else if (!status.equals("accept") || !status.equals("refuse")) {
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
}
