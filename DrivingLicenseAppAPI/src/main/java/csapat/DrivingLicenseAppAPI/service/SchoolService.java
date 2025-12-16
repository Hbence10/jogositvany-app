package csapat.DrivingLicenseAppAPI.service;

import csapat.DrivingLicenseAppAPI.entity.*;
import csapat.DrivingLicenseAppAPI.repository.*;
import csapat.DrivingLicenseAppAPI.service.other.ValidatorCollection;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class SchoolService {

    private final SchoolRepository schoolRepository;
    private final OpeningDetailRepository openingDetailRepository;
    private final SchoolJoinRequestRepository schoolJoinRequestRepository;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;
    private final ArrayList<String> dayNames = new ArrayList<String>(Arrays.asList("Hétfő", "Kedd", "Szerda", "Csütörtök", "Péntek", "Szombat", "Vasárnap"));

    public ResponseEntity<Object> handleJoinRequest(Integer joinRequestId, String status) {
        try {
            if (joinRequestId == null || status == null) {
                return ResponseEntity.status(422).build();
            }

            SchoolJoinRequest searchedSchoolJoinRequest = schoolJoinRequestRepository.getSchoolJoinRequest(joinRequestId).orElse(null);

            if (searchedSchoolJoinRequest == null || searchedSchoolJoinRequest.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else if (!status.trim().equals("accept") && !status.trim().equals("refuse")) {
                return ResponseEntity.status(415).build();
            } else {
                if (status.trim().equals("accept")) {
                    if (searchedSchoolJoinRequest.getRequestedRole().equals("student")) {
                        Students newStudent = new Students(searchedSchoolJoinRequest.getSchoolJoinRequestUser(), searchedSchoolJoinRequest.getSchoolJoinRequestSchool());
                        studentRepository.save(newStudent);
                    } else {
                        Instructors newInstructor = new Instructors(searchedSchoolJoinRequest.getSchoolJoinRequestSchool(), searchedSchoolJoinRequest.getSchoolJoinRequestUser());
                        instructorRepository.save(newInstructor);
                    }
                    searchedSchoolJoinRequest.setIsAccepted(true);
                } else {
                    searchedSchoolJoinRequest.setIsAccepted(false);
                }
                return ResponseEntity.ok().body(schoolJoinRequestRepository.save(searchedSchoolJoinRequest));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Object> updateSchool(School updatedSchool) {
        try {
            if (updatedSchool == null) {
                return ResponseEntity.status(422).build();
            }

            if (schoolRepository.getSchool(updatedSchool.getId()).orElse(null) == null) {
                return ResponseEntity.notFound().build();
            } else if (!ValidatorCollection.emailValidator(updatedSchool.getEmail().trim())) {
                return ResponseEntity.status(415).body("invalidEmail");
            } else if (!ValidatorCollection.phoneValidator(updatedSchool.getPhone().trim())) {
                return ResponseEntity.status(415).body("invalidPhone");
            } else {
                return ResponseEntity.ok().body(schoolRepository.save(updatedSchool));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Object> changeCoverImg(Integer id, MultipartFile bannerImg) {
        try {
            if (id == null || bannerImg == null){
                return ResponseEntity.status(422).build();
            }

            School searchedSchool = schoolRepository.getSchool(id).orElse(null);

            if (searchedSchool == null || searchedSchool.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                String filePath = "" + File.separator + bannerImg.getOriginalFilename();

                try {
                    FileOutputStream fout = new FileOutputStream(filePath);
                    fout.write(bannerImg.getBytes());
                    fout.close();

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

    public ResponseEntity<Object> addOpeningDetails(Integer id, OpeningDetails newOpeningDetails) {
        try {
            if (id == null || newOpeningDetails == null) {
                return ResponseEntity.status(422).build();
            }

            School searchedSchool = schoolRepository.getSchool(id).orElse(null);

            if (searchedSchool == null || searchedSchool.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else if (newOpeningDetails.getId() != null) {
                return ResponseEntity.status(415).body("invalidObject");
            } else if (newOpeningDetails.getOpeningTime() > newOpeningDetails.getCloseTime()) {
                return ResponseEntity.status(415).body("invalidOpeningTimeRange");
            } else if (!dayNames.contains(newOpeningDetails.getDay().trim())) {
                return ResponseEntity.status(415).body("invalidDay");
            } else {
                List<OpeningDetails> openingDetailsList = searchedSchool.getOpeningDetails();
                newOpeningDetails.setDay(newOpeningDetails.getDay().trim());
                openingDetailsList.add(newOpeningDetails);
                searchedSchool.setOpeningDetails(openingDetailsList);
                return ResponseEntity.ok().body(schoolRepository.save(searchedSchool));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Object> updateOpeningDetails(Integer id, OpeningDetails updatedOpeningDetails) {
        try {
            if (id == null || updatedOpeningDetails == null) {
                return ResponseEntity.status(422).build();
            }

            School searchedSchool = schoolRepository.getSchool(id).orElse(null);

            if (searchedSchool == null || searchedSchool.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else if (updatedOpeningDetails.getId() != null) {
                return ResponseEntity.status(415).body("");
            } else if (updatedOpeningDetails.getOpeningTime() > updatedOpeningDetails.getCloseTime()) {
                return ResponseEntity.status(415).body("");
            } else if (!dayNames.contains(updatedOpeningDetails.getDay())) {
                return ResponseEntity.status(415).body("");
            } else {
                updatedOpeningDetails.setDay(updatedOpeningDetails.getDay().trim());
                openingDetailRepository.save(updatedOpeningDetails);
                return ResponseEntity.ok().body(schoolRepository.getSchool(id).orElse(null));
            }
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
                return ResponseEntity.ok().body(searchedSchool.getSchoolJoinRequestList().stream().filter(request -> !request.getIsDeleted() && request.getIsAccepted() != null).toList());
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

    public ResponseEntity<List<School>> getSchoolBySearch(String name, String town) {
        try {
            if (name == null || town.trim().equals("")) {
                return ResponseEntity.status(422).build();
            } else {
                List<Integer> searchedSchoolId = schoolRepository.getSchoolBySearch(name, town);
                List<School> searchedSchools = new ArrayList<>();
                for (Integer i : searchedSchoolId) {
                    searchedSchools.add(schoolRepository.getSchool(i).orElse(null));
                }
                return ResponseEntity.ok().body(searchedSchools);
            }
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