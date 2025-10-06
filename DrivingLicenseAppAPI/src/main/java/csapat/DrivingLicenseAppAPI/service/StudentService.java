package csapat.DrivingLicenseAppAPI.service;

import csapat.DrivingLicenseAppAPI.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;

    public ResponseEntity<Object> getInstructorDetails(){
        return null;
    }

    public ResponseEntity<Object> getHoursDetails(){
        return null;
    }
}
