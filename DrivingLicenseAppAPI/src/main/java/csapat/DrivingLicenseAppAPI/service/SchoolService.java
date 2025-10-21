package csapat.DrivingLicenseAppAPI.service;

import csapat.DrivingLicenseAppAPI.entity.School;
import csapat.DrivingLicenseAppAPI.repository.OpeningDetailRepository;
import csapat.DrivingLicenseAppAPI.repository.SchoolRepository;
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

    public ResponseEntity<Object> getInfo(int id){
        School searchedSchool = schoolRepository.findById(id).get();

        if(searchedSchool.getId() == null || searchedSchool.getIsDeleted()){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(searchedSchool);
        }
    }

    //Iskola tagjainak a kezelese
    //Jelentkezesek
    //@PreAuthorize("hasAnyRole('school_admin', 'school_owner')")
    public ResponseEntity<Object> addStudent(){
        return null;
    }

    //@PreAuthorize("hasAnyRole('school_admin', 'school_owner')")
    public ResponseEntity<Object> addInstructor(){
        return null;
    }

    //@PreAuthorize("hasAnyRole('school_admin', 'school_owner')")
    public ResponseEntity<Object> addSchoolAdmin(){
        return null;
    }

    //Tagok torlese
    //@PreAuthorize("hasAnyRole('school_admin', 'school_owner')")
    public ResponseEntity<Object> deleteStudent(Integer studentId){
        return null;
    }

    //@PreAuthorize("hasAnyRole('school_admin', 'school_owner')")
    public ResponseEntity<Object> deleteInstructor(Integer instructorId){
        return null;
    }

    //@PreAuthorize("hasAnyRole('school_admin', 'school_owner')")
    public ResponseEntity<Object> deleteAdmin(Integer adminId){
        return null;
    }
}
