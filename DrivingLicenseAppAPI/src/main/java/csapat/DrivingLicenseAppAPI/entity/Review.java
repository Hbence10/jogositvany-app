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
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "review")
@Getter
@Setter
@NoArgsConstructor
@ToString
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getAllReview", procedureName = "getAllReview", resultClasses = Review.class),
        @NamedStoredProcedureQuery(name = "getReview", procedureName = "getReview", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Integer.class, mode = ParameterMode.IN)
        }, resultClasses = Users.class),
        @NamedStoredProcedureQuery(name = "deleteReview", procedureName = "deleteReview", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Integer.class, mode = ParameterMode.IN)
        }, resultClasses = String.class)
})
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "text")
    @NotNull
    private String text;

    @Column(name = "created_at")
    @NotNull
    @JsonIgnore
    private LocalDate createdAt;

    @Column(name = "rating")
    @NotNull
    private Double rating;

    @Column(name = "is_anonymous")
    @NotNull
    private Boolean isAnonymous = false;

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
    @ManyToOne()
    @JoinColumn(name = "author_id")
    @JsonIgnoreProperties(value = {"studentSchool", "studentInstructor", "requestList", "drivingLessons", "examRequestList", "instructorJoinRequestList"}, allowSetters = true)
    private Students reviewAuthor;

    @ManyToOne()
    @JoinColumn(name = "instructor_id")
    @Null
    @JsonIgnoreProperties(value = {"instructorUser", "instructorSchool", "vehicle", "students", "drivingLessonRequestList", "examRequestList", "instructorDrivingLessons", "instructorJoinRequestList"}, allowSetters = true)
    private Instructors aboutInstructor;

    @ManyToOne()
    @JoinColumn(name = "school_id")
    @Null
    @JsonIgnoreProperties(value = {"owner", "adminList", "instructorsList", "openingDetails", "reviewList", "studentsList", "drivingLessonsType", "examRequestList", "schoolJoinRequestList"}, allowSetters = true)
    private School aboutSchool;

    //Constructorok
    public Review(String text, Double rating, Students author) {
        this.text = text;
        this.rating = rating;
        this.reviewAuthor = author;
    }
}
