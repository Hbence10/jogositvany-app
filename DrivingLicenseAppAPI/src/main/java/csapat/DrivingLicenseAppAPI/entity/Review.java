package csapat.DrivingLicenseAppAPI.entity;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "review")
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

    @ManyToOne(cascade = {})
    @JoinColumn(name = "author_id")
    private Students reviewAuthor;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "about_id")
    private Instructors aboutInstructor;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "about_id")
    private School aboutSchool;
}
