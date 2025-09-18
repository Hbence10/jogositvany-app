package csapat.DrivingLicenseAppAPI.controller;

import csapat.DrivingLicenseAppAPI.entity.User;
import csapat.DrivingLicenseAppAPI.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public ResponseEntity<User> login(@RequestParam("email") String email, @RequestParam("password") String password){
        return userService.login(email, password);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody User newUser){
        return userService.register(newUser);
    }


    //Error lekezelesek:
    @ExceptionHandler
    public ResponseEntity<User> handleUniqueError(DataIntegrityViolationException e){
        return ResponseEntity.notFound().build();
    }
}
