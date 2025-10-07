package csapat.DrivingLicenseAppAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Table(name = "students")
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
    private int id;

    //Kapcsolatok
    @ManyToOne(cascade = {})
    @JoinColumn(name = "school_id")
    @JsonIgnoreProperties({"owner", "adminList", "instructorsList", "studentsList", "reviewList"})
    private School studentSchool;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "instructor_id")
    @JsonIgnoreProperties({"students", "instructorDrivingLessons", "instructorSchool", "reviewList"})
    private Instructors studentInstructor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"students"})
    private User studentUser;

    @OneToMany(
            mappedBy = "reviewAuthor",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    private List<Review> reviewList;

    @OneToMany(
            mappedBy = "requestedStudent",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    private List<Request> requestList;

    @OneToMany(
            mappedBy = "dStudent",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    private List<DrivingLessons> drivingLessons;
}
