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
        SchoolJoinRequest searchedSchoolJoinRequest = schoolJoinRequestRepository.getSchoolJoinRequest(joinRequestId).orElse(null);

        if (searchedSchoolJoinRequest == null || searchedSchoolJoinRequest.getId() == null || searchedSchoolJoinRequest.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else if (!status.equals("accept") || !status.equals("refuse")) {
            return null;
        } else {
            if (status.equals("accept")) {
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
    }

    public ResponseEntity<School> updateSchool(School updatedSchool) {
        if (schoolRepository.getSchool(updatedSchool.getId()) == null) {
            return ResponseEntity.notFound().build();
        } else if (!ValidatorCollection.emailValidator(updatedSchool.getEmail())) {
            return ResponseEntity.status(417).build();
        } else if (!ValidatorCollection.phoneValidator(updatedSchool.getPhone())) {
            return ResponseEntity.status(417).build();
        } else {
            return ResponseEntity.ok().body(schoolRepository.save(updatedSchool));
        }
    }

    public ResponseEntity<School> changeCoverImg(Integer id, MultipartFile bannerImg) {
        School searchedSchool = schoolRepository.getSchool(id).orElse(null);

        if (searchedSchool == null || searchedSchool.getId() == null || searchedSchool.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else {
            String filePath = "" + File.separator + bannerImg.getOriginalFilename();

            try {
                FileOutputStream fout = new FileOutputStream(filePath);
                fout.write(bannerImg.getBytes());
                fout.close();

                searchedSchool.setBannerImgPath("assets\\images\\coverImg" + File.separator + bannerImg.getOriginalFilename());
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }

            return ResponseEntity.ok().body(schoolRepository.save(searchedSchool));
        }
    }

    public ResponseEntity<School> addOpeningDetails(Integer id, OpeningDetails newOpeningDetails) {
        School searchedSchool = schoolRepository.getSchool(id).orElse(null);

        if (searchedSchool == null || searchedSchool.getId() == null || searchedSchool.getIsDeleted()){
            return ResponseEntity.notFound().build();
        } else if (newOpeningDetails.getId() != null){
            return null;
        } else if (newOpeningDetails.getOpeningTime() > newOpeningDetails.getCloseTime()){
            return ResponseEntity.status(417).build();
        } else if (!dayNames.contains(newOpeningDetails.getDay())) {
            return ResponseEntity.status(417).build();
        } else {
            List<OpeningDetails> openingDetailsList = searchedSchool.getOpeningDetails();
            openingDetailsList.add(newOpeningDetails);
            searchedSchool.setOpeningDetails(openingDetailsList);
            return ResponseEntity.ok().body(schoolRepository.save(searchedSchool));
        }
    }

    public ResponseEntity<School> updateOpeningDetails(Integer id, OpeningDetails updatedOpeningDetails) {
        School searchedSchool = schoolRepository.getSchool(id).orElse(null);

        if (searchedSchool == null || searchedSchool.getId() == null || searchedSchool.getIsDeleted()){
            return ResponseEntity.notFound().build();
        } else if (updatedOpeningDetails.getId() != null){
            return null;
        } else if (updatedOpeningDetails.getOpeningTime() > updatedOpeningDetails.getCloseTime()){
            return ResponseEntity.status(417).build();
        } else if (!dayNames.contains(updatedOpeningDetails.getDay())) {
            return ResponseEntity.status(417).build();
        } else {
            openingDetailRepository.save(updatedOpeningDetails);
            return ResponseEntity.ok().body(schoolRepository.getSchool(id).orElse(null));
        }
    }

    public ResponseEntity<List<SchoolJoinRequest>> getAllJoinRequest(Integer id) {
        School searchedSchool = schoolRepository.getSchool(id).orElse(null);
        if (searchedSchool == null || searchedSchool.getId() == null || searchedSchool.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(searchedSchool.getSchoolJoinRequestList().stream().filter(request -> !request.getIsDeleted() && request.getIsAccepted() != null).toList());
        }
    }

    public ResponseEntity<List<ExamRequest>> getAllExamRequest(Integer id) {
        School searchedSchool = schoolRepository.getSchool(id).orElse(null);
        if (searchedSchool == null || searchedSchool.getId() == null || searchedSchool.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(searchedSchool.getExamRequestList().stream().filter(request -> !request.getIsDeleted()).toList());
        }
    }

    public ResponseEntity<Object> deleteSchool(Integer id){
        School searchedSchool = schoolRepository.getSchool(id).orElse(null);
        if(searchedSchool == null || searchedSchool.getId() == null || searchedSchool.getIsDeleted()){
            return ResponseEntity.notFound().build();
        } else {
            schoolRepository.deleteSchool(id);
            return ResponseEntity.ok().build();
        }
    }

    public ResponseEntity<List<School>> getSchoolBySearch(String name, String town){
        if (name == null || town.equals("")){
            return ResponseEntity.status(422).build();
        } else  {
            List<Integer> searchedSchoolId = schoolRepository.getSchoolBySearch(name, town);
            List<School> searchedSchools = new ArrayList<>();
            for (Integer i : searchedSchoolId){
                searchedSchools.add(schoolRepository.getSchool(i).orElse(null));
            }
            return ResponseEntity.ok().body(searchedSchools);
        }
    }

    public ResponseEntity<School> getSchoolById(Integer id) {
        School searchedShcool = schoolRepository.getSchool(id).orElse(null);
        if (searchedShcool == null || searchedShcool.getId() == null || searchedShcool.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(searchedShcool);
        }
    }
}
