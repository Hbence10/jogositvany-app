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
}
