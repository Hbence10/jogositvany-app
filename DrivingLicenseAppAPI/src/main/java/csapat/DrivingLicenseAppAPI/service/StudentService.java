package csapat.DrivingLicenseAppAPI.service;

import csapat.DrivingLicenseAppAPI.entity.Role;
import csapat.DrivingLicenseAppAPI.entity.Students;
import csapat.DrivingLicenseAppAPI.repository.StudentRepository;
import csapat.DrivingLicenseAppAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(noRollbackFor = {DataIntegrityViolationException.class, ConstraintViolationException.class, SQLIntegrityConstraintViolationException.class, SQLException.class})
public class StudentService {

    private final StudentRepository studentRepository;

    public ResponseEntity<Map<String, Integer>> getLessonDetails(Integer id) {
        try {
            if (id == null) {
                return ResponseEntity.status(422).build();
            }

            Students searchedStudent = studentRepository.getStudent(id).orElse(null);

            if (searchedStudent == null || searchedStudent.getIsDeleted()) {
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
            if (searchedStudent == null || searchedStudent.getIsDeleted()) {
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

    public ResponseEntity<Object> getStudentById(Integer id) {
        try {
            if (id == null) {
                return ResponseEntity.status(422).build();
            }
            Students searchedStudent = studentRepository.getStudent(id).orElse(null);
            if (searchedStudent == null || searchedStudent.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok().body(searchedStudent);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}

/*
 * HTTP STATUS KODOK:
 *   - 200: Sikeres muvelet
 *   - 404: Not Found
 *   - 409: Mar foglalt nev
 *   - 415: Unsupported Media Type --> Ha az adott adat invalid
 *   - 422: Hianyzo parameter/response body
 *   - 500: Internal Server Error
 * */