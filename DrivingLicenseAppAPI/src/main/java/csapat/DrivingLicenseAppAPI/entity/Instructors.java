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
@NoArgsConstructor
@ToString
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getAllIntructor", procedureName = "getAllIntructor", resultClasses = Instructors.class),
        @NamedStoredProcedureQuery(name = "getInstructor", procedureName = "getInstructor", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Long.class, mode = ParameterMode.IN)
        }, resultClasses = Instructors.class),
        @NamedStoredProcedureQuery(name = "deleteInstructor", procedureName = "deleteInstructor", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Long.class, mode = ParameterMode.IN)
        }),
        @NamedStoredProcedureQuery(name = "getInstructorBySearch", procedureName = "getInstructorBySearch", parameters = {
                @StoredProcedureParameter(name = "fuelTypeIdIN", type = Long.class, mode = ParameterMode.IN),
                @StoredProcedureParameter(name = "schoolIdIN", type = Long.class, mode = ParameterMode.IN),
                @StoredProcedureParameter(name = "categoryIdIN", type = Long.class, mode = ParameterMode.IN)
        }, resultClasses = Integer.class)})
public class Instructors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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
    @OneToOne()
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"instructor", "adminSchool"})
    private Users instructorUser;

    @ManyToOne()
    @JoinColumn(name = "school_id")
    @JsonIgnoreProperties({"owner", "instructorsList", "adminList", "reviewList", "studentsList", "drivingLessonsType", "examRequestList", "schoolJoinRequestList"})
    @Null
    private School instructorSchool;

    @OneToOne()
    @JoinColumn(name = "vehicle_id")
    @JsonIgnoreProperties({"instructor"})
    private Vehicle vehicle;

    @OneToMany(mappedBy = "aboutInstructor", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Review> reviewList;

    @OneToMany(mappedBy = "studentInstructor", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"studentSchool", "studentInstructor", "reviewList", "requestList", "drivingLessons", "examRequestList", "instructorJoinRequestList"})
    private List<Students> students;

    @OneToMany(mappedBy = "dLessonInstructor", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<DrivingLessonRequest> drivingLessonRequestList;

    @OneToMany(mappedBy = "dinstructor", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<DrivingLessons> instructorDrivingLessons;

    @OneToMany(mappedBy = "instructorJoinRequestInstructor", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<InstructorJoinRequest> instructorJoinRequestList;

    //Constructorok:
    public Instructors(String promoText) {
        this.promoText = promoText;
    }

    public Instructors(School instructorSchool, Users instructorUser) {
        this.instructorSchool = instructorSchool;
        this.instructorUser = instructorUser;
    }

    public Instructors(School instructorSchool, Users instructorUser, Vehicle vehicle) {
        this.instructorSchool = instructorSchool;
        this.instructorUser = instructorUser;
        this.vehicle = vehicle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPromoText() {
        return promoText;
    }

    public void setPromoText(String promoText) {
        this.promoText = promoText;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Users getInstructorUser() {
        return instructorUser;
    }

    public void setInstructorUser(Users instructorUser) {
        this.instructorUser = instructorUser;
    }

    public School getInstructorSchool() {
        return instructorSchool;
    }

    public void setInstructorSchool(School instructorSchool) {
        this.instructorSchool = instructorSchool;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    public List<Students> getStudents() {
        return students;
    }

    public void setStudents(List<Students> students) {
        this.students = students;
    }

    public List<DrivingLessonRequest> getDrivingLessonRequestList() {
        return drivingLessonRequestList;
    }

    public void setDrivingLessonRequestList(List<DrivingLessonRequest> drivingLessonRequestList) {
        this.drivingLessonRequestList = drivingLessonRequestList;
    }

    public List<DrivingLessons> getInstructorDrivingLessons() {
        return instructorDrivingLessons;
    }

    public void setInstructorDrivingLessons(List<DrivingLessons> instructorDrivingLessons) {
        this.instructorDrivingLessons = instructorDrivingLessons;
    }

    public List<InstructorJoinRequest> getInstructorJoinRequestList() {
        return instructorJoinRequestList;
    }

    public void setInstructorJoinRequestList(List<InstructorJoinRequest> instructorJoinRequestList) {
        this.instructorJoinRequestList = instructorJoinRequestList;
    }
}
