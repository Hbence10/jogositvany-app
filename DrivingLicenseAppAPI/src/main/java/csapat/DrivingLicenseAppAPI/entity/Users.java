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
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getUserByEmail", procedureName = "getUserByEmail", parameters = {
                @StoredProcedureParameter(name = "emailIN", type = String.class, mode = ParameterMode.IN)
        }, resultClasses = Users.class),
        @NamedStoredProcedureQuery(name = "getAllEmail", procedureName = "getAllEmail", resultClasses = String.class),
        @NamedStoredProcedureQuery(name = "getAllUser", procedureName = "getAllUser", resultClasses = Users.class),
        @NamedStoredProcedureQuery(name = "getUser", procedureName = "getUser", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Integer.class, mode = ParameterMode.IN)
        }, resultClasses = Users.class),
        @NamedStoredProcedureQuery(name = "deleteUser", procedureName = "deleteUser", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Integer.class, mode = ParameterMode.IN)
        }, resultClasses = String.class)
})
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

    @OneToOne(mappedBy = "instructorUser", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonIgnoreProperties({"instructorUser", "reviewList", "drivingLessonRequestList", "examRequestList", "instructorDrivingLessons", "instructorJoinRequestList"})
    private Instructors instructor;

    @OneToOne(mappedBy = "studentUser", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonIgnoreProperties({"studentUser", "reviewList", "requestList", "drivingLessons", "examRequestList", "instructorJoinRequestList"})
    private Students student;

    @OneToOne(cascade = {})
    @JoinColumn(name = "school_administrator_id")
    private School adminSchool;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "education_id")
    @JsonIgnoreProperties({"userEducationList"})
    private Education userEducation;

    @OneToMany(
            mappedBy = "schoolJoinRequestUser",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JsonIgnore
    private List<SchoolJoinRequest> schoolJoinRequestList;

    @OneToOne(mappedBy = "owner", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    private School ownedSchool;

}
