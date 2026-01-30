package csapat.DrivingLicenseAppAPI.other;

import csapat.DrivingLicenseAppAPI.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestDbInitializerService {

    private final DrivingLessonRepository drivingLessonRepository;
    private final DrivingLessonRequestRepository drivingLessonRequestRepository;
    private final DrivingLicenseCategoryRepository drivingLicenseCategoryRepository;
    private final EducationRepository educationRepository;
    private final FuelTypeRepository fuelTypeRepository;
    private final InstructorJoinRequestRepository instructorJoinRequestRepository;
    private final InstructorRepository instructorRepository;
}
