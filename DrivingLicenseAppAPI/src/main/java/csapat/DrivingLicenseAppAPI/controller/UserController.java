package csapat.DrivingLicenseAppAPI.controller;

import csapat.DrivingLicenseAppAPI.entity.Users;
import csapat.DrivingLicenseAppAPI.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "", description = "")
    @PostMapping("/login")
    public ResponseEntity<Users> login(@RequestBody Map<String, String> loginBody){
        return userService.login(loginBody.get("email"), loginBody.get("password"));
    }

    @Operation(summary = "", description = "")
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody Users newUser){
        return userService.register(newUser);
    }

    //password reset
    @Operation(summary = "", description = "")
    @GetMapping("/getVerificationCode")
    public ResponseEntity<HashMap<String, Object>> getVerificationCode(@RequestParam("email") String email) {
        HashMap<String, Object> returnObject = new HashMap<>();
        returnObject.put("isSent", userService.getVerificationCode(email).getBody());
        return ResponseEntity.ok(returnObject);
    }

    @Operation(summary = "", description = "")
    @PostMapping("/checkVerificationCode")
    public ResponseEntity<Object> checkVerificationCode(@RequestBody Map<String, String> body){
        return userService.checkVCode(body.get("vCode"), body.get("email"));
    }

    @Operation(summary = "", description = "")
    @PatchMapping("/passwordReset")
    public ResponseEntity<String> updatePassword(@RequestBody Map<String, String> body) {
        return ResponseEntity.ok(userService.updatePassword(body.get("email"), body.get("newPassword")).getBody());
    }

    //Frissitesek:
    @Operation(summary = "", description = "")
    @PutMapping("/update")
    public ResponseEntity<Object> updateUser(@RequestBody Users updatedUser){
        return userService.updateUser(updatedUser);
    }

    @Operation(summary = "", description = "")
    @PatchMapping("/pfp/{id}")
    public ResponseEntity<Users> updatePfp(@PathVariable("id") Integer id, @RequestParam("pfpImg") MultipartFile file){
        return userService.updatePfp(id, file);
    }

    //Torles
    @Operation(summary = "", description = "")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Integer id){
        return userService.deleteUser(id);
    }

    //Egyeb endpointok:
    @Operation(summary = "", description = "")
    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable("id") Integer id){
        return null;
    }
}
