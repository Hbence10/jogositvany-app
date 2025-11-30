package csapat.DrivingLicenseAppAPI.service;

import csapat.DrivingLicenseAppAPI.entity.DrivingLessons;
import csapat.DrivingLicenseAppAPI.entity.Students;
import csapat.DrivingLicenseAppAPI.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class DrivingLessonService {

    private final DrivingLessonRepository drivingLessonRepository;
    private final DrivingLessonTypeRepository drivingLessonTypeRepository;
    private final ReservedDateRepository reservedDateRepository;
    private final ReservedHourRepository reservedHourRepository;
    private final StudentRepository studentRepository;

    public ResponseEntity<List<DrivingLessons>> getLessonInformationByStudent(Integer studentId){
        Students searchedStudent = studentRepository.getStudent(studentId);

        if(searchedStudent == null || searchedStudent.getId() == null || searchedStudent.getIsDeleted()){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(searchedStudent.getDrivingLessons());
        }
    }
}
