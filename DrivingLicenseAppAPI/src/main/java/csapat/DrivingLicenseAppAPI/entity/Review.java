package csapat.DrivingLicenseAppAPI.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDate;

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

    //Kapcsolatok:
    @ManyToOne(cascade = {})
    @JoinColumn(name = "author_id")
    private Students reviewAuthor;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "instructor_id", insertable = false, updatable = false)
    @Null
    private Instructors aboutInstructor;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "school_id", insertable = false, updatable = false)
    @Null
    private School aboutSchool;

    //Constructorok
    public Review(String text, LocalDate createdAt, float rating) {
        this.text = text;
        this.createdAt = createdAt;
        this.rating = rating;
    }
}
