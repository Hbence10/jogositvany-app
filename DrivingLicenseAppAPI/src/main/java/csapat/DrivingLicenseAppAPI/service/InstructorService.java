package csapat.DrivingLicenseAppAPI.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import csapat.DrivingLicenseAppAPI.config.email.EmailSender;
import csapat.DrivingLicenseAppAPI.dto.InstructorUpdate;
import csapat.DrivingLicenseAppAPI.dto.ProfileCard;
import csapat.DrivingLicenseAppAPI.entity.*;
import csapat.DrivingLicenseAppAPI.exception.InvalidDataException;
import csapat.DrivingLicenseAppAPI.exception.NotFoundException;
import csapat.DrivingLicenseAppAPI.exception.UniqueErrorException;
import csapat.DrivingLicenseAppAPI.repository.*;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    private final SchoolRepository schoolRepository;
    private final StatusRepository statusRepository;
    private final ObjectMapper objectMapper;
    private final DrivingLessonRepository drivingLessonRepository;
    private final EmailSender emailSender;
    private final ReservedHourRepository reservedHourRepository;
    private final ReservedDateRepository reservedDateRepository;
    private final UserRepository userRepository;

    @PreAuthorize("(hasRole('instructor') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> handleRequest(Long requestId, String status) {
        InstructorJoinRequest searchedJoinRequest = instructorJoinRequestRepository.getInstructorJoinRequest(requestId).orElseThrow(() -> new NotFoundException("requestNotFound"));

        if (!status.trim().equals("accept") && !status.trim().equals("refuse")) {
            throw new InvalidDataException("invalidStatus");
        } else {
            if (status.trim().equals("accept")) {
                Students student = searchedJoinRequest.getInstructorJoinRequestStudent();
                student.setStudentInstructor(searchedJoinRequest.getInstructorJoinRequestInstructor());

                studentRepository.save(student);
                searchedJoinRequest.setIsAccepted(true);
            } else {
                searchedJoinRequest.setIsAccepted(false);
            }
            searchedJoinRequest.setAcceptedAt(new Date());
            try {
                emailSender.sendEmailAboutInstructorJoinRequestToStudent(searchedJoinRequest.getInstructorJoinRequestStudent().getStudentUser().getEmail(), searchedJoinRequest, status);
            } catch (MessagingException e) {
            }
            return ResponseEntity.ok().body(instructorJoinRequestRepository.save(searchedJoinRequest));
        }
    }

    @PreAuthorize("(hasRole('instructor') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<List<InstructorJoinRequest>> getAllJoinRequestByInstructor(Long id, Pageable pageable) {
        Instructors searchedInstructor = instructorRepository.getInstructor(id).orElseThrow(() -> new NotFoundException("instructorNotFound"));
        Page<InstructorJoinRequest> returnList = instructorJoinRequestRepository.findByInstructorJoinRequestInstructorAndIsAcceptedAndIsDeleted(searchedInstructor, null, false, pageable);
        return ResponseEntity.ok().body(returnList.toList());
    }

    @PreAuthorize("(hasRole('instructor') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<List<DrivingLessonRequest>> getDrivingLessonRequestByInstructor(Long instructorId, Pageable pageable) {
        Instructors searchedInstructor = instructorRepository.getInstructor(instructorId).orElseThrow(() -> new NotFoundException("instructorNotFound"));
        Page<DrivingLessonRequest> returnList = drivingLessonRequestRepository.findBydLessonInstructorAndIsAcceptedAndIsDeleted(searchedInstructor, null, false, pageable);
        return ResponseEntity.ok().body(returnList.toList());
    }

    @PreAuthorize("(hasRole('instructor') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> updateInstructor(Long instructorId, InstructorUpdate updatedInstructor) {
        Instructors searchedInstructors = instructorRepository.getInstructor(instructorId).orElseThrow(() -> new NotFoundException("instructorNotFound"));
        Vehicle searchedVehicle = vehicleRepository.getVehicle(updatedInstructor.vehicleId()).orElseThrow(() -> new NotFoundException("vehicleNotFound"));
        FuelType searchedFuelType = fuelTypeRepository.getFuelType(updatedInstructor.fuelTypeId()).orElseThrow(() -> new NotFoundException("fuelTypeNotFound"));
        VehicleType searchedVehicleType = vehicleTypeRepository.getVehicleType(updatedInstructor.vehicleTypeId()).orElseThrow(() -> new NotFoundException("vehicleTypeNotFound"));

        if (updatedInstructor.licensePlate().length() != 7 && updatedInstructor.licensePlate().length() != 9) {
            throw new InvalidDataException("invalidLicensePlate");
        } if (!searchedVehicle.getLicensePlate().equals(updatedInstructor.licensePlate()) && vehicleRepository.findByLicensePlateAndIsDeleted(updatedInstructor.licensePlate(), false).isPresent()){
            throw new UniqueErrorException("registeredLicensePlate");
        } else {
            searchedInstructors.setPromoText(updatedInstructor.promoText().trim());
            searchedVehicle.setLicensePlate(updatedInstructor.licensePlate());
            searchedVehicle.setName(updatedInstructor.vehicleName());
            searchedVehicle.setFuelType(searchedFuelType);
            searchedVehicle.setVehicleType(searchedVehicleType);
            searchedInstructors.setVehicle(vehicleRepository.save(searchedVehicle));

            Instructors updated = instructorRepository.save(searchedInstructors);
            Users user = updated.getInstructorUser();
            user.setInstructor(updated);

            return ResponseEntity.ok().body(userRepository.save(user));
        }
    }

    @PreAuthorize("(hasRole('instructor') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> handleDrivingLessonRequest(Long requestId, String status) {
        DrivingLessonRequest searchedRequest = drivingLessonRequestRepository.getDrivingLessonRequest(requestId).orElseThrow(() -> new NotFoundException("requestNotFound"));
        if (searchedRequest.getDate().before(new Date())) {
            return ResponseEntity.status(415).body("invalidDate");
        }

        List<Long> drivingLessonsAtThisTime = drivingLessonRepository.getDrivingLessonBetweenHour(searchedRequest.getDate(), searchedRequest.getStartTime(), searchedRequest.getEndTime(), searchedRequest.getDLessonInstructor().getId());
        if (!drivingLessonsAtThisTime.isEmpty()) {
            return ResponseEntity.status(400).body("reservedAppointment");
        }

        if (!status.equals("accept") && !status.equals("refuse")) {
            throw new InvalidDataException("invalidStatus");
        } else {
            if (status.equals("accept")) {
                ReservedDate reservedDate = reservedDateRepository.save(reservedDateRepository.findByDate(searchedRequest.getDate()).orElse(new ReservedDate(searchedRequest.getDate())));
                ReservedHour reservedHour = reservedHourRepository.save(new ReservedHour(searchedRequest.getStartTime(), searchedRequest.getEndTime(), reservedDate));
                drivingLessonRepository.save(new DrivingLessons(reservedHour, searchedRequest.getDLessonRequestStudent(), searchedRequest.getDLessonInstructor(), statusRepository.getStatus(1L).get()));
                searchedRequest.setIsAccepted(true);
            } else {
                searchedRequest.setIsAccepted(false);
            }
            searchedRequest.setAcceptedAt(new Date());
            drivingLessonRequestRepository.save(searchedRequest);

            try {
                emailSender.sendEmailAboutDrivingLessonRequestToStudent(searchedRequest.getDLessonRequestStudent().getStudentUser().getEmail(), searchedRequest, status);
            } catch (MessagingException e) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.ok().build();
        }

    }

    @PreAuthorize("(isAuthenticated() and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> getInstructorsBySearch(Long fuelTypeId, Long schoolId, Long categoryId) {
        FuelType searchedFuelType = fuelTypeRepository.getFuelType(fuelTypeId).orElseThrow(() -> new NotFoundException("fuelTypeNotFound"));
        School searchedSchool = schoolRepository.getSchool(schoolId).orElseThrow(() -> new NotFoundException("schoolNotFound"));

        List<Long> searchedInstructorsId = instructorRepository.getInstructorBySearch(fuelTypeId, schoolId, categoryId);
        List<JsonNode> searchedInstructors = new ArrayList<>();

        for (Long id : searchedInstructorsId) {
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

    @PreAuthorize("(isAuthenticated() and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Instructors> getInstructorById(Long id) {
        Instructors searchedInstructor = instructorRepository.getInstructor(id).orElseThrow(() -> new NotFoundException("instructorNotFound"));
        return ResponseEntity.ok().body(searchedInstructor);
    }

    @PreAuthorize("(hasRole('instructor') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> getStudentsByInstructor(Long id, Pageable pageable) {
        Instructors searchedInstructor = instructorRepository.getInstructor(id).orElseThrow(() -> new NotFoundException("instructorNotFound"));

        List<ProfileCard> returnList = new ArrayList<>();
        for (Students i : instructorRepository.getAllStudents(id, pageable).toList()) {
            returnList.add(new ProfileCard(i.getId(), i.getStudentUser().getFirstName() + " " + i.getStudentUser().getLastName(), i.getStudentUser().getPfpPath(), i.getStudentUser().getId()));
        }

        return ResponseEntity.ok().body(returnList);
    }

    @PreAuthorize("(hasRole('instructor') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> kickoutStudent(Long studentId) {
        Students searchedStudent = studentRepository.getStudent(studentId).orElseThrow(() -> new NotFoundException("studentNotFound"));
        searchedStudent.setStudentInstructor(null);
        studentRepository.save(searchedStudent);
        return ResponseEntity.ok().build();
    }
}