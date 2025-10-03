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
        @NamedStoredProcedureQuery(name = "getUserByEmail", procedureName = "getUserByEmail", parameters = {
                @StoredProcedureParameter(name = "emailIN", type = String.class, mode = ParameterMode.IN)
        }, resultClasses = {User.class}),

        @NamedStoredProcedureQuery(name = "getAllEmail", procedureName = "getAllEmail", resultClasses = String.class),
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

    @Column(name = "password")
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
    @ManyToOne(cascade = {CascadeType.DETACH})
    @JoinColumn(name = "role_id")
    private Role role = new Role(1, "user");

    @OneToOne(mappedBy = "instructorUser", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    private Instructors instructor;

    @OneToOne(mappedBy = "studentUser", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonIgnore
    private Students students;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "school_administrator_id")
    private School adminSchool;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "education_id")
    private Education userEducation;

    //Constructorok:
    public User(String firstName, String lastName, String email, String phone, Date birthDate, String gender, String password, String pfpPath) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.birthDate = birthDate;
        this.gender = gender;
        this.password = password;
        this.pfpPath = pfpPath;
    }
}
