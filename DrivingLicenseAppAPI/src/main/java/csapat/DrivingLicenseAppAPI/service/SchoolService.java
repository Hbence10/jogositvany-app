package csapat.DrivingLicenseAppAPI.service;

import csapat.DrivingLicenseAppAPI.entity.Instructors;
import csapat.DrivingLicenseAppAPI.entity.School;
import csapat.DrivingLicenseAppAPI.entity.SchoolJoinRequest;
import csapat.DrivingLicenseAppAPI.entity.Students;
import csapat.DrivingLicenseAppAPI.repository.*;
import csapat.DrivingLicenseAppAPI.service.other.ValidatorCollection;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class SchoolService {

    private final SchoolRepository schoolRepository;
    private final OpeningDetailRepository openingDetails;
    private final SchoolJoinRequestRepository schoolJoinRequestRepository;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;

    public ResponseEntity<Object> handleJoinRequest(Integer joinRequestId, String status) {
        SchoolJoinRequest searchedSchoolJoinRequest = schoolJoinRequestRepository.getSchoolJoinRequest(joinRequestId);

        if (searchedSchoolJoinRequest == null || searchedSchoolJoinRequest.getId() == null || searchedSchoolJoinRequest.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else if (!status.equals("accept") || !status.equals("refuse")) {
            return null;
        } else {
            if (status.equals("accept")) {
                if (searchedSchoolJoinRequest.getRequestedRole().equals("student")) {
                    Students newStudent = new Students(searchedSchoolJoinRequest.getSchoolJoinRequestUser(), searchedSchoolJoinRequest.getSchoolJoinRequestSchool());
                    studentRepository.save(newStudent);
                } else {
                    Instructors newInstructor = new Instructors(searchedSchoolJoinRequest.getSchoolJoinRequestSchool(), searchedSchoolJoinRequest.getSchoolJoinRequestUser());
                    instructorRepository.save(newInstructor);
                }
                searchedSchoolJoinRequest.setIsAccepted(true);
            } else {
                searchedSchoolJoinRequest.setIsAccepted(false);
            }
            return ResponseEntity.ok().body(schoolJoinRequestRepository.save(searchedSchoolJoinRequest));
        }
    }

    public ResponseEntity<School> updateSchool(School updatedSchool) {
        if (schoolRepository.getSchool(updatedSchool.getId()) == null) {
            return ResponseEntity.notFound().build();
        } else if (!ValidatorCollection.emailChecker(updatedSchool.getEmail())) {
            return ResponseEntity.status(417).build();
        } else if (!ValidatorCollection.phoneValidator(updatedSchool.getPhone())) {
            return ResponseEntity.status(417).build();
        } else {
            return ResponseEntity.ok().body(schoolRepository.save(updatedSchool));
        }
    }

    public ResponseEntity<School> changeCoverImg() {
        return null;
    }

    public ResponseEntity<School> addOpeningDetails() {
        return null;
    }

    public ResponseEntity<School> updateOpeningDetails() {
        return null;
    }

    public ResponseEntity<List<SchoolJoinRequest>> getAllJoinRequestBySchool(Integer id){
        School searchedSchool = schoolRepository.getSchool(id);
        if (searchedSchool == null || searchedSchool.getId() == null || searchedSchool.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(searchedSchool.getSchoolJoinRequestList().stream().filter(request -> !request.getIsDeleted() && request.getIsAccepted() != null).toList());
        }
    }
}
