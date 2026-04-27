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

@Table(name = "student")
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getStudentByUserId", procedureName = "getStudentByUserId", parameters = {
                @StoredProcedureParameter(name = "userIdIN", mode = ParameterMode.IN, type = Long.class)
        }, resultClasses = {Students.class}),
        @NamedStoredProcedureQuery(name = "getAllStudent", procedureName = "getAllStudent", resultClasses = Students.class),
        @NamedStoredProcedureQuery(name = "getStudent", procedureName = "getStudent", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Long.class, mode = ParameterMode.IN)
        }, resultClasses = Students.class),
        @NamedStoredProcedureQuery(name = "deleteStudent", procedureName = "deleteStudent", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Long.class, mode = ParameterMode.IN)
        })
})
public class Students {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "is_deleted")
    @NotNull
    @JsonIgnore
    private Boolean isDeleted = false;

    @Column(name = "deleted_at")
    @Null
    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

    //Kapcsolatok
    @ManyToOne()
    @JoinColumn(name = "school_id")
    @JsonIgnoreProperties({"owner", "adminList", "instructorsList", "reviewList", "studentsList", "drivingLessonsType", "examRequestList", "schoolJoinRequestList"})
    private School studentSchool;

    @ManyToOne()
    @JoinColumn(name = "instructor_id")
    @JsonIgnoreProperties({"instructorSchool", "reviewList", "students", "drivingLessonRequestList", "examRequestList", "instructorDrivingLessons", "instructorJoinRequestList"})
    @Null
    private Instructors studentInstructor;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"student"})
    private Users studentUser;

    @OneToMany(mappedBy = "reviewAuthor", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Review> reviewList;

    @OneToMany(mappedBy = "dLessonRequestStudent", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<DrivingLessonRequest> requestList;

    @OneToMany(mappedBy = "dstudent", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<DrivingLessons> drivingLessons;

    @OneToMany(mappedBy = "instructorJoinRequestStudent", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<InstructorJoinRequest> instructorJoinRequestList;

    @ManyToOne
    @JoinColumn(name = "driving_license_category_id")
    @Null
    private DrivingLicenseCategory selectedCategory;

    public Students(Users studentUser, School studentSchool, DrivingLicenseCategory selectedCategory) {
        this.studentUser = studentUser;
        this.studentSchool = studentSchool;
        this.selectedCategory = selectedCategory;
    }

    public Students(Users studentUser, School studentSchool, DrivingLicenseCategory selectedCategory, Instructors instructors) {
        this.studentUser = studentUser;
        this.studentSchool = studentSchool;
        this.selectedCategory = selectedCategory;
        this.studentInstructor = instructors;
    }

    public Students(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public School getStudentSchool() {
        return studentSchool;
    }

    public void setStudentSchool(School studentSchool) {
        this.studentSchool = studentSchool;
    }

    public Instructors getStudentInstructor() {
        return studentInstructor;
    }

    public void setStudentInstructor(Instructors studentInstructor) {
        this.studentInstructor = studentInstructor;
    }

    public Users getStudentUser() {
        return studentUser;
    }

    public void setStudentUser(Users studentUser) {
        this.studentUser = studentUser;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    public List<DrivingLessonRequest> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<DrivingLessonRequest> requestList) {
        this.requestList = requestList;
    }

    public List<DrivingLessons> getDrivingLessons() {
        return drivingLessons;
    }

    public void setDrivingLessons(List<DrivingLessons> drivingLessons) {
        this.drivingLessons = drivingLessons;
    }

    public List<InstructorJoinRequest> getInstructorJoinRequestList() {
        return instructorJoinRequestList;
    }

    public void setInstructorJoinRequestList(List<InstructorJoinRequest> instructorJoinRequestList) {
        this.instructorJoinRequestList = instructorJoinRequestList;
    }

    public DrivingLicenseCategory getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(DrivingLicenseCategory selectedCategory) {
        this.selectedCategory = selectedCategory;
    }
}
