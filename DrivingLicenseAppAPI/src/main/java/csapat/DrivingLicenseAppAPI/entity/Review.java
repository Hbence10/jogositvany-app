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
@ToString
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getAllReview", procedureName = "getAllReview", resultClasses = Review.class),
        @NamedStoredProcedureQuery(name = "getReview", procedureName = "getReview", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Long.class, mode = ParameterMode.IN)
        }, resultClasses = Users.class),
        @NamedStoredProcedureQuery(name = "deleteReview", procedureName = "deleteReview", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Long.class, mode = ParameterMode.IN)
        })
})
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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
    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonIgnoreProperties(value = {"studentSchool", "studentInstructor", "requestList", "drivingLessons", "examRequestList", "instructorJoinRequestList"}, allowSetters = true)
    private Students reviewAuthor;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    @Null
//    @JsonIgnoreProperties(value = {"instructorUser", "instructorSchool", "vehicle", "students", "drivingLessonRequestList", "examRequestList", "instructorDrivingLessons", "instructorJoinRequestList"}, allowSetters = true)
    @JsonIgnore
    private Instructors aboutInstructor;

    @ManyToOne
    @JoinColumn(name = "school_id")
    @Null
//    @JsonIgnoreProperties(value = {"owner", "adminList", "instructorsList", "openingDetails", "reviewList", "studentsList", "drivingLessonsType", "examRequestList", "schoolJoinRequestList"}, allowSetters = true)
    @JsonIgnore
    private School aboutSchool;

    //Constructorok
    public Review(String text, Double rating, Students author) {
        this.text = text;
        this.rating = rating;
        this.reviewAuthor = author;
    }

    public Review(String text, Double rating, Boolean isAnonymous, Students author, Instructors aboutInstructor) {
        this.text = text;
        this.rating = rating;
        this.isAnonymous = isAnonymous;
        this.reviewAuthor = author;
        this.aboutInstructor = aboutInstructor;
    }

    public Review(String text, Double rating, Boolean isAnonymous, Students author, School aboutSchool) {
        this.text = text;
        this.rating = rating;
        this.isAnonymous = isAnonymous;
        this.reviewAuthor = author;
        this.aboutSchool = aboutSchool;
    }

    public Review(Long id, String text, LocalDate createdAt, Double rating, Boolean isAnonymous, Boolean isDeleted, Date deletedAt, Students reviewAuthor, Instructors aboutInstructor, School aboutSchool) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.rating = rating;
        this.isAnonymous = isAnonymous;
        this.isDeleted = isDeleted;
        this.deletedAt = deletedAt;
        this.reviewAuthor = reviewAuthor;
        this.aboutInstructor = aboutInstructor;
        this.aboutSchool = aboutSchool;
    }

    public Review() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Boolean getAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        isAnonymous = anonymous;
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

    public Students getReviewAuthor() {
        return reviewAuthor;
    }

    public void setReviewAuthor(Students reviewAuthor) {
        this.reviewAuthor = reviewAuthor;
    }

    public Instructors getAboutInstructor() {
        return aboutInstructor;
    }

    public void setAboutInstructor(Instructors aboutInstructor) {
        this.aboutInstructor = aboutInstructor;
    }

    public School getAboutSchool() {
        return aboutSchool;
    }

    public void setAboutSchool(School aboutSchool) {
        this.aboutSchool = aboutSchool;
    }
}
