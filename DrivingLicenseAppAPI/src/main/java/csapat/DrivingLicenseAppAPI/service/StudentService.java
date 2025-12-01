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

    public ResponseEntity<Object> getInfo(int id) {
        return null;
    }

    public ResponseEntity<Map<String, Integer>> getLessonDetails(int id) {
        Students searchedStudent = studentRepository.getStudent(id);

        if (searchedStudent == null || searchedStudent.getId() == null || searchedStudent.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else {
            Map<String, Integer> responseBody = new HashMap<>();
            responseBody.put("paidLesson", searchedStudent.getDrivingLessons().stream().filter(lesson -> lesson.isPaid()).toList().size());
            responseBody.put("drivenLesson", searchedStudent.getDrivingLessons().stream().filter(lesson -> lesson.isEnd()).toList().size());
            responseBody.put("totalLessonNumber", searchedStudent.getDrivingLessons().size());
            return ResponseEntity.ok().body(responseBody);
        }
    }

    public ResponseEntity<Object> deleteStudent(Integer id){
        Students searchedStudent = studentRepository.getStudent(id);
        if (searchedStudent == null || searchedStudent.getId() == null || searchedStudent.getIsDeleted()){
            return ResponseEntity.notFound().build();
        } else {
            studentRepository.deleteStudent(id);
            return ResponseEntity.ok().build();
        }
    }
}
