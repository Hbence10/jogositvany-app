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
import java.time.LocalDateTime;
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
        }, resultClasses = {Students.class})
})
public class Students {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "is_deleted")
    @NotNull
    private Boolean isDeleted = false;

    @Column(name = "deleted_at")
    @Null
    private LocalDateTime deletedAt;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"students", "instructor"})
    private User studentUser;

    @OneToMany(
            mappedBy = "reviewAuthor",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    private List<Review> reviewList;

    @OneToMany(
            mappedBy = "dlessonRequestStudent",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JsonIgnoreProperties({})
    private List<DrivingLessonRequest> requestList;

    @OneToMany(
            mappedBy = "dstudent",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JsonIgnoreProperties({})
    private List<DrivingLessons> drivingLessons;

    @OneToMany(
            mappedBy = "examStudent",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JsonIgnoreProperties({})
    private List<ExamRequest> examRequestList;

    @OneToMany(
            mappedBy = "instructorJoinRequestStudent",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JsonIgnoreProperties({})
    private List<InstructorJoinRequest> instructorJoinRequestList;

    public Students(User studentUser, School studentSchool) {
        this.studentUser = studentUser;
        this.studentSchool = studentSchool;
    }

    public Students(Integer id) {
        this.id = id;
    }
}
