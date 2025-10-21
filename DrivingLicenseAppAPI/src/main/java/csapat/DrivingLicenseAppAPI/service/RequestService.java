package csapat.DrivingLicenseAppAPI.service;

import csapat.DrivingLicenseAppAPI.repository.DrivingLessonRequestRepository;
import csapat.DrivingLicenseAppAPI.repository.ExamRequestRepository;
import csapat.DrivingLicenseAppAPI.repository.InstructorJoinRequestRepository;
import csapat.DrivingLicenseAppAPI.repository.SchoolJoinRequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class RequestService {

    private final DrivingLessonRequestRepository drivingLessonRequestRepository;
    private final ExamRequestRepository examRequestRepository;
    private final InstructorJoinRequestRepository instructorJoinRequestRepository;
    private final SchoolJoinRequestRepository schoolJoinRequestRepository;



}
