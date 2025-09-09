package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface UserRepository extends JpaRepository<Users, Long> {

    @Procedure(name = "Login", procedureName = "Login")
    Users login(@Param("emailIN") String username, @Param("passwordIN") String password);

    @Procedure(name = "RegisterUser", procedureName = "RegisterUser")
    String register(@Param("firstNameIN") String firstName, @Param("lastNameIN") String lastName, @Param("emailIN") String email, @Param("phoneIN") String phone, @Param("passwordIN") String password, @Param("birthDateIN") LocalDate birthDay, @Param("genderIN") String gender, @Param("educationQualificationIN") String education);
}
