package csapat.DrivingLicenseAppAPI.entity;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "Login", procedureName = "Login", parameters = {
                @StoredProcedureParameter(name = "emailIN", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "passwordIN", mode = ParameterMode.IN, type = String.class),
        }, resultClasses = {Users.class}),

        @NamedStoredProcedureQuery(name = "RegisterUser", procedureName = "RegisterUser", parameters = {
                @StoredProcedureParameter(name = "firstNameIN", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "lastNameIN", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "emailIN", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "phoneIN", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "passwordIN", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "birthDateIN", mode = ParameterMode.IN, type = LocalDate.class),
                @StoredProcedureParameter(name = "genderIN", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "educationQualificationIN", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "result", mode = ParameterMode.OUT, type = String.class)
        }, resultClasses = {String.class})
})
public class Users {
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

    //Kapcsolatok:
    @ManyToOne(cascade = {})
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToOne(mappedBy = "instructorUser", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH}) //Az Instructor class-ban levo field-re mutat
    private Instructors instructor;

    @OneToOne(mappedBy = "studentUser", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH}) //Az Instructor class-ban levo field-re mutat
    private Students students;

    @OneToOne(mappedBy = "administrator", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH}) //Az Instructor class-ban levo field-re mutat
    private School school;

    @OneToMany(
            mappedBy = "senderUser",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    private List<Request> sendedRequestList;

    @OneToMany(
            mappedBy = "pickerUser",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    private List<Request> recievedRequestList;

    //Constructorok:
    public Users() {
    }

    public Users(String firstName, String lastName, String email, String phone, Date birthDate, String gender, String educationQualification, String password, String pfpPath) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.birthDate = birthDate;
        this.gender = gender;
        this.educationQualification = educationQualification;
        this.password = password;
        this.pfpPath = pfpPath;
    }

    //Getterek & Setterek
    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender;
    }

    public String getEducationQualification() {
        return educationQualification;
    }

    public String getPassword() {
        return password;
    }

    public String getPfpPath() {
        return pfpPath;
    }

    @Override
    public String toString() {
        return "Users{" +
                "pfpPath='" + pfpPath + '\'' +
                ", password='" + password + '\'' +
                ", educationQualification='" + educationQualification + '\'' +
                ", gender='" + gender + '\'' +
                ", birthDate=" + birthDate +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", id=" + id +
                '}';
    }
}
