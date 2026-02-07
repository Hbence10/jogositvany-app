package csapat.DrivingLicenseAppAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "user")
@NamedStoredProcedureQueries({@NamedStoredProcedureQuery(name = "getUserByEmail", procedureName = "getUserByEmail", parameters = {@StoredProcedureParameter(name = "emailIN", type = String.class, mode = ParameterMode.IN)}, resultClasses = Users.class), @NamedStoredProcedureQuery(name = "getAllEmail", procedureName = "getAllEmail", resultClasses = String.class), @NamedStoredProcedureQuery(name = "getAllUser", procedureName = "getAllUser", resultClasses = Users.class), @NamedStoredProcedureQuery(name = "getUser", procedureName = "getUser", parameters = {@StoredProcedureParameter(name = "idIN", type = Integer.class, mode = ParameterMode.IN)}, resultClasses = Users.class), @NamedStoredProcedureQuery(name = "deleteUser", procedureName = "deleteUser", parameters = {@StoredProcedureParameter(name = "idIN", type = Integer.class, mode = ParameterMode.IN)}, resultClasses = String.class),

        @NamedStoredProcedureQuery(name = "setRoleOfUser", procedureName = "setRoleOfUser", parameters = {@StoredProcedureParameter(name = "userIdIN", type = Integer.class, mode = ParameterMode.IN), @StoredProcedureParameter(name = "roleIdIN", type = Integer.class, mode = ParameterMode.IN)})})
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

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
    @Size(max = 11)
    private String phone;

    @Column(name = "birth_date")
    @NotNull
    private Date birthDate;

    @Column(name = "gender")
    @NotNull
    @Size(max = 6)
    private String gender;

    @Column(name = "password")
    @NotNull
    private String password;

    @Column(name = "pfp_path")
    @NotNull
    private String pfpPath = "assets/icons/defaultProfileImg.svg";

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    private Date createdAt;

    @Column(name = "last_login")
    @Null
    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;

    @Column(name = "is_deleted")
    @JsonIgnore
    private Boolean isDeleted = false;

    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    @Null
    @JsonIgnore
    private Date deletedAt;

    @Column(name = "verification_code")
    @Null
    @JsonIgnore
    private String vCode;

    //Kapcsolatok:
    @ManyToOne(cascade = {})
    @JoinColumn(name = "role_id")
    @JsonIgnoreProperties({"userList"})
    private Role role = new Role(1, "ROLE_user");

    @OneToOne(mappedBy = "instructorUser")
    @JsonIgnoreProperties({"instructorUser", "reviewList", "drivingLessonRequestList", "examRequestList", "instructorDrivingLessons", "instructorJoinRequestList"})
    private Instructors instructor;

    @OneToOne(mappedBy = "studentUser")
    @JsonIgnoreProperties({"studentUser", "reviewList", "requestList", "drivingLessons", "examRequestList", "instructorJoinRequestList"})
    private Students student;

    @ManyToOne()
    @JoinColumn(name = "school_admin_id")
    @Null
    private School adminSchool;

    @ManyToOne()
    @JoinColumn(name = "education_id")
    @JsonIgnoreProperties({"userEducationList"})
    private Education userEducation;

    @OneToMany(mappedBy = "schoolJoinRequestUser", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<SchoolJoinRequest> schoolJoinRequestList;

    @OneToOne(mappedBy = "owner")
    private School ownedSchool;

    //constructorok
    public Users(String firstName, String lastName, String email, String phone, Date birthDate, String gender, String password, Education userEducation, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.birthDate = birthDate;
        this.gender = gender;
        this.password = password;
        this.userEducation = userEducation;
        this.role = role;
    }

    public Users(String firstName, String lastName, String email, String phone, Date birthDate, String gender, String password, Education userEducation) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.birthDate = birthDate;
        this.gender = gender;
        this.password = password;
        this.userEducation = userEducation;
    }

    public Users(Integer id, String firstName, String lastName, String email, String phone, Date birthDate, String gender, String password, Education userEducation) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.birthDate = birthDate;
        this.gender = gender;
        this.password = password;
        this.userEducation = userEducation;
    }

    public Users(String firstName, String lastName, String email, String phone, Date birthDate, String gender, String password, Education userEducation, String vCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.birthDate = birthDate;
        this.gender = gender;
        this.password = password;
        this.userEducation = userEducation;
        this.vCode = vCode;
    }
}
