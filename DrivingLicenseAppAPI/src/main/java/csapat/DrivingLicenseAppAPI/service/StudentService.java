package csapat.DrivingLicenseAppAPI.service;

import csapat.DrivingLicenseAppAPI.dto.DrivingLessonCard;
import csapat.DrivingLicenseAppAPI.entity.DrivingLessons;
import csapat.DrivingLicenseAppAPI.entity.Students;
import csapat.DrivingLicenseAppAPI.exception.NotFoundException;
import csapat.DrivingLicenseAppAPI.repository.DrivingLessonRepository;
import csapat.DrivingLicenseAppAPI.repository.StudentRepository;
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
    private final DrivingLessonRepository drivingLessonRepository;

    public ResponseEntity<Map<String, Integer>> getLessonDetails(Long id) {
        Students searchedStudent = studentRepository.getStudent(id).orElseThrow(() -> new NotFoundException("studentNotFound"));
        Map<String, Integer> responseBody = new HashMap<>();
        List<DrivingLessons> drivingLessons = drivingLessonRepository.getDrivingLessonByStudentId(id);
        if (drivingLessons == null) {
            drivingLessons = new ArrayList<DrivingLessons>();
        }

        responseBody.put("paidLesson", drivingLessons.stream().filter(DrivingLessons::getIsPaid).toList().size());
        responseBody.put("drivenLesson", drivingLessons.stream().filter(DrivingLessons::getIsEnd).toList().size());
        responseBody.put("totalLessonNumber", drivingLessons.size());
        return ResponseEntity.ok().body(responseBody);
    }

    public ResponseEntity<Object> deleteStudent(Long id) {
        Students searchedStudent = studentRepository.getStudent(id).orElseThrow(() -> new NotFoundException("studentNotFound"));
        studentRepository.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Object> getStudentById(Long id) {
        Students searchedStudent = studentRepository.getStudent(id).orElseThrow(() -> new NotFoundException("studentNotFound"));
        return ResponseEntity.ok().body(searchedStudent);
    }

    public ResponseEntity<Object> getDrivingHistory(Long id) {
        Students searchedStudent = studentRepository.getStudent(id).orElseThrow(() -> new NotFoundException("studentNotFound"));
        List<DrivingLessons> drivingLessons = drivingLessonRepository.getDrivingLessonByStudentId(id);
        if (drivingLessons == null) {
            drivingLessons = new ArrayList<DrivingLessons>();
        }

        List<DrivingLessonCard> returnList = new ArrayList<>();
        for (DrivingLessons i : drivingLessons) {
            returnList.add(new DrivingLessonCard(i.getReservedHour().getReservedDate().getDate().toString(), i.getReservedHour().getStartTime().toString(), i.getReservedHour().getEndTime().toString(), i.getLocation(), i.getEndKm() - i.getStartKm()));
        }
        return ResponseEntity.ok().body(returnList);
    }
}