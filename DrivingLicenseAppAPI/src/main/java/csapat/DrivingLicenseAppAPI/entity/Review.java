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
import java.time.LocalDateTime;

@Entity
@Table(name = "review")
@Getter
@Setter
@NoArgsConstructor
@ToString
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "", procedureName = "", resultClasses = String.class),
        @NamedStoredProcedureQuery(name = "", procedureName = "", parameters = {
                @StoredProcedureParameter(name = "", type = Integer.class, mode = ParameterMode.IN)
        }, resultClasses = User.class),
        @NamedStoredProcedureQuery(name = "", procedureName = "", parameters = {
                @StoredProcedureParameter(name = "", type = Integer.class, mode = ParameterMode.IN)
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
    private float rating;

    @Column(name = "is_deleted")
    @NotNull
    @JsonIgnore
    private boolean isDeleted = false;

    @Column(name = "deleted_at")
    @Null
    @JsonIgnore
    private LocalDateTime deletedAt;

    //Kapcsolatok:
    @ManyToOne(cascade = {})
    @JoinColumn(name = "author_id")
    @JsonIgnoreProperties({"studentSchool", "studentInstructor", "reviewList", "requestList", "drivingLessons", "examRequestList", "instructorJoinRequestList"})
    private Students reviewAuthor;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "instructor_id")
    @Null
    @JsonIgnore
    private Instructors aboutInstructor;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "school_id")
    @Null
    @JsonIgnore
    private School aboutSchool;

    //Constructorok
    public Review(String text, LocalDate createdAt, float rating) {
        this.text = text;
        this.createdAt = createdAt;
        this.rating = rating;
    }
}
