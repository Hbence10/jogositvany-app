package csapat.DrivingLicenseAppAPI.service;

import csapat.DrivingLicenseAppAPI.entity.Students;
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

    public ResponseEntity<Object> getInstructorDetails(Integer studentId) {
        Students searchedStudent = studentRepository.findById(studentId).get();
        if (searchedStudent == null) {
            return ResponseEntity.notFound().build();
        } else {
            return null;
        }
    }

    public ResponseEntity<Object> getHoursDetails(Integer studentId) {
        Students searchedStudent = studentRepository.findById(studentId).get();

        if (searchedStudent == null) {
            return ResponseEntity.notFound().build();
        } else {
            return null;
        }
    }
}
