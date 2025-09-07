package csapat.DrivingLicenseAppAPI.entity;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "users")
public class Users {
//    @Temporal(TemporalType.TIMESTAMP)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    @NotNull
    @Size(max = 100)
    private String firstName;

    @Column(name = "last_name")
    @NotNull
    @Size(max = 100)
    private String lastName;

    @Column(name = "email")
    @NotNull
    @Size(max = 100)
    private String email;

    @Column(name = "phone")
    @NotNull
    @Size(max = 50)
    private String phone;

    @Column(name = "birth_date")
    @NotNull
    private Date birthDate;

    @Column(name = "gender")
    @NotNull
    @Size(max = 50)
    private String gender;

    @Column(name = "education_qualification")
    @NotNull
    @Size(max = 150)
    private String educationQualification;

    @Column(name = "password")
    @Size(max = 64)
    @NotNull
    private String password;

    @Column(name = "pfp_path")
    @NotNull
    @Size(max = 64)
    private String pfpPath;


    private Role role;



}
