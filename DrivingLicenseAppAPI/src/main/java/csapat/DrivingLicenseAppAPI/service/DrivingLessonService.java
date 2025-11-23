package csapat.DrivingLicenseAppAPI.service;

import csapat.DrivingLicenseAppAPI.entity.DrivingLessons;
import csapat.DrivingLicenseAppAPI.repository.DrivingLessonRepository;
import csapat.DrivingLicenseAppAPI.repository.DrivingLessonTypeRepository;
import csapat.DrivingLicenseAppAPI.repository.ReservedDateRepository;
import csapat.DrivingLicenseAppAPI.repository.ReservedHourRepository;
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

    public ResponseEntity<List<DrivingLessons>> getLessonInformationByStudent(Integer studentId){
        return null;
    }
}
