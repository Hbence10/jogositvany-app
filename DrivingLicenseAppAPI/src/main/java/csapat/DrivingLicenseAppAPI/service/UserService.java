package csapat.DrivingLicenseAppAPI.service;

import csapat.DrivingLicenseAppAPI.entity.Users;
import csapat.DrivingLicenseAppAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Users login(String email, String password) {
        return userRepository.login(email, password);
    }

    public String register(Map<String, Object> newUser){
        return userRepository.register(
                String.valueOf(newUser.get("firstName")),
                String.valueOf(newUser.get("lastName")),
                String.valueOf(newUser.get("email")),
                String.valueOf(newUser.get("phone")),
                String.valueOf(newUser.get("password")),
                LocalDate.parse(String.valueOf(newUser.get("birthDate"))),
                String.valueOf(newUser.get("gender")),
                String.valueOf(newUser.get("education"))
        );
    }
}
