package csapat.DrivingLicenseAppAPI.entity;

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
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "text")
    @NotNull
    private String text;

    @Column(name = "created_at")
    @NotNull
    private LocalDate createdAt;

    @Column(name = "rating")
    @NotNull
    private float rating;

    @Column(name = "is_deleted")
    @NotNull
    private boolean isDeleted = false;

    @Column(name = "deleted_at")
    @Null
    private LocalDateTime deletedAt;

    //Kapcsolatok:
    @ManyToOne(cascade = {})
    @JoinColumn(name = "author_id")
    @JsonIgnoreProperties({})
    private Students reviewAuthor;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "instructor_id")
    @Null
    @JsonIgnoreProperties({})
    private Instructors aboutInstructor;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "school_id")
    @Null
    @JsonIgnoreProperties({})
    private School aboutSchool;

    //Constructorok
    public Review(String text, LocalDate createdAt, float rating) {
        this.text = text;
        this.createdAt = createdAt;
        this.rating = rating;
    }
}
