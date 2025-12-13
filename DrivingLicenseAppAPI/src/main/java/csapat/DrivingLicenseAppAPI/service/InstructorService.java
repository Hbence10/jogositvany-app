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
        try {
            if (requestId == null || status == null) {
                return ResponseEntity.status(422).build();
            }

            InstructorJoinRequest searchedJoinRequest = instructorJoinRequestRepository.getInstructorJoinRequest(requestId).orElse(null);

            if (searchedJoinRequest == null ||  searchedJoinRequest.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else if (!status.trim().equals("accept") && !status.trim().equals("refuse")) {
                return ResponseEntity.status(415).build();
            } else {
                if (status.trim().equals("accept")) {
                    Students student = searchedJoinRequest.getInstructorJoinRequestStudent();
                    student.setStudentInstructor(searchedJoinRequest.getInstructorJoinRequestInstructor());

                    studentRepository.save(student);
                    searchedJoinRequest.setIsAccepted(true);
                } else if (status.trim().equals("refuse")) {
                    searchedJoinRequest.setIsAccepted(false);
                } else {
                    return ResponseEntity.internalServerError().build();
                }
                searchedJoinRequest.setAcceptedAt(LocalDateTime.now());
                return ResponseEntity.ok().body(instructorJoinRequestRepository.save(searchedJoinRequest));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<List<InstructorJoinRequest>> getAllJoinRequestByInstructor(Integer id) {
        try {
            if (id == null) {
                return ResponseEntity.status(422).build();
            }

            Instructors searchedInstructor = instructorRepository.getInstructor(id).orElse(null);
            if (searchedInstructor == null || searchedInstructor.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok().body(searchedInstructor.getInstructorJoinRequestList().stream().filter(request -> !request.getIsDeleted() && request.getIsAccepted() != null).toList());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Object> deleteInstructor(Integer instructorId) {
        try {
            if (instructorId == null) {
                return ResponseEntity.status(422).build();
            }

            Instructors searchedInstructor = instructorRepository.getInstructor(instructorId).orElse(null);
            if (searchedInstructor == null || searchedInstructor.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                instructorRepository.deleteInstructor(instructorId);
                return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<List<DrivingLessonRequest>> getDrivingLessonRequestByInstructor(Integer instructorId) {
        try {
            if (instructorId == null) {
                return ResponseEntity.status(422).build();
            }

            Instructors searchedInstructor = instructorRepository.getInstructor(instructorId).orElse(null);
            if (searchedInstructor == null || searchedInstructor.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok().body(searchedInstructor.getDrivingLessonRequestList());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Instructors> updateInstructor(Instructors updatedInstructor) {
        try {
            if (updatedInstructor == null) {
                return ResponseEntity.status(422).build();
            }

            if (updatedInstructor.getId() == null || updatedInstructor.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else if (!validateVehicle(updatedInstructor.getVehicle())) {
                return ResponseEntity.status(415).build();
            } else {
                return ResponseEntity.ok().body(instructorRepository.save(updatedInstructor));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Object> handleJoinRequest(Integer requestId, String status) {
        try {
            if (requestId == null || status == null) {
                return ResponseEntity.status(422).build();
            }

            InstructorJoinRequest searchedJoinRequest = instructorJoinRequestRepository.getInstructorJoinRequest(requestId).orElse(null);
            if (searchedJoinRequest == null || searchedJoinRequest.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                if (!status.trim().equals("accept") && !status.trim().equals("refuse")) {
                    return ResponseEntity.status(415).build();
                } else {
                    if (status.trim().equals("accept")) {
                        searchedJoinRequest.setIsAccepted(true);
                        searchedJoinRequest.setAcceptedAt(LocalDateTime.now());

                        Students newStudent = searchedJoinRequest.getInstructorJoinRequestStudent();
                        newStudent.setStudentInstructor(searchedJoinRequest.getInstructorJoinRequestInstructor());
                        studentRepository.save(newStudent);

                    } else if (status.trim().equals("refuse")) {
                        searchedJoinRequest.setIsAccepted(false);
                    } else {
                        return ResponseEntity.internalServerError().build();
                    }
                }

                instructorJoinRequestRepository.save(searchedJoinRequest);
                return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Object> handleDrivingLessonRequest(Integer requestId, String status) {
        try {
            if (requestId == null || status == null) {
                return ResponseEntity.status(422).build();
            } else {
                DrivingLessonRequest searchedRequest = drivingLessonRequestRepository.getDrivingLessonRequest(requestId).orElse(null);
                if (searchedRequest == null || searchedRequest.getIsDeleted()) {
                    return ResponseEntity.notFound().build();
                }

                if (!status.equals("accept") && !status.equals("refuse")) {
                    return ResponseEntity.status(415).build();
                } else {
                    if (status.equals("accept")) {

                    } else if (status.equals("refuse")) {

                    }
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<List<Instructors>> getInstructorsBySearch(String name, Integer fuelTypeId) {
        try {
            if (name == null || fuelTypeId == null) {
                return ResponseEntity.status(422).build();
            }

            FuelType searchedFuelType = fuelTypeRepository.getFuelType(fuelTypeId).orElse(null);
            if (searchedFuelType == null || searchedFuelType.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                List<Integer> searchedInstructorsId = instructorRepository.getInstructorBySearch(name, fuelTypeId);
                List<Instructors> searchedInstructors = new ArrayList<>();
                for (Integer id : searchedInstructorsId) {
                    searchedInstructors.add(instructorRepository.getInstructor(id).orElse(null));
                }

                return ResponseEntity.ok().body(searchedInstructors);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Instructors> getInstructorById(Integer id) {
        try {
            if (id == null) {
                return ResponseEntity.status(422).build();
            }

            Instructors searchedInstructor = instructorRepository.getInstructor(id).orElse(null);
            if (searchedInstructor == null || searchedInstructor.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok().body(searchedInstructor);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Vehicle> addVehicle(Vehicle addedVehicle, Integer instructorId){
        try {
            if (addedVehicle == null || instructorId == null) {
                return ResponseEntity.status(422).build();
            }

            Instructors searchedInstructor = instructorRepository.getInstructor(instructorId).orElse(null);
            if (searchedInstructor == null || searchedInstructor.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else if (!validateVehicle(addedVehicle)) {
                return ResponseEntity.status(415).build();
            } else {
                Vehicle newVehicle = vehicleRepository.save(addedVehicle);
                searchedInstructor.setVehicle(newVehicle);
                instructorRepository.save(searchedInstructor);
                return ResponseEntity.ok().body(newVehicle);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    //
    public Boolean validateVehicle(Vehicle wantedVehicle){
        if (wantedVehicle.getLicensePlate().length() == 6 || wantedVehicle.getLicensePlate().length() == 8){
            FuelType searchedFuelType = fuelTypeRepository.getFuelType(wantedVehicle.getFuelType().getId()).orElse(null);
            VehicleType searchedVehicleType = vehicleTypeRepository.getVehicleType(wantedVehicle.getVehicleType().getId()).orElse(null);

            if (searchedFuelType == null || searchedFuelType.getId() == null || searchedFuelType.getIsDeleted()){
                return false;
            } else if (searchedVehicleType == null || searchedVehicleType.getId() == null || searchedVehicleType.getIsDeleted()){
                return false;
            } else {
                return true;
            }
        } else {
            return false;
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