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

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Map<String, String> loginBody){
        return userService.login(loginBody.get("email"), loginBody.get("password"));
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

    @PatchMapping("/passwordReset")
    public ResponseEntity<HashMap<String, String>> updatePassword(@RequestBody Map<String, String> body) {
        HashMap<String, String> returnObject = new HashMap<>();
        returnObject.put("result", userService.updatePassword(body.get("email"), body.get("newPassword")).getBody());
        return ResponseEntity.ok(returnObject);
    }
}
