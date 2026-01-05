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
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "instructor")
@Getter
@Setter
@NoArgsConstructor
@ToString
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getAllIntructor", procedureName = "getAllIntructor", resultClasses = Instructors.class),
        @NamedStoredProcedureQuery(name = "getInstructor", procedureName = "getInstructor", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Integer.class, mode = ParameterMode.IN)
        }, resultClasses = Instructors.class),
        @NamedStoredProcedureQuery(name = "deleteInstructor", procedureName = "deleteInstructor", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Integer.class, mode = ParameterMode.IN)
        }, resultClasses = String.class),
        @NamedStoredProcedureQuery(name = "getInstructorBySearch", procedureName = "getInstructorBySearch", parameters = {
                @StoredProcedureParameter(name = "fuelTypeIdIN", type = Integer.class, mode = ParameterMode.IN),
                @StoredProcedureParameter(name = "schoolIdIN", type = Integer.class, mode = ParameterMode.IN)
        }, resultClasses = Integer.class)
})
public class Instructors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "promo_text")
    @Null
    private String promoText;

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
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"instructor", "adminSchool"})
    private Users instructorUser;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "school_id")
    @JsonIgnoreProperties({"owner", "instructorsList", "adminList", "reviewList", "studentsList", "drivingLessonsType", "examRequestList", "schoolJoinRequestList"})
    @Null
    private School instructorSchool;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_id")
    @JsonIgnoreProperties({"instructor"})
    private Vehicle vehicle;

    @OneToMany(mappedBy = "aboutInstructor", fetch = FetchType.LAZY, cascade = {})
    @JsonIgnore
    private List<Review> reviewList;

    @OneToMany(mappedBy = "studentInstructor", fetch = FetchType.LAZY, cascade = {})
    @JsonIgnoreProperties({"studentSchool", "studentInstructor", "reviewList", "requestList", "drivingLessons", "examRequestList", "instructorJoinRequestList"})
    private List<Students> students;

    @OneToMany(mappedBy = "dLessonInstructor", fetch = FetchType.LAZY, cascade = {})
    @JsonIgnore
    private List<DrivingLessonRequest> drivingLessonRequestList;

    @OneToMany(mappedBy = "examRequesterInstructor", fetch = FetchType.LAZY, cascade = {})
    @JsonIgnore
    private List<ExamRequest> examRequestList;

    @OneToMany(mappedBy = "dinstructor", fetch = FetchType.LAZY, cascade = {})
    @JsonIgnore
    private List<DrivingLessons> instructorDrivingLessons;

    @OneToMany(
            mappedBy = "instructorJoinRequestInstructor",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JsonIgnore
    private List<InstructorJoinRequest> instructorJoinRequestList;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {}, mappedBy = "instructorsList")
    private List<DrivingLicenseCategory> categoryList;

    //Constructorok:
    public Instructors(String promoText) {
        this.promoText = promoText;
    }

    public Instructors(School instructorSchool, Users instructorUser) {
        this.instructorSchool = instructorSchool;
        this.instructorUser = instructorUser;
    }
}
