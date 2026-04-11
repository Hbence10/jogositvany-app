package csapat.DrivingLicenseAppAPI.service;

import csapat.DrivingLicenseAppAPI.config.email.EmailSender;
import csapat.DrivingLicenseAppAPI.dto.DrivingLessonRequestDto;
import csapat.DrivingLicenseAppAPI.entity.*;
import csapat.DrivingLicenseAppAPI.exception.InvalidDataException;
import csapat.DrivingLicenseAppAPI.exception.NotFoundException;
import csapat.DrivingLicenseAppAPI.exception.UniqueErrorException;
import csapat.DrivingLicenseAppAPI.repository.*;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class RequestService {

    private final DrivingLessonRequestRepository drivingLessonRequestRepository;
    private final DrivingLicenseCategoryRepository drivingLicenseCategoryRepository;
    private final InstructorJoinRequestRepository instructorJoinRequestRepository;
    private final SchoolJoinRequestRepository schoolJoinRequestRepository;
    private final SchoolRepository schoolRepository;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;
    private final UserRepository userRepository;
    private final EmailSender emailSender;

    @PreAuthorize("(hasAnyRole('instructor', 'user') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> sendSchoolJoinRequest(Long schoolId, Long userId, Long categoryId) {
        School searchedSchool = schoolRepository.getSchool(schoolId).orElseThrow(() -> new NotFoundException("schoolNotFound"));
        Users searchedUser = userRepository.getUser(userId).orElseThrow(() -> new NotFoundException("userNotFound"));
        DrivingLicenseCategory searchedCategory = drivingLicenseCategoryRepository.getDrivingLicenseCategory(categoryId).orElseThrow(() -> new NotFoundException("categoryNotFound"));

        System.out.println(searchedSchool.getLicenseCategoryList().get(0).getId());

        List<DrivingLicenseCategory> categories = searchedCategory.getLicenseCategory().stream().map(SchoolCategory::getLicenseCategory).toList();
        if (categories.stream().filter(c -> c.getId() == categoryId).toList().isEmpty()) {
            throw new InvalidDataException("invalidCategory");
        }

        SchoolJoinRequest newSchoolJoinRequest;
        if (searchedUser.getRole().getName().equals("ROLE_user")) {
            newSchoolJoinRequest = new SchoolJoinRequest(searchedUser, searchedSchool, searchedCategory);
        } else {
            newSchoolJoinRequest = new SchoolJoinRequest(searchedUser, searchedSchool);
        }

        try {
            emailSender.sendEmailAboutSchoolJoinRequestToSchool(searchedSchool.getEmail(), newSchoolJoinRequest);
        } catch (MessagingException e) {
        }
        schoolJoinRequestRepository.save(newSchoolJoinRequest);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("(hasRole('student') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> sendInstructorJoinRequest(Long studentId, Long instructorId) {
        Students searchedStudent = studentRepository.getStudent(studentId).orElseThrow(() -> new NotFoundException("studentNotFound"));
        Instructors searchedInstructor = instructorRepository.getInstructor(instructorId).orElseThrow(() -> new NotFoundException("instructorNotFound"));

        if (!Objects.equals(searchedStudent.getStudentSchool().getId(), searchedInstructor.getInstructorSchool().getId())) {
            throw new InvalidDataException("invalidInstructor");
        } else {
            InstructorJoinRequest instructorJoinRequest = new InstructorJoinRequest(searchedStudent, searchedInstructor);
            instructorJoinRequestRepository.save(instructorJoinRequest);
            try {
                emailSender.sendEmailAboutInstructorJoinRequestToInstructor(searchedInstructor.getInstructorUser().getEmail(), searchedInstructor.getInstructorUser().getFirstName() + " " + searchedInstructor.getInstructorUser().getLastName(), searchedStudent.getStudentUser().getFirstName() + " " + searchedStudent.getStudentUser().getLastName());
            } catch (MessagingException e) {
            }
            return ResponseEntity.ok().build();
        }

    }

    @PreAuthorize("(hasRole('student') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> sendDrivingLessonRequest(DrivingLessonRequestDto newRequestDto) {
        try {
            DateFormat dateWithTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.GERMAN);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMAN);
            Students searchedStudent = studentRepository.getStudent(newRequestDto.studentId()).orElseThrow(() -> new NotFoundException("studentNotFound"));
            Instructors searchedInstructor = instructorRepository.getInstructor(newRequestDto.instructorId()).orElseThrow(() -> new NotFoundException("instructorNotFound"));

            Date startTime = dateWithTimeFormat.parse(newRequestDto.startTime());
            Date endTime = dateWithTimeFormat.parse(newRequestDto.endTime());

            if (searchedStudent.getStudentSchool().getId() != searchedInstructor.getInstructorSchool().getId() || searchedStudent.getStudentInstructor().getId() != searchedInstructor.getId()) {
                throw new InvalidDataException("invalidInstructor");
            } else if (dateFormat.parse(newRequestDto.date()).before(new Date()) || endTime.before(startTime)) {
                throw new InvalidDataException("invalidDate");
            } else {
                DrivingLessonRequest newRequest = new DrivingLessonRequest(newRequestDto.msg(), dateFormat.parse(newRequestDto.date()), dateWithTimeFormat.parse(newRequestDto.startTime()), dateWithTimeFormat.parse(newRequestDto.endTime()), searchedStudent, searchedInstructor);
                newRequest = drivingLessonRequestRepository.save(newRequest);
                try {
                    emailSender.sendEmailAboutDrivingLessonRequestToInstructor(searchedInstructor.getInstructorUser().getEmail(), newRequest);
                } catch (MessagingException e) {
                }
                return ResponseEntity.ok().build();
            }
        } catch (ParseException e) {
            throw new InvalidDataException("invalidDateFormat");
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