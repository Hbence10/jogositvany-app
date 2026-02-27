package csapat.DrivingLicenseAppAPI.service;

import csapat.DrivingLicenseAppAPI.dto.DrivingLessonCard;
import csapat.DrivingLicenseAppAPI.entity.DrivingLessons;
import csapat.DrivingLicenseAppAPI.entity.Students;
import csapat.DrivingLicenseAppAPI.entity.Users;
import csapat.DrivingLicenseAppAPI.repository.DrivingLessonRepository;
import csapat.DrivingLicenseAppAPI.repository.RoleRepository;
import csapat.DrivingLicenseAppAPI.repository.StudentRepository;
import csapat.DrivingLicenseAppAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final DrivingLessonRepository drivingLessonRepository;

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
                responseBody.put("drivenLesson", searchedStudent.getDrivingLessons().stream().filter(lesson -> lesson.getIsEnd()).toList().size());
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

            Students searchedStudent = studentRepository.findById(id).orElse(null);
            if (searchedStudent == null || searchedStudent.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                Users user = searchedStudent.getStudentUser();
                user.setRole(roleRepository.getRole(1).get());
                user.setStudent(null);
                userRepository.save(user);
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

    public ResponseEntity<Object> getDrivingHistory(Integer id) {
        try {
            Students searchedStudent = studentRepository.getStudent(id).orElse(null);
            if (searchedStudent == null || searchedStudent.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            }

            List<DrivingLessons> drivingLessons = drivingLessonRepository.getDrivingLessonByStudentId(id);
            List<DrivingLessonCard> returnList = new ArrayList<>();
            for (DrivingLessons i : drivingLessons) {
                returnList.add(new DrivingLessonCard(i.getReservedHour().getReservedDate().getDate().toString(), i.getReservedHour().getStartTime().toString(), i.getReservedHour().getEndTime().toString(), i.getLocation(), i.getEndKm() - i.getStartKm()));
            }

            return ResponseEntity.ok().body(returnList);
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