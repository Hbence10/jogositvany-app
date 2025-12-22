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
@Table(name = "school")
@Getter
@Setter
@NoArgsConstructor
@ToString
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getAllSchool", procedureName = "getAllSchool", resultClasses = School.class),
        @NamedStoredProcedureQuery(name = "getSchool", procedureName = "getSchool", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Integer.class, mode = ParameterMode.IN)
        }, resultClasses = School.class),
        @NamedStoredProcedureQuery(name = "deleteSchool", procedureName = "deleteSchool", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Integer.class, mode = ParameterMode.IN)
        }, resultClasses = String.class),
        @NamedStoredProcedureQuery(name = "getSchoolBySearch", procedureName = "getSchoolBySearch", parameters = {
                @StoredProcedureParameter(name = "nameIN", type = String.class, mode = ParameterMode.IN),
                @StoredProcedureParameter(name = "townnameIN", type = String.class, mode = ParameterMode.IN),
        }, resultClasses = Integer.class)
})
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    @NotNull
    @Size(max = 100)
    private String name;

    @Column(name = "email")
    @NotNull
    @Size(max = 100)
    private String email;

    @Column(name = "phone")
    @NotNull
    @Size(max = 100)
    private String phone;

    @Column(name = "country")
    @NotNull
    @Size(max = 100)
    private String country;

    @Column(name = "town")
    @NotNull
    @Size(max = 100)
    private String town;

    @Column(name = "address")
    @NotNull
    @Size(max = 100)
    private String address;

    @Column(name = "promo_text")
    @NotNull
    private String promoText;

    @Column(name = "banner_img_path")
    @NotNull
    private String bannerImgPath = "";

    @Column(name = "is_deleted")
    @NotNull
    @JsonIgnore
    private Boolean isDeleted = false;

    @Column(name = "deleted_at")
    @Null
    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

    //Kapcsolatok:
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id")
    @JsonIgnoreProperties({"ownedSchool"})
    private Users owner;

    @OneToMany(
            mappedBy = "adminSchool",
            cascade = {}
    )
    @JsonIgnore
    @Null
    private List<Users> adminList;

    @OneToMany(
            mappedBy = "instructorSchool",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JsonIgnoreProperties({"instructorSchool", "vehicle", "reviewList", "students", "drivingLessonRequestList", "examRequestList", "instructorDrivingLessons", "instructorJoinRequestList"})
    private List<Instructors> instructorsList;

    @OneToMany(
            mappedBy = "schoolOpeningDetail",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JsonIgnoreProperties({"schoolOpeningDetail"})
    private List<OpeningDetails> openingDetails;

    @OneToMany(
            mappedBy = "aboutSchool",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JsonIgnore
    private List<Review> reviewList;

    @OneToMany(
            mappedBy = "studentSchool",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JsonIgnoreProperties({"studentSchool", "studentInstructor"})
    private List<Students> studentsList;

    @OneToMany(
            mappedBy = "drivingTypeSchool",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JsonIgnore
    private List<DrivingLessonType> drivingLessonsType;

    @JsonIgnore
    @OneToMany(
            mappedBy = "examSchool",
            fetch = FetchType.LAZY,
            cascade = {}
    )
    private List<ExamRequest> examRequestList;

    @OneToMany(
            mappedBy = "schoolJoinRequestSchool",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JsonIgnore
    private List<SchoolJoinRequest> schoolJoinRequestList;
}
