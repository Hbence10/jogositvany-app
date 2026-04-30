package csapat.DrivingLicenseAppAPI.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import csapat.DrivingLicenseAppAPI.config.email.EmailSender;
import csapat.DrivingLicenseAppAPI.dto.ProfileCard;
import csapat.DrivingLicenseAppAPI.dto.SchoolCategoryDto;
import csapat.DrivingLicenseAppAPI.dto.SchoolDto;
import csapat.DrivingLicenseAppAPI.entity.*;
import csapat.DrivingLicenseAppAPI.exception.InvalidDataException;
import csapat.DrivingLicenseAppAPI.exception.NotFoundException;
import csapat.DrivingLicenseAppAPI.exception.UniqueErrorException;
import csapat.DrivingLicenseAppAPI.repository.*;
import csapat.DrivingLicenseAppAPI.service.other.ValidatorCollection;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SchoolService {

    private final SchoolRepository schoolRepository;
    private final OpeningDetailRepository openingDetailRepository;
    private final SchoolJoinRequestRepository schoolJoinRequestRepository;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;
    private final UserRepository userRepository;
    private final DrivingLicenseCategoryRepository drivingLicenseCategoryRepository;
    private final SchoolCategoryRepository schoolCategoryRepository;
    private final ObjectMapper objectMapper;
    private final EmailSender emailSender;
    private final ArrayList<String> dayNames = new ArrayList<String>(Arrays.asList("Hétfő", "Kedd", "Szerda", "Csütörtök", "Péntek", "Szombat", "Vasárnap"));

    @PreAuthorize("(hasAnyRole('school_admin', 'school_owner') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> handleJoinRequest(Long joinRequestId, String status) {
        SchoolJoinRequest searchedSchoolJoinRequest = schoolJoinRequestRepository.getSchoolJoinRequest(joinRequestId).orElseThrow(() -> new NotFoundException("requestNotFound"));

        if (!status.trim().equals("accept") && !status.trim().equals("refuse")) {
            return ResponseEntity.status(415).body("invalidStatus");
        } else {
            if (status.trim().equals("accept")) {
                if (searchedSchoolJoinRequest.getSchoolJoinRequestUser().getRole().getName().equals("ROLE_user")) {
                    Students newStudent = new Students(searchedSchoolJoinRequest.getSchoolJoinRequestUser(), searchedSchoolJoinRequest.getSchoolJoinRequestSchool(), searchedSchoolJoinRequest.getJoinRequestCategory());
                    newStudent.getStudentUser().setRole(new Role(2L, "ROLE_student"));
                    newStudent.setSelectedCategory(searchedSchoolJoinRequest.getJoinRequestCategory());
                    studentRepository.save(newStudent);

                    for (SchoolJoinRequest i : searchedSchoolJoinRequest.getSchoolJoinRequestUser().getSchoolJoinRequestList()) {
                        if (i.getId() != searchedSchoolJoinRequest.getId()) {
                            i.setDeleted(true);
                            i.setDeletedAt(new Date());
                            schoolJoinRequestRepository.save(i);
                        }
                    }
                } else {
                    Instructors senderInstructor = searchedSchoolJoinRequest.getSchoolJoinRequestUser().getInstructor();
                    senderInstructor.setInstructorSchool(searchedSchoolJoinRequest.getSchoolJoinRequestSchool());
                    instructorRepository.save(senderInstructor);
                }
                searchedSchoolJoinRequest.setAccepted(true);
                Users senderUser = searchedSchoolJoinRequest.getSchoolJoinRequestUser();
                for (int i = 0; i < senderUser.getSchoolJoinRequestList().size(); i++) {
                    schoolJoinRequestRepository.deleteSchoolJoinRequest(senderUser.getSchoolJoinRequestList().get(i).getId());
                }
            } else {
                searchedSchoolJoinRequest.setAccepted(false);
            }
            searchedSchoolJoinRequest.setAcceptedAt(new Date());
            schoolJoinRequestRepository.save(searchedSchoolJoinRequest);
            try {
                emailSender.sendEmailAboutSchoolJoinRequestToUser(searchedSchoolJoinRequest, status);
            } catch (MessagingException e) {

            }
            return ResponseEntity.ok().build();
        }
    }

    @PreAuthorize("(hasAnyRole('school_admin', 'school_owner') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> updateSchool(Long schoolId, SchoolDto updatedSchool) {
        School searchedSchool = schoolRepository.getSchool(schoolId).orElseThrow(() -> new NotFoundException("schoolNotFound"));
        System.out.println(schoolRepository.findByName(updatedSchool.schoolName()).isPresent());

        if (!ValidatorCollection.emailValidator(updatedSchool.email().trim())) {
            throw new InvalidDataException("invalidEmail");
        } else if (!ValidatorCollection.phoneValidator(updatedSchool.phoneNumber().trim())) {
            throw new InvalidDataException("invalidPhone");
        } else if (!searchedSchool.getEmail().equals(updatedSchool.email()) && schoolRepository.findByEmail(updatedSchool.email()).isPresent()) {
            throw new UniqueErrorException("duplicateEmail");
        } else if (!searchedSchool.getPhone().equals(updatedSchool.phoneNumber()) &&schoolRepository.findByPhone(updatedSchool.phoneNumber()).isPresent()){
            throw new UniqueErrorException("duplicatePhone");
        } else if (!searchedSchool.getName().equals(updatedSchool.schoolName()) && schoolRepository.findByName(updatedSchool.schoolName()).isPresent()) {
            throw new UniqueErrorException("duplicateName");
        }

        searchedSchool.setName(updatedSchool.schoolName().trim());
        searchedSchool.setEmail(updatedSchool.email().trim());
        searchedSchool.setPhone(updatedSchool.phoneNumber().trim());
        searchedSchool.setCountry(updatedSchool.county().trim());
        searchedSchool.setTown(updatedSchool.town().trim());
        searchedSchool.setAddress(updatedSchool.address().trim());
        searchedSchool.setPromoText(updatedSchool.promoText().trim());
        return ResponseEntity.ok().body(schoolRepository.save(searchedSchool));
    }

    @PreAuthorize("(hasAnyRole('school_admin', 'school_owner') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> changeCoverImg(Long id, MultipartFile bannerImg) {
        School searchedSchool = schoolRepository.getSchool(id).orElseThrow(() -> new NotFoundException("schoolNotFound"));
        String filePath = "images/coverImages" + File.separator + searchedSchool.getId() + bannerImg.getOriginalFilename();

        try {
            FileOutputStream fout = new FileOutputStream(filePath);
            fout.write(bannerImg.getBytes());
            fout.close();
            searchedSchool.setBannerImgPath("https://jogositvany-app.onrender.com/coverImages/" + searchedSchool.getId() + bannerImg.getOriginalFilename());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("fileUploadingError");
        }

        return ResponseEntity.ok().body(schoolRepository.save(searchedSchool));
    }

    @PreAuthorize("(hasAnyRole('school_admin', 'school_owner') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> updateOpeningDetails(Long id, List<OpeningDetails> updatedOpeningDetails) {
        School searchedSchool = schoolRepository.getSchool(id).orElseThrow(() -> new NotFoundException("schoolNotFound"));

        for (int i = 0; i < updatedOpeningDetails.size(); i++) {
            updatedOpeningDetails.get(i).setSchoolOpeningDetail(searchedSchool);
            if (updatedOpeningDetails.get(i).getId() == null) {
                throw new InvalidDataException("invalidObject");
            } else if (!dayNames.contains(updatedOpeningDetails.get(i).getDay().trim())) {
                throw new InvalidDataException("invalidDay");
            } else {
                openingDetailRepository.save(updatedOpeningDetails.get(i));
            }
        }

        return ResponseEntity.ok().body(schoolRepository.getSchool(id).get());
    }

    @PreAuthorize("(hasAnyRole('school_admin', 'school_owner') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<List<SchoolJoinRequest>> getAllJoinRequest(Long id, Pageable pageable) {
        School searchedSchool = schoolRepository.getSchool(id).orElseThrow(() -> new NotFoundException("schoolNotFound"));
        Page<SchoolJoinRequest> returnList = schoolJoinRequestRepository.findBySchoolJoinRequestSchoolAndIsAcceptedAndIsDeleted(searchedSchool, null, false, pageable);
        return ResponseEntity.ok().body(returnList.toList());
    }

    @PreAuthorize("(hasAnyRole('administrator', 'school_owner') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> deleteSchool(Long id) {
        School searchedSchool = schoolRepository.getSchool(id).orElseThrow(() -> new NotFoundException("schoolNotFound"));
        schoolRepository.deleteSchool(id);
        return ResponseEntity.ok().build();
    }

    //    @PreAuthorize("(isAuthenticated() and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<List<JsonNode>> getSchoolBySearch(String town) {
        List<Long> searchedSchoolId = schoolRepository.getSchoolBySearch(town);
        List<JsonNode> searchedSchools = new ArrayList<JsonNode>();
        for (Long i : searchedSchoolId) {
            School searchedSchool = schoolRepository.getSchool(i).orElse(null);
            if (searchedSchool != null) {
                JsonNode schoolNode = objectMapper.createObjectNode();
                ((ObjectNode) schoolNode).put("id", searchedSchool.getId());
                ((ObjectNode) schoolNode).put("name", searchedSchool.getName());
                searchedSchools.add(schoolNode);
            }
        }
        return ResponseEntity.ok().body(searchedSchools);
    }

    @PreAuthorize("(isAuthenticated() and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<School> getSchoolById(Long id) {
        School searchedSchool = schoolRepository.getSchool(id).orElseThrow(() -> new NotFoundException("schoolNotFound"));
        return ResponseEntity.ok().body(searchedSchool);
    }

    @PreAuthorize("(hasRole('administrator') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> createSchool(SchoolDto addedSchool) {
        Users ownerUser = userRepository.getUser(addedSchool.ownerId()).orElseThrow(() -> new NotFoundException("userNotFound"));
        System.out.println(schoolRepository.findByName(addedSchool.schoolName()).isPresent());
        if (!ValidatorCollection.emailValidator(addedSchool.email().trim())) {
            throw new InvalidDataException("invalidEmail");
        } else if (!ValidatorCollection.phoneValidator(addedSchool.phoneNumber().trim())) {
            throw new InvalidDataException("invalidPhone");
        } else if (schoolRepository.findByEmail(addedSchool.email()).isPresent()) {
            throw new UniqueErrorException("duplicateEmail");
        } else if (schoolRepository.findByPhone(addedSchool.phoneNumber()).isPresent()){
            throw new UniqueErrorException("duplicatePhone");
        } else if (schoolRepository.findByName(addedSchool.schoolName()).isPresent()) {
            throw new UniqueErrorException("duplicateName");
        } else {
            School newSchool = new School(addedSchool.schoolName(), addedSchool.email(), addedSchool.phoneNumber(), addedSchool.county(), addedSchool.town(), addedSchool.address(), addedSchool.promoText(), ownerUser);
            schoolRepository.save(newSchool);
            userRepository.setRoleOfUser(ownerUser.getId(), 6L);

            emailSender.sendEmailAboutSchoolRegistration(addedSchool.email());
            return ResponseEntity.ok().build();
        }
    }

    @PreAuthorize("(hasAnyRole('school_admin', 'school_owner') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> getMembersOfSchool(Long schoolId, String role, Pageable pageable) {
        if (!role.equals("students") && !role.equals("instructors")) {
            throw new InvalidDataException("invalidUserType");
        }

        School searchedSchool = schoolRepository.getSchool(schoolId).orElseThrow(() -> new NotFoundException("schoolNotFound"));
        List<ProfileCard> returnList = new ArrayList<>();
        if (role.equals("students")) {
            List<Students> students = schoolRepository.getAllStudents(schoolId, pageable).toList();
            for (Students i : students) {
                returnList.add(new ProfileCard(i.getId(), i.getStudentUser().getFirstName() + " " + i.getStudentUser().getLastName(), i.getStudentUser().getPfpPath(), i.getStudentUser().getId()));
            }
        } else {
            List<Instructors> studentsList = schoolRepository.getAllInstructor(schoolId, pageable).toList();
            for (Instructors i : studentsList) {
                returnList.add(new ProfileCard(i.getId(), i.getInstructorUser().getFirstName() + " " + i.getInstructorUser().getLastName(), i.getInstructorUser().getPfpPath(), i.getInstructorUser().getId()));
            }
        }

        return ResponseEntity.ok().body(returnList);
    }

    @PreAuthorize("(hasAnyRole('school_admin', 'school_owner') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> kickoutInstructor(Long instructorId) {
        Instructors searchedInstructor = instructorRepository.getInstructor(instructorId).orElseThrow(() -> new NotFoundException("instructorNotFound"));
        searchedInstructor.setInstructorSchool(null);
        List<Students> studentsList = searchedInstructor.getStudents();
        if (searchedInstructor.getStudents() == null) {
            studentsList = new ArrayList<Students>();
        }

        for (Students i : studentsList) {
            i.setStudentInstructor(null);
            studentRepository.save(i);
        }

        searchedInstructor.setStudents(studentsList);
        instructorRepository.save(searchedInstructor);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("(hasRole('administrator') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> getAllSchool(Pageable pageable) {
        Page<School> allSchool = schoolRepository.findAll(pageable);
        List<JsonNode> returnList = new ArrayList<>();

        for (School i : allSchool) {
            JsonNode school = objectMapper.createObjectNode();
            ((ObjectNode) school).put("id", i.getId());
            ((ObjectNode) school).put("name", i.getName());
            returnList.add(school);
        }

        HttpHeaders header = new HttpHeaders();
        header.add("PageNumber", allSchool.getTotalPages() + "");

        return new ResponseEntity<>(returnList, header, HttpStatus.OK);
    }

    @PreAuthorize("(hasRole('school_owner') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> setAdmin(String email, Long schoolId) {
        Users searchedUser = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("userNotFound"));
        School searchedSchool = schoolRepository.findById(schoolId).orElseThrow(() -> new NotFoundException("schoolNotFound"));

        searchedUser.setRole(new Role(4L, "ROLE_school_admin"));
        searchedUser.setAdminSchool(searchedSchool);
        userRepository.save(searchedUser);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("(hasRole('school_owner') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> addCategory(SchoolCategoryDto newCategory) {
        School searchedSchool = schoolRepository.getSchool(newCategory.schoolId()).orElseThrow(() -> new NotFoundException("schoolNotFound"));
        DrivingLicenseCategory searchedCategory = drivingLicenseCategoryRepository.getDrivingLicenseCategory(newCategory.categoryId()).orElseThrow(() -> new NotFoundException("categoryNotFound"));

        return ResponseEntity.ok().body(schoolCategoryRepository.save(new SchoolCategory(newCategory.price(), searchedCategory, searchedSchool)));
    }

    @PreAuthorize("(hasRole('school_owner') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> deleteCategory(Long id) {
        SchoolCategory searchedCategory = schoolCategoryRepository.findById(id).orElseThrow(() -> new NotFoundException("categoryNotFound"));
        System.out.println(searchedCategory.getId());
        schoolCategoryRepository.delete(searchedCategory);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("(hasRole('school_owner') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> updatePriceOfCategory(Long categoryId, Integer price) {
        SchoolCategory searchedCategory = schoolCategoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("categoryNotFound"));
        searchedCategory.setHourlyRate(price);
        return ResponseEntity.ok().body(schoolCategoryRepository.save(searchedCategory));
    }
}