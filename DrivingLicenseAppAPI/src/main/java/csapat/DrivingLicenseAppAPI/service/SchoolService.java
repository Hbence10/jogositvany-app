package csapat.DrivingLicenseAppAPI.service;

import csapat.DrivingLicenseAppAPI.entity.*;
import csapat.DrivingLicenseAppAPI.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
        SchoolJoinRequest searchedSchoolJoinRequest = schoolJoinRequestRepository.findById(joinRequestId).get();

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


}
