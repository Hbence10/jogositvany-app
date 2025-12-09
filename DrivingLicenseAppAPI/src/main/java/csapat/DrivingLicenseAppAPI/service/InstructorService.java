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
public class InstructorService {

    private final InstructorRepository instructorRepository;
    private final VehicleRepository vehicleRepository;
    private final VehicleTypeRepository vehicleTypeRepository;
    private final InstructorJoinRequestRepository instructorJoinRequestRepository;
    private final StudentRepository studentRepository;
    private final FuelTypeRepository fuelTypeRepository;
    private final DrivingLessonRequestRepository drivingLessonRequestRepository;

    public ResponseEntity<Object> handleRequest(Integer requestId, String status) {
        ArrayList<String> statusTypes = new ArrayList<>(Arrays.asList("accept", "refuse"));
        InstructorJoinRequest searchedJoinRequest = instructorJoinRequestRepository.getInstructorJoinRequest(requestId);

        if (searchedJoinRequest == null || searchedJoinRequest.getId() == null || searchedJoinRequest.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else if (!statusTypes.contains(status)) {
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
        Instructors searchedInstructor = instructorRepository.getInstructor(instructorId);
        if (searchedInstructor == null || searchedInstructor.getId() == null || searchedInstructor.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else {
            instructorRepository.deleteInstructor(instructorId);
            return ResponseEntity.ok().build();
        }
    }

    public ResponseEntity<List<DrivingLessonRequest>> getDrivingLessonRequestByInstructor(Integer instructorId) {
        Instructors searchedInstructor = instructorRepository.getInstructor(instructorId);
        if (searchedInstructor == null || searchedInstructor.getId() == null || searchedInstructor.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(searchedInstructor.getDrivingLessonRequestList());
        }
    }



    public ResponseEntity<Instructors> updateInstructor(Instructors updatedInstructor) {


        return null;
    }

    public ResponseEntity<Object> handleJoinRequest(Integer requestId, String status) {
        if (requestId == null || status == null) {
            return ResponseEntity.status(422).build();
        }

        InstructorJoinRequest searchedJoinRequest = instructorJoinRequestRepository.getInstructorJoinRequest(requestId);
        if (searchedJoinRequest == null || searchedJoinRequest.getId() == null || searchedJoinRequest.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else {
            if (!status.equals("accept") && !status.equals("refuse")) {
                return ResponseEntity.status(417).build();
            } else {
                if (status.equals("accept")) {
                    searchedJoinRequest.setIsAccepted(true);
                    searchedJoinRequest.setAcceptedAt(LocalDateTime.now());

                    Students newStudent = searchedJoinRequest.getInstructorJoinRequestStudent();
                    newStudent.setStudentInstructor(searchedJoinRequest.getInstructorJoinRequestInstructor());
                    studentRepository.save(newStudent);

                } else if (status.equals("refuse")) {
                    searchedJoinRequest.setIsAccepted(false);
                } else {
                    return ResponseEntity.internalServerError().build();
                }
            }

            instructorJoinRequestRepository.save(searchedJoinRequest);
            return ResponseEntity.ok().build();
        }
    }

    public ResponseEntity<Object> handleDrivingLessonRequest(Integer requestId, String status) {
        if (requestId == null || status == null) {

        } else {
            DrivingLessonRequest searchedRequest = drivingLessonRequestRepository.getDrivingLessonRequest(requestId);
            if (searchedRequest == null || searchedRequest.getId() == null || searchedRequest.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            }

            if (!status.equals("accept") && !status.equals("refuse")){

            } else {
                if (status.equals("accept")){

                } else if (status.equals("refuse")) {

                }
            }
        }
        return null;
    }

    public ResponseEntity<List<Instructors>> getInstructorsBySearch(String name, Integer fuelTypeId) {
        if (name == null || fuelTypeId == null) {
            return ResponseEntity.status(422).build();
        }

        FuelType searchedFuelType = fuelTypeRepository.getFuelType(fuelTypeId);
        if (searchedFuelType == null || searchedFuelType.getId() == null || searchedFuelType.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else {
            List<Integer> searchedInstructorsId = instructorRepository.getInstructorBySearch(name, fuelTypeId);
            List<Instructors> searchedInstructors = new ArrayList<>();
            for (Integer id : searchedInstructorsId) {
                searchedInstructors.add(instructorRepository.getInstructor(id));
            }

            return ResponseEntity.ok().body(searchedInstructors);
        }
    }

    public ResponseEntity<Instructors> getInstructorById(Integer id) {
        Instructors searchedInstructor = instructorRepository.getInstructor(id);
        if (searchedInstructor == null || searchedInstructor.getId() == null || searchedInstructor.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(searchedInstructor);
        }
    }
}
