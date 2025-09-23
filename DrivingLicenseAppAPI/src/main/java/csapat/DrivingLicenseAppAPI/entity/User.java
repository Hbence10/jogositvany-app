package csapat.DrivingLicenseAppAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "Login", procedureName = "Login", parameters = {
                @StoredProcedureParameter(name = "emailIN", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "passwordIN", mode = ParameterMode.IN, type = String.class),
        }, resultClasses = {User.class})
})
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {
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

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "last_login")
    @Null
    private Date lastLogin;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    @Null
    private Date deletedAt;

    //Kapcsolatok:
    @ManyToOne(cascade = {})
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToOne(mappedBy = "instructorUser", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH}) //Az Instructor class-ban levo field-re mutat
    @JsonIgnore
    private Instructors instructor;

    @OneToOne(mappedBy = "studentUser", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH}) //Az Instructor class-ban levo field-re mutat
    @JsonIgnore
    private Students students;

    @OneToOne(mappedBy = "administrator", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH}) //Az Instructor class-ban levo field-re mutat
    @JsonIgnore
    private School school;

    @OneToMany(
            mappedBy = "senderUser",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JsonIgnore
    private List<Request> sendedRequestList;

    @OneToMany(
            mappedBy = "pickerUser",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JsonIgnore
    private List<Request> recievedRequestList;

    //Constructorok:
    public User(String firstName, String lastName, String email, String phone, Date birthDate, String gender, String educationQualification, String password, String pfpPath) {
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
}
