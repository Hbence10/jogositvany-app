package csapat.DrivingLicenseAppAPI.controller;

import csapat.DrivingLicenseAppAPI.entity.User;
import csapat.DrivingLicenseAppAPI.service.UserService;
import lombok.RequiredArgsConstructor;
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

    //password reset
    @PostMapping("/checkVerificationCode")
    public ResponseEntity<Object> checkVerificationCode(@RequestBody Map<String, String> codeObject){
        return userService.checkVCode(codeObject.get("vCode"));
    }

    @GetMapping("/getVerificationCode")
    public ResponseEntity<HashMap<String, Object>> getVerificationCode(@RequestParam("email") String email) {
        HashMap<String, Object> returnObject = new HashMap<>();
        returnObject.put("isSent", userService.getVerificationCode(email).getBody());
        return ResponseEntity.ok(returnObject);
    }

    @PatchMapping("/passwordReset")
    public ResponseEntity<HashMap<String, String>> updatePassword(@RequestBody Map<String, String> body) {
        HashMap<String, String> returnObject = new HashMap<>();
        returnObject.put("result", userService.updatePassword(body.get("email"), body.get("newPassword")).getBody());
        return ResponseEntity.ok(returnObject);
    }

    //
    @PutMapping("/update")
    public ResponseEntity<Object> updateUser(@RequestBody User updatedUser){
        return userService.updateUser(updatedUser);
    }

    //
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("id") Long id){
        return userService.deleteUser(id);
    }
}
