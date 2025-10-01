package csapat.DrivingLicenseAppAPI.service;


import csapat.DrivingLicenseAppAPI.config.email.EmailSender;
import csapat.DrivingLicenseAppAPI.entity.Students;
import csapat.DrivingLicenseAppAPI.entity.User;
import csapat.DrivingLicenseAppAPI.other.ValidatorCollection;
import csapat.DrivingLicenseAppAPI.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;
    private String vCode = "";


    //Endpointok
    public ResponseEntity<Object> login(String username, String password) {
        User loggedUser = userRepository.login(username);

        boolean successFullLogin = passwordEncoder.matches(password, loggedUser.getPassword());

        if (!successFullLogin || loggedUser.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(loggedUser);
    }

    public ResponseEntity<Object> register(User newUser) {
        if (ValidatorCollection.emailChecker(newUser.getEmail()) && ValidatorCollection.passwordChecker(newUser.getPassword())) {
            String hashedPassword = passwordEncoder.encode(newUser.getPassword());
            newUser.setPassword(hashedPassword);
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

    public ResponseEntity<Map<String, Integer>> getHourDetails(Long id) {
        Students searchedStudent = userRepository.findById(id).get().getStudents();
        if (searchedStudent == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, Integer> responseObject = new HashMap<String, Integer>();
        responseObject.put("drivedHourNumber", searchedStudent.getDrivingLessons().size());
        responseObject.put("paidedHourNumber", searchedStudent.getDrivingLessons().stream().filter(hour -> hour.isPaid()).toList().size());

        return ResponseEntity.ok(responseObject);
    }

    public ResponseEntity<String> getVerificationCode(String email) {
        List<String> emailList = userRepository.getAllEmail();

        if (!ValidatorCollection.emailChecker(email.trim())) {
            return ResponseEntity.status(417).body("InvalidEmail");
        } else if (!emailList.contains(email.trim())) {
            return ResponseEntity.notFound().build();
        } else {
            this.vCode = generateVerificationCode();
            emailSender.sendVerificationCodeEmail(email, vCode);
            return ResponseEntity.ok("");
        }
    }

    public ResponseEntity<String> updatePassword(String email, String newPassword) {
        User user = userRepository.getUserByEmail(email);

        if (!ValidatorCollection.emailChecker(email) && !ValidatorCollection.passwordChecker(newPassword)) {
            return ResponseEntity.status(417).body("InvalidPasswordAndEmail");
        } else if (!ValidatorCollection.emailChecker(email)) {
            return ResponseEntity.status(417).body("InvalidEmail");
        } else if (!ValidatorCollection.passwordChecker(newPassword)) {
            return ResponseEntity.status(417).body("InvalidPassword");
        } else if (user == null) {
            return ResponseEntity.notFound().build();
        } else {
            String hashedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(hashedPassword);
            userRepository.save(user);
            return ResponseEntity.ok("successfullyReset");
        }
    }

    public ResponseEntity<Boolean> checkVCode(String userVCode){
        if (userVCode == this.vCode){
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(422).body(false);
        }
    }

    //egyeb:
    public static String generateVerificationCode() {
        String code = "";
        ArrayList<String> characters = new ArrayList<String>(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"));

        for (int i = 97; i <= 122; i++) {
            characters.add(String.valueOf((char) i));
        }

        while (code.length() != 10) {
            Random random = new Random();
            code += characters.get(random.nextInt(characters.size()));
        }

        return code;
    }

}
