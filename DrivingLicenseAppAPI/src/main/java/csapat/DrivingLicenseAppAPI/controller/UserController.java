package csapat.DrivingLicenseAppAPI.controller;

import csapat.DrivingLicenseAppAPI.entity.Users;
import csapat.DrivingLicenseAppAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public Users login(@RequestParam("email") String email, @RequestParam("password") String password){
        return userService.login(email, password);
    }

    @PostMapping("/register")
    public String register(@RequestBody Map<String, Object> newUser){
        return userService.register(newUser);
    }
}
