package csapat.DrivingLicenseAppAPI.controller;

import csapat.DrivingLicenseAppAPI.entity.User;
import csapat.DrivingLicenseAppAPI.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public ResponseEntity<Object> login(@RequestParam("email") String email, @RequestParam("password") String password){
        return userService.login(email, password);
    }

    @GetMapping("/hours/{id}")
    public ResponseEntity<Map<String, Integer>> getHourDetails(@PathVariable("id") Long id){
        return userService.getHourDetails(id);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody User newUser){
        return userService.register(newUser);
    }

    @GetMapping("/verificationCode")
    public ResponseEntity<HashMap<String, String>> getVerificationCode(@RequestParam("email") String email) {
        HashMap<String, String> returnObject = new HashMap<>();
        returnObject.put("vCode", userService.getVerificationCode(email).getBody());
        return ResponseEntity.ok(returnObject);
    }

    //Error lekezelesek:
//    @ExceptionHandler
//    public ResponseEntity<String> handleUniqueError(DataIntegrityViolationException e){
//        return ResponseEntity.status(409).body("UniqueEmail");
//    }
}
