package csapat.DrivingLicenseAppAPI.service;


import csapat.DrivingLicenseAppAPI.config.email.EmailSender;
import csapat.DrivingLicenseAppAPI.entity.Education;
import csapat.DrivingLicenseAppAPI.entity.User;
import csapat.DrivingLicenseAppAPI.service.other.ValidatorCollection;
import csapat.DrivingLicenseAppAPI.repository.EducationRepository;
import csapat.DrivingLicenseAppAPI.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;
    private final EducationRepository educationRepository;

    public ResponseEntity<Object> login(String email, String password) {
        User loggedUser = userRepository.findAll().stream().filter(user -> user.getEmail().equals(email)).toList().get(0);

        boolean successFullLogin = passwordEncoder.matches(password, loggedUser.getPassword());

        if (!successFullLogin || loggedUser.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        }
        loggedUser.setLastLogin(LocalDateTime.now());
        userRepository.save(loggedUser);

        return ResponseEntity.ok(loggedUser);
    }

    public ResponseEntity<Object> register(User newUser) {
        System.out.println(newUser);

        Education searchedEducation = educationRepository.findById(newUser.getUserEducation().getId()).orElse(null);
        if (searchedEducation == null || searchedEducation.getId() == null) {
            return ResponseEntity.notFound().build();
        } else if (!ValidatorCollection.emailChecker(newUser.getEmail())) {
            System.out.println("invalidEmail");
            return ResponseEntity.status(417).body("invalidEmail");
        } else if (!ValidatorCollection.phoneValidator(newUser.getPhone())) {
            System.out.println("invalidPhone");
            return ResponseEntity.status(417).body("invalidPhone");
        } else if (!ValidatorCollection.passwordChecker(newUser.getPassword())) {
            System.out.println("invalidPassword");
            return ResponseEntity.status(417).body("invalidPassword");
        } else {
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
//            emailSender.sendEmailAboutRegistration(newUser.getEmail()); //Itt majd le kell kezelni, ha a user egy nem letezo emailt ad meg
            userRepository.save(newUser);
        }

        return ResponseEntity.ok().body("success");
    }

    //password reset
    public ResponseEntity<String> getVerificationCode(String email) {
        List<String> emailList = userRepository.getAllEmail();

        if (!ValidatorCollection.emailChecker(email.trim())) {
            return ResponseEntity.status(417).body("InvalidEmail");
        } else if (!emailList.contains(email.trim())) {
            return ResponseEntity.notFound().build();
        } else {
            User searchedUser = userRepository.getUserByEmail(email);

            if (searchedUser == null || searchedUser.getId() == null || searchedUser.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            }

            String vCode = generateVerificationCode();
            searchedUser.setVCode(passwordEncoder.encode(vCode));
            userRepository.save(searchedUser);
            emailSender.sendVerificationCodeEmail(email, vCode);
            return ResponseEntity.ok().body("success");
        }
    }

    public ResponseEntity<Object> checkVCode(String userVCode, String email) {
        if (userVCode.length() != 10) {
            return ResponseEntity.status(417).body("InvalidVerificationCode");
        } else if (!ValidatorCollection.emailChecker(email)) {
            return ResponseEntity.status(417).body("InvalidEmail");
        } else {
            User searchedUser = userRepository.getUserByEmail(email);
            if (searchedUser == null || searchedUser.getId() == null || searchedUser.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok().body(passwordEncoder.matches(userVCode, searchedUser.getVCode()) ? "success" : "failed");
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
        } else if (user == null || user.getId() == null || user.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return ResponseEntity.ok("success");
        }
    }

    // update:
    public ResponseEntity<Object> updateUser(User updatedUser) {
        if (updatedUser.getId() == null) {
            return ResponseEntity.notFound().build();
        } else {
            List<Education> allEducation = educationRepository.findAll();

            if (!ValidatorCollection.phoneValidator(updatedUser.getPhone())) {
                return ResponseEntity.status(417).body("InvalidPhone");
            } else if (!ValidatorCollection.emailChecker(updatedUser.getEmail())) {
                return ResponseEntity.status(417).body("InvalidEmail");
            } else if (!allEducation.contains(updatedUser.getUserEducation())) {
                return ResponseEntity.status(417).body("InvalidEducation");
            } else {
                User result = userRepository.save(updatedUser);
                return ResponseEntity.ok(result);
            }
        }
    }

    public ResponseEntity<User> updatePfp(Integer id, MultipartFile file) {
        return null;
    }

    // delete:
    public ResponseEntity<String> deleteUser(Integer id)    {
        User searchedUser = userRepository.findById(id).get();
        if (searchedUser == null || searchedUser.getId() == null || searchedUser.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        }

        searchedUser.setIsDeleted(true);
        searchedUser.setDeletedAt(new Date());
        userRepository.save(searchedUser);

        return ResponseEntity.ok().body("success");
    }

    //egyeb endpointok:
    public ResponseEntity<User> getUserById(Integer id){
        User searchedUser = userRepository.findById(id).get();

        if(searchedUser == null || searchedUser.getId() == null || searchedUser.getIsDeleted()){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(searchedUser);
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
