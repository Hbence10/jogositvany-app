package csapat.DrivingLicenseAppAPI.service;

import csapat.DrivingLicenseAppAPI.entity.School;
import csapat.DrivingLicenseAppAPI.entity.Students;
import csapat.DrivingLicenseAppAPI.entity.User;
import csapat.DrivingLicenseAppAPI.repository.OpeningDetailRepository;
import csapat.DrivingLicenseAppAPI.repository.SchoolRepository;
import csapat.DrivingLicenseAppAPI.repository.UserRepository;
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
//    private final SchoolRe

    public ResponseEntity<Object> getInfo(int id) {
        School searchedSchool = schoolRepository.findById(id).get();

        if (searchedSchool.getId() == null || searchedSchool.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(searchedSchool);
        }
    }

    //Iskola tagjainak a kezelese
    //Jelentkezesek
    public ResponseEntity<Students> addStudent(Long userId, Integer schoolId) {



        return null;
    }

    public ResponseEntity<Object> addInstructor() {
        return null;
    }

    public ResponseEntity<Object> addSchoolAdmin() {
        return null;
    }

    //Tagok torlese
    public ResponseEntity<Object> deleteStudent(Integer studentId) {
        return null;
    }

    public ResponseEntity<Object> deleteInstructor(Integer instructorId) {
        return null;
    }

    public ResponseEntity<Object> deleteAdmin(Integer adminId) {
        return null;
    }
}
