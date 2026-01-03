package csapat.DrivingLicenseAppAPI.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.opencsv.CSVReader;
import csapat.DrivingLicenseAppAPI.entity.*;
import csapat.DrivingLicenseAppAPI.repository.*;
import csapat.DrivingLicenseAppAPI.service.other.ValidatorCollection;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import java.io.File;
import java.io.FileReader;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Transactional(noRollbackFor = {DataIntegrityViolationException.class, ConstraintViolationException.class, SQLIntegrityConstraintViolationException.class, SQLException.class})
@Service
@RequiredArgsConstructor
public class SchoolService {

    private final SchoolRepository schoolRepository;
    private final OpeningDetailRepository openingDetailRepository;
    private final SchoolJoinRequestRepository schoolJoinRequestRepository;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final ArrayList<String> dayNames = new ArrayList<String>(Arrays.asList("Hétfő", "Kedd", "Szerda", "Csütörtök", "Péntek", "Szombat", "Vasárnap"));

    public ResponseEntity<Object> handleJoinRequest(Integer joinRequestId, String status) {
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
                    if (searchedSchoolJoinRequest.getRequestedRole().equals("student")) {
                        Students newStudent = new Students(searchedSchoolJoinRequest.getSchoolJoinRequestUser(), searchedSchoolJoinRequest.getSchoolJoinRequestSchool());
                        newStudent.getStudentUser().setRole(new Role(2, "ROLE_student"));
                        studentRepository.save(newStudent);
                    } else {
                        Instructors newInstructor = new Instructors(searchedSchoolJoinRequest.getSchoolJoinRequestSchool(), searchedSchoolJoinRequest.getSchoolJoinRequestUser());
                        newInstructor.getInstructorUser().setRole(new Role(3, "ROLE_instructor"));
                        instructorRepository.save(newInstructor);
                    }
                    searchedSchoolJoinRequest.setIsAccepted(true);
                } else {
                    searchedSchoolJoinRequest.setIsAccepted(false);
                }
                searchedSchoolJoinRequest.setAcceptedAt(new Date());
                schoolJoinRequestRepository.save(searchedSchoolJoinRequest);
                return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Object> updateSchool(Integer schoolId, String name, String email, String phone, String country, String town, String address, String promoText) {
        try {
            if (schoolId == null || name == null || email == null || phone == null || country == null || town == null || address == null || promoText == null) {
                return ResponseEntity.status(422).build();
            }

            School searchedSchool = schoolRepository.getSchool(schoolId).orElse(null);

            if (searchedSchool == null || searchedSchool.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else if (!ValidatorCollection.emailValidator(email.trim())) {
                System.out.println("invalidEmail");
                return ResponseEntity.status(415).body("invalidEmail");
            } else if (!ValidatorCollection.phoneValidator(phone.trim())) {
                System.out.println("invalidPhone");
                return ResponseEntity.status(415).body("invalidPhone");
            }
            searchedSchool.setName(name.trim());
            searchedSchool.setEmail(email.trim());
            searchedSchool.setPhone(phone.trim());
            searchedSchool.setCountry(country.trim());
            searchedSchool.setTown(town.trim());
            searchedSchool.setAddress(address.trim());
            searchedSchool.setPromoText(promoText.trim());
            return ResponseEntity.ok().body(schoolRepository.save(searchedSchool));
        } catch (DataIntegrityViolationException e) {
            String errorMsg = e.getMessage().contains("Duplicate entry") && e.getMessage().contains("for key 'email'") ? "emailDuplicate" : "phoneDuplicate";
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity.status(409).body(errorMsg);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Object> changeCoverImg(Integer id, MultipartFile bannerImg) {
        try {
            if (id == null || bannerImg == null) {
                return ResponseEntity.status(422).build();
            }

            School searchedSchool = schoolRepository.getSchool(id).orElse(null);

            if (searchedSchool == null || searchedSchool.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                String filePath = File.separator + bannerImg.getOriginalFilename();

                try {
//                    FileOutputStream fout = new FileOutputStream(filePath);
//                    fout.write(bannerImg.getBytes());
//                    fout.close();

                    searchedSchool.setBannerImgPath("assets\\images\\coverImg" + File.separator + bannerImg.getOriginalFilename());
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

    public ResponseEntity<Object> updateOpeningDetails(Integer id, List<OpeningDetails> updatedOpeningDetails) {
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

    public ResponseEntity<List<SchoolJoinRequest>> getAllJoinRequest(Integer id) {
        try {
            if (id == null) {
                return ResponseEntity.status(422).build();
            }

            School searchedSchool = schoolRepository.getSchool(id).orElse(null);
            if (searchedSchool == null || searchedSchool.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok().body(searchedSchool.getSchoolJoinRequestList().stream().filter(request -> !request.getIsDeleted() && request.getIsAccepted() == null).toList());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<List<ExamRequest>> getAllExamRequest(Integer id) {
        try {
            if (id == null) {
                return ResponseEntity.status(422).build();
            }

            School searchedSchool = schoolRepository.getSchool(id).orElse(null);
            System.out.println(searchedSchool.getName());
            if (searchedSchool == null || searchedSchool.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok().body(searchedSchool.getExamRequestList().stream().filter(request -> !request.getIsDeleted()).toList());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Object> deleteSchool(Integer id) {
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

    public ResponseEntity<List<JsonNode>> getSchoolBySearch(String town) {
        try {
            List<Integer> searchedSchoolId = schoolRepository.getSchoolBySearch(town);
            List<JsonNode> searchedSchools = new ArrayList<JsonNode>();
            for (Integer i : searchedSchoolId) {
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

    public ResponseEntity<School> getSchoolById(Integer id) {
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

    public ResponseEntity<Object> createSchool(School addedSchool) {
        try {
            if (addedSchool == null) {
                return ResponseEntity.status(422).build();
            }
            List<String> townName = new ArrayList<String>();
            try {
                FileReader fileReader = new FileReader(new File("src/main/java/csapat/DrivingLicenseAppAPI/service/other/townList.csv"));
                CSVReader reader = new CSVReader(fileReader);

                List<String[]> allRecords = reader.readAll();
                for (int i = 1; i < allRecords.size(); i++) {
                    townName.add(allRecords.get(i)[0]);
                }
            } catch (Exception e) {
                return ResponseEntity.internalServerError().body("fileHandlingError");
            }

            Users ownerUser = userRepository.getUser(addedSchool.getOwner().getId()).orElse(null);
            if (ownerUser == null || ownerUser.getId() != addedSchool.getOwner().getId()) {
                return ResponseEntity.notFound().build();
            } else if (!ValidatorCollection.emailValidator(addedSchool.getEmail().trim())) {
                return ResponseEntity.status(415).body("invalidEmail");
            } else if (!ValidatorCollection.phoneValidator(addedSchool.getPhone().trim())) {
                return ResponseEntity.status(415).body("invalidPhone");
            } else if (!townName.contains(addedSchool.getTown().trim())) {
                return ResponseEntity.status(415).body("invalidTown");
            } else {
                schoolRepository.save(addedSchool);
                return ResponseEntity.ok().build();
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