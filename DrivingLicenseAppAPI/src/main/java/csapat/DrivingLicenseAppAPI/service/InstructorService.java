package csapat.DrivingLicenseAppAPI.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Transactional(noRollbackFor = {DataIntegrityViolationException.class, ConstraintViolationException.class, SQLIntegrityConstraintViolationException.class, SQLException.class})
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
    private final SchoolRepository schoolRepository;
    private final ObjectMapper objectMapper;

    public ResponseEntity<Object> handleRequest(Integer requestId, String status) {
        try {
            if (requestId == null || status == null) {
                return ResponseEntity.status(422).build();
            }

            InstructorJoinRequest searchedJoinRequest = instructorJoinRequestRepository.getInstructorJoinRequest(requestId).orElse(null);

            if (searchedJoinRequest == null || searchedJoinRequest.getIsDeleted()) {
                return ResponseEntity.status(404).body("requestNotFound");
            } else if (!status.trim().equals("accept") && !status.trim().equals("refuse")) {
                return ResponseEntity.status(415).body("invalidStatus");
            } else {
                if (status.trim().equals("accept")) {
                    Students student = searchedJoinRequest.getInstructorJoinRequestStudent();
                    student.setStudentInstructor(searchedJoinRequest.getInstructorJoinRequestInstructor());

                    studentRepository.save(student);
                    searchedJoinRequest.setIsAccepted(true);
                } else if (status.trim().equals("refuse")) {
                    searchedJoinRequest.setIsAccepted(false);
                }
                searchedJoinRequest.setAcceptedAt(new Date());
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
                return ResponseEntity.ok().body(searchedInstructor.getInstructorJoinRequestList().stream().filter(request -> !request.getIsDeleted() && request.getIsAccepted() == null).toList());
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
                return ResponseEntity.ok().body(searchedInstructor.getDrivingLessonRequestList().stream().filter(request -> !request.getIsDeleted() && request.getIsAccepted() == null).toList());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Object> updateInstructor(Integer instructorId, String promoText, Integer vehicleId, String vehicleName, String licensePlate, Integer fuelTypeId, Integer vehicleTypeId) {
        try {
            if (instructorId == null || promoText == null || vehicleId == null || vehicleName == null || licensePlate == null || fuelTypeId == null || vehicleTypeId == null) {
                return ResponseEntity.status(422).build();
            }

            Instructors searchedInstructors = instructorRepository.getInstructor(instructorId).orElse(null);
            Vehicle searchedVehicle = vehicleRepository.getVehicle(vehicleId).orElse(null);
            FuelType searchedFuelType = fuelTypeRepository.getFuelType(fuelTypeId).orElse(null);
            VehicleType searchedVehicleType = vehicleTypeRepository.getVehicleType(vehicleTypeId).orElse(null);

            if (searchedInstructors == null || searchedInstructors.getIsDeleted()) {
                return ResponseEntity.status(404).body("instructorNotFound");
            } else if (searchedVehicle == null || searchedVehicle.getIsDeleted()) {
                return ResponseEntity.status(404).body("vehicleNotFound");
            } else if (searchedFuelType == null || searchedFuelType.getIsDeleted()) {
                return ResponseEntity.status(404).body("fuelTypeNotFound");
            } else if (searchedVehicleType == null || searchedVehicleType.getIsDeleted()) {
                return ResponseEntity.status(404).body("vehicleTypeNotFound");
            } else if (licensePlate.length() == 7 || licensePlate.length() == 9) {
                return ResponseEntity.status(415).build();
            } else {
                searchedInstructors.setPromoText(promoText.trim());
                searchedVehicle.setLicensePlate(licensePlate);
                searchedVehicle.setName(vehicleName);
                searchedVehicle.setFuelType(searchedFuelType);
                searchedVehicle.setVehicleType(searchedVehicleType);
                searchedInstructors.setVehicle(vehicleRepository.save(searchedVehicle));
                return ResponseEntity.ok().body(instructorRepository.save(searchedInstructors).getInstructorUser());
            }
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(409).body("duplicateLicensePlate");
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

    public ResponseEntity<Object> getInstructorsBySearch(Integer fuelTypeId, Integer schoolId) {
        try {
            if (fuelTypeId == null || schoolId == null) {
                return ResponseEntity.status(422).build();
            }

            FuelType searchedFuelType = fuelTypeRepository.getFuelType(fuelTypeId).orElse(null);
            School searchedSchool = schoolRepository.getSchool(schoolId).orElse(null);
            if (searchedFuelType == null || searchedFuelType.getIsDeleted()) {
                return ResponseEntity.status(404).body("fuelTypeNotFound");
            } else if (searchedSchool == null || searchedSchool.getIsDeleted()) {
                return ResponseEntity.status(404).body("schoolNotFound");
            } else {
                List<Integer> searchedInstructorsId = instructorRepository.getInstructorBySearch(fuelTypeId, schoolId);
                List<JsonNode> searchedInstructors = new ArrayList<>();

                for (Integer id : searchedInstructorsId) {
                    Instructors searchedInstructor = instructorRepository.getInstructor(id).orElse(null);
                    if (searchedInstructor != null) {
                        JsonNode instructorNode = objectMapper.createObjectNode();
                        ((ObjectNode) instructorNode).put("id", searchedInstructor.getId());
                        ((ObjectNode) instructorNode).put("name", searchedInstructor.getInstructorUser().getFirstName() + " " + searchedInstructor.getInstructorUser().getLastName());
                        searchedInstructors.add(instructorNode);
                    }
                }

                return ResponseEntity.ok().body(searchedInstructors.stream().filter(Objects::nonNull).toList());
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

    public ResponseEntity<Object> getStudentsByInstructor(Integer id) {
        try {
            if (id == null) {
                return ResponseEntity.status(422).build();
            }
            Instructors searchedInstructor = instructorRepository.getInstructor(id).orElse(null);
            if (searchedInstructor == null || searchedInstructor.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok().body(searchedInstructor.getStudents().stream().filter(student -> student.getIsDeleted()).toList());
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