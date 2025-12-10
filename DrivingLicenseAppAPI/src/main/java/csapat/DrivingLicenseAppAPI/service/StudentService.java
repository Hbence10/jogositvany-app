package csapat.DrivingLicenseAppAPI.service;

import csapat.DrivingLicenseAppAPI.entity.Students;
import csapat.DrivingLicenseAppAPI.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;

    public ResponseEntity<Map<String, Integer>> getLessonDetails(Integer id) {
        try {
            if (id == null) {
                return ResponseEntity.status(422).build();
            }

            Students searchedStudent = studentRepository.getStudent(id).orElse(null);

            if (searchedStudent == null || searchedStudent.getId() == null || searchedStudent.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                Map<String, Integer> responseBody = new HashMap<>();
                responseBody.put("paidLesson", searchedStudent.getDrivingLessons().stream().filter(lesson -> lesson.getIsPaid()).toList().size());
                responseBody.put("drivenLesson", searchedStudent.getDrivingLessons().stream().filter(lesson -> lesson.getIsPaid()).toList().size());
                responseBody.put("totalLessonNumber", searchedStudent.getDrivingLessons().size());
                return ResponseEntity.ok().body(responseBody);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Object> deleteStudent(Integer id) {
        try {
            if (id == null) {
                return ResponseEntity.status(422).build();
            }

            Students searchedStudent = studentRepository.getStudent(id).orElse(null);
            if (searchedStudent == null || searchedStudent.getId() == null || searchedStudent.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                studentRepository.deleteStudent(id);
                return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
