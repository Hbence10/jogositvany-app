package csapat.DrivingLicenseAppAPI.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import csapat.DrivingLicenseAppAPI.config.email.EmailSender;
import csapat.DrivingLicenseAppAPI.dto.ProfileCard;
import csapat.DrivingLicenseAppAPI.dto.SchoolDto;
import csapat.DrivingLicenseAppAPI.entity.*;
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
    private final ObjectMapper objectMapper;
    private final EmailSender emailSender;
    private final ArrayList<String> dayNames = new ArrayList<String>(Arrays.asList("Hétfő", "Kedd", "Szerda", "Csütörtök", "Péntek", "Szombat", "Vasárnap"));

    @PreAuthorize("(hasAnyRole('school_admin', 'school_owner') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> handleJoinRequest(Long joinRequestId, String status) {
        try {
            if (joinRequestId == null || status == null) {
                return ResponseEntity.status(422).build();
            }

            SchoolJoinRequest searchedSchoolJoinRequest = schoolJoinRequestRepository.getSchoolJoinRequest(joinRequestId).orElse(null);

            if (searchedSchoolJoinRequest == null || searchedSchoolJoinRequest.getIsDeleted() || searchedSchoolJoinRequest.getIsAccepted() != null) {
                return ResponseEntity.notFound().build();
            } else if (!status.trim().equals("accept") && !status.trim().equals("refuse")) {
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
                                i.setIsDeleted(true);
                                i.setDeletedAt(new Date());
                                schoolJoinRequestRepository.save(i);
                            }
                        }
                    } else {
                        Instructors senderInstructor = searchedSchoolJoinRequest.getSchoolJoinRequestUser().getInstructor();
                        senderInstructor.setInstructorSchool(searchedSchoolJoinRequest.getSchoolJoinRequestSchool());
                        instructorRepository.save(senderInstructor);
                    }
                    searchedSchoolJoinRequest.setIsAccepted(true);
                    Users senderUser = searchedSchoolJoinRequest.getSchoolJoinRequestUser();
                    for (int i = 0; i < senderUser.getSchoolJoinRequestList().size(); i++) {
                        schoolJoinRequestRepository.deleteSchoolJoinRequest(senderUser.getSchoolJoinRequestList().get(i).getId());
                    }
                } else {
                    searchedSchoolJoinRequest.setIsAccepted(false);
                }
                searchedSchoolJoinRequest.setAcceptedAt(new Date());
                schoolJoinRequestRepository.save(searchedSchoolJoinRequest);
                try {
                    emailSender.sendEmailAboutSchoolJoinRequestToUser(searchedSchoolJoinRequest, status);
                } catch (MessagingException e) {

                }
                return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("(hasAnyRole('school_admin', 'school_owner') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> updateSchool(Long schoolId, SchoolDto updatedSchool) {
        if (schoolId == null || updatedSchool == null) {
            return ResponseEntity.status(422).build();
        }

        School searchedSchool = schoolRepository.getSchool(schoolId).orElse(null);

        if (searchedSchool == null || searchedSchool.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else if (!ValidatorCollection.emailValidator(updatedSchool.email().trim())) {
            System.out.println("invalidEmail");
            return ResponseEntity.status(415).body("invalidEmail");
        } else if (!ValidatorCollection.phoneValidator(updatedSchool.phoneNumber().trim())) {
            System.out.println("invalidPhone");
            return ResponseEntity.status(415).body("invalidPhone");
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
        try {
            if (id == null || bannerImg == null) {
                return ResponseEntity.status(422).build();
            }

            School searchedSchool = schoolRepository.getSchool(id).orElse(null);

            if (searchedSchool == null || searchedSchool.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                String filePath = "images/coverImages" + File.separator + searchedSchool.getId() + bannerImg.getOriginalFilename();

                try {
                    FileOutputStream fout = new FileOutputStream(filePath);
                    fout.write(bannerImg.getBytes());
                    fout.close();

                    searchedSchool.setBannerImgPath("http://localhost:8080/coverImages/" + searchedSchool.getId() + bannerImg.getOriginalFilename());
                } catch (Exception e) {
                    return ResponseEntity.internalServerError().body("fileUploadingError");
                }

                return ResponseEntity.ok().body(schoolRepository.save(searchedSchool));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("serverError");
        }
    }

    @PreAuthorize("(hasAnyRole('school_admin', 'school_owner') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> updateOpeningDetails(Long id, List<OpeningDetails> updatedOpeningDetails) {
        try {
            if (id == null || updatedOpeningDetails == null) {
                return ResponseEntity.status(422).build();
            }

            School searchedSchool = schoolRepository.getSchool(id).orElse(null);

            if (searchedSchool == null || searchedSchool.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            }

            for (int i = 0; i < updatedOpeningDetails.size(); i++) {
                updatedOpeningDetails.get(i).setSchoolOpeningDetail(searchedSchool);
                if (updatedOpeningDetails.get(i).getId() == null) {
                    return ResponseEntity.status(415).body("invalidObject");
                }
//                else if (ValidatorCollection.startEndValidator(updatedOpeningDetails.get(i).getOpeningTime().getHours(), updatedOpeningDetails.get(i).getOpeningTime().getMinutes(), updatedOpeningDetails.get(i).getCloseTime().getHours(), updatedOpeningDetails.get(i).getCloseTime().getMinutes())) {
//                    System.out.println("Invalid Opening Time Range");
//
//                    return ResponseEntity.status(415).body("invalidOpeningTimeRange");
//                }
                else if (!dayNames.contains(updatedOpeningDetails.get(i).getDay().trim())) {
                    return ResponseEntity.status(415).body("invalidDay");
                } else {
                    openingDetailRepository.save(updatedOpeningDetails.get(i));
                }
            }

            return ResponseEntity.ok().body(schoolRepository.getSchool(id).get());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("(hasAnyRole('school_admin', 'school_owner') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<List<SchoolJoinRequest>> getAllJoinRequest(Long id, Pageable pageable) {
        try {
            if (id == null) {
                return ResponseEntity.status(422).build();
            }

            School searchedSchool = schoolRepository.getSchool(id).orElse(null);
            if (searchedSchool == null || searchedSchool.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                //
                Page<SchoolJoinRequest> returnList = schoolJoinRequestRepository.findBySchoolJoinRequestSchoolAndIsAcceptedAndIsDeleted(searchedSchool, null, false, pageable);
                return ResponseEntity.ok().body(returnList.toList());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("(hasAnyRole('administrator', 'school_owner') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> deleteSchool(Long id) {
        try {
            if (id == null) {
                return ResponseEntity.status(422).build();
            }

            School searchedSchool = schoolRepository.getSchool(id).orElse(null);
            if (searchedSchool == null || searchedSchool.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                schoolRepository.deleteSchool(id);
                return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

//    @PreAuthorize("(isAuthenticated() and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<List<JsonNode>> getSchoolBySearch(String town) {
        try {
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

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("(isAuthenticated() and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<School> getSchoolById(Long id) {
        try {
            if (id == null) {
                return ResponseEntity.status(422).build();
            }

            School searchedSchool = schoolRepository.getSchool(id).orElse(null);
            if (searchedSchool == null || searchedSchool.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok().body(searchedSchool);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("(hasRole('administrator') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> createSchool(SchoolDto addedSchool) {
        if (addedSchool == null) {
            return ResponseEntity.status(422).build();
        }

        Users ownerUser = userRepository.getUser(addedSchool.ownerId()).orElse(null);
        if (ownerUser == null || ownerUser.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else if (!ValidatorCollection.emailValidator(addedSchool.email().trim())) {
            return ResponseEntity.status(415).body("invalidEmail");
        } else if (!ValidatorCollection.phoneValidator(addedSchool.phoneNumber().trim())) {
            return ResponseEntity.status(415).body("invalidPhone");
        } else {
            School newSchool = new School(addedSchool.schoolName(), addedSchool.email(), addedSchool.phoneNumber(), addedSchool.county(), addedSchool.town(), addedSchool.address(), addedSchool.promoText(), ownerUser);
            schoolRepository.save(newSchool);
            emailSender.sendEmailAboutSchoolRegistration(addedSchool.email());
            return ResponseEntity.ok().build();
        }
    }

    @PreAuthorize("(hasAnyRole('school_admin', 'school_owner') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> getMembersOfSchool(Long schoolId, String role, Pageable pageable) {
        try {
            if (schoolId == null || role == null) {
                return ResponseEntity.status(422).build();
            }

            if (!role.equals("students") && !role.equals("instructors")) {
                return ResponseEntity.status(415).build();
            }

            School searchedSchool = schoolRepository.getSchool(schoolId).orElse(null);
            if (searchedSchool == null || searchedSchool.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            }

            List<ProfileCard> returnList = new ArrayList<>();
            if (role.equals("students")) {
                List<Students> students = schoolRepository.getAllStudents(schoolId, pageable).toList();
                for (Students i : students) {
                    returnList.add(new ProfileCard(i.getId(), i.getStudentUser().getFirstName() + " " + i.getStudentUser().getLastName(), i.getStudentUser().getPfpPath(), i.getStudentUser().getId()));
                }

            } else if (role.equals("instructors")) {
                List<Instructors> studentsList = schoolRepository.getAllInstructor(schoolId, pageable).toList();
                for (Instructors i : studentsList) {
                    returnList.add(new ProfileCard(i.getId(), i.getInstructorUser().getFirstName() + " " + i.getInstructorUser().getLastName(), i.getInstructorUser().getPfpPath(), i.getInstructorUser().getId()));
                }
            }

            return ResponseEntity.ok().body(returnList);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("(hasAnyRole('school_admin', 'school_owner') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> kickoutInstructor(Long instructorId) {
        try {
            if (instructorId == null) {
                return ResponseEntity.status(422).build();
            }
            Instructors searchedInstructor = instructorRepository.getInstructor(instructorId).orElse(null);

            if (searchedInstructor == null || searchedInstructor.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                searchedInstructor.setInstructorSchool(null);
                for (Students i : searchedInstructor.getStudents()) {
                    i.setStudentInstructor(null);
                    studentRepository.save(i);
                }
                instructorRepository.save(searchedInstructor);
                return ResponseEntity.ok().build();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("(hasRole('administrator') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> getAllSchool(Pageable pageable) {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("(hasRole('school_owner') and @environment.acceptsProfiles('prod')) or @environment.acceptsProfiles('test') or @environment.acceptsProfiles('dev')")
    public ResponseEntity<Object> setAdmin(String email, Long schoolId) {
        try {
            if (email == null || schoolId == null) {
                return ResponseEntity.status(422).build();
            }

            Users searchedUser = userRepository.findByEmail(email).orElse(null);
            School searchedSchool = schoolRepository.findById(schoolId).orElse(null);

            if (searchedSchool == null || searchedSchool.getIsDeleted()) {
                return ResponseEntity.status(404).body("schoolNotFound");
            } else if (searchedUser == null || searchedUser.getIsDeleted()) {
                return ResponseEntity.status(404).body("userNotFound");
            }

            searchedUser.setRole(new Role(4L, "ROLE_school_admin"));
            searchedUser.setAdminSchool(searchedSchool);
            userRepository.save(searchedUser);

            return ResponseEntity.ok().build();
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