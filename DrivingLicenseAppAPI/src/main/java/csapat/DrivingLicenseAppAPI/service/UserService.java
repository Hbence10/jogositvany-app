package csapat.DrivingLicenseAppAPI.service;


import csapat.DrivingLicenseAppAPI.entity.Students;
import csapat.DrivingLicenseAppAPI.entity.User;
import csapat.DrivingLicenseAppAPI.other.ValidatorCollection;
import csapat.DrivingLicenseAppAPI.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    //Endpointok
    public ResponseEntity<Object> login(String username, String password) {
        User loggedUser = userRepository.login(username, password);

        if (loggedUser == null || loggedUser.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(loggedUser);
    }

    public ResponseEntity<Object> register(User newUser) {
        if (ValidatorCollection.emailChecker(newUser.getEmail()) && ValidatorCollection.passwordChecker(newUser.getPassword())) {
            User registeredUser = userRepository.save(newUser);
            return ResponseEntity.ok(registeredUser);
        } else if (!ValidatorCollection.emailChecker(newUser.getEmail()) && !ValidatorCollection.passwordChecker(newUser.getPassword())) {
            return ResponseEntity.status(417).body("InvalidPasswordAndEmail");
        } else if (!ValidatorCollection.emailChecker(newUser.getEmail())) {
            return ResponseEntity.status(417).body("InvalidEmail");
        } else if (!ValidatorCollection.passwordChecker(newUser.getPassword())) {
            return ResponseEntity.status(417).body("InvalidPassword");
        }

        return ResponseEntity.internalServerError().build();
    }

    public ResponseEntity<Map<String, Integer>> getHourDetails(Long id){
        Students searchedStudent = userRepository.findById(id).get().getStudents();
        if (searchedStudent == null){
            return ResponseEntity.notFound().build();
        }

        Map<String, Integer> responseObject = new HashMap<String, Integer>();
        responseObject.put("drivedHourNumber", searchedStudent.getDrivingLessons().size());
        responseObject.put("paidedHourNumber", searchedStudent.getDrivingLessons().stream().filter(hour -> hour.isPaid()).toList().size());

        return ResponseEntity.ok(responseObject);
    }

    //---------------------------------
    //Egyeb

}
