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
                @StoredProcedureParameter(name = "userIdIN", mode = ParameterMode.IN, type = Integer.class)
        }, resultClasses = {Students.class}),
        @NamedStoredProcedureQuery(name = "getAllStudent", procedureName = "getAllStudent", resultClasses = Students.class),
        @NamedStoredProcedureQuery(name = "getStudent", procedureName = "getStudent", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Integer.class, mode = ParameterMode.IN)
        }, resultClasses = Students.class),
        @NamedStoredProcedureQuery(name = "deleteStudent", procedureName = "deleteStudent", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Integer.class, mode = ParameterMode.IN)
        }, resultClasses = String.class)
})
public class Students {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

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
    @ManyToOne(cascade = {})
    @JoinColumn(name = "school_id")
    @JsonIgnoreProperties({"owner", "adminList", "instructorsList", "reviewList", "studentsList", "drivingLessonsType", "examRequestList", "schoolJoinRequestList"})
    private School studentSchool;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "instructor_id")
    @JsonIgnoreProperties({"instructorSchool", "reviewList", "students", "drivingLessonRequestList", "examRequestList", "instructorDrivingLessons", "instructorJoinRequestList"})
    @Null
    private Instructors studentInstructor;

    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"student"})
    private Users studentUser;

    @OneToMany(mappedBy = "reviewAuthor", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JsonIgnore
    private List<Review> reviewList;

    @OneToMany(mappedBy = "dLessonRequestStudent", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JsonIgnore
    private List<DrivingLessonRequest> requestList;

    @OneToMany(mappedBy = "dstudent", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JsonIgnore
    private List<DrivingLessons> drivingLessons;

    @OneToMany(mappedBy = "instructorJoinRequestStudent", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JsonIgnore
    private List<InstructorJoinRequest> instructorJoinRequestList;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "driving_license_category_id")
    @Null
    private DrivingLicenseCategory selectedCategory;

    public Students(Users studentUser, School studentSchool, DrivingLicenseCategory selectedCategory) {
        this.studentUser = studentUser;
        this.studentSchool = studentSchool;
        this.selectedCategory = selectedCategory;
    }

    public Students(Integer id) {
        this.id = id;
    }
}
