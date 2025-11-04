package csapat.DrivingLicenseAppAPI.entity;

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

@Entity
@Table(name = "instructor")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Instructors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "promo_text")
    @NotNull
    private String promoText;

    @Column(name = "is_deleted")
    @NotNull
    private Boolean isDeleted = false;

    @Column(name = "deleted_at")
    @Null
    private LocalDateTime deletedAt;

    //Kapcsolatok:
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"instructor"})
    private User instructorUser;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "school_id")
    @JsonIgnoreProperties({"owner", "adminList", "instructorsList", "reviewList", "studentsList", "drivingLessonsType", "examRequestList"})
    private School instructorSchool;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @OneToMany(mappedBy = "aboutInstructor", fetch = FetchType.LAZY, cascade = {})
    @JsonIgnoreProperties({})
    private List<Review> reviewList;

    @OneToMany(mappedBy = "studentInstructor", fetch = FetchType.LAZY, cascade = {})
    @JsonIgnoreProperties({"studentSchool", "studentInstructor", "reviewList", "requestList", "drivingLessons", "examRequestList"})
    private List<Students> students;

    //innen van a baj
    @OneToMany(mappedBy = "dLessonInstructor", fetch = FetchType.LAZY, cascade = {})
    private List<DrivingLessonRequest> drivingLessonRequestList;

    @OneToMany(mappedBy = "examRequesterInstructor", fetch = FetchType.LAZY, cascade = {})
    private List<ExamRequest> examRequestList;

    @OneToMany(mappedBy = "dinstructor", fetch = FetchType.LAZY, cascade = {})
    private List<DrivingLessons> instructorDrivingLessons;

    //
    @OneToMany(
            mappedBy = "instructorJoinRequestInstructor",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JsonIgnoreProperties({})
    private List<InstructorJoinRequest> instructorJoinRequestList;

    //Constructorok:
    public Instructors(String promoText) {
        this.promoText = promoText;
    }

    public Instructors(School instructorSchool, User instructorUser) {
        this.instructorSchool = instructorSchool;
        this.instructorUser = instructorUser;
    }
}
