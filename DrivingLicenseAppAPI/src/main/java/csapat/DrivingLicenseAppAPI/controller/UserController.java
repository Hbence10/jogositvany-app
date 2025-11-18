package csapat.DrivingLicenseAppAPI.controller;

import csapat.DrivingLicenseAppAPI.entity.User;
import csapat.DrivingLicenseAppAPI.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody User newUser){
        return userService.register(newUser);
    }

    //password reset
    @GetMapping("/getVerificationCode")
    public ResponseEntity<HashMap<String, Object>> getVerificationCode(@RequestParam("email") String email) {
        HashMap<String, Object> returnObject = new HashMap<>();
        returnObject.put("isSent", userService.getVerificationCode(email).getBody());
        return ResponseEntity.ok(returnObject);
    }

    @PostMapping("/checkVerificationCode")
    public ResponseEntity<Object> checkVerificationCode(@RequestBody Map<String, String> body){
        return userService.checkVCode(body.get("vCode"), body.get("email"));
    }

    @PatchMapping("/passwordReset")
    public ResponseEntity<String> updatePassword(@RequestBody Map<String, String> body) {
        return ResponseEntity.ok(userService.updatePassword(body.get("email"), body.get("newPassword")).getBody());
    }

    //Frissitesek:
    @PutMapping("/update")
    public ResponseEntity<Object> updateUser(@RequestBody User updatedUser){
        return userService.updateUser(updatedUser);
    }

    @PatchMapping("/pfp/{id}")
    public ResponseEntity<User> updatePfp(@PathVariable("id") Integer id, @RequestParam("pfpImg") MultipartFile file){
        return userService.updatePfp(id, file);
    }

    //Torles
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Integer id){
        return userService.deleteUser(id);
    }

    //Egyeb endpointok:
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Integer id){
        return null;
    }
}
