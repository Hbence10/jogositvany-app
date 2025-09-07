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
//    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private LocalDate createdAt;

    @Column(name = "rating")
    @NotNull
    private float rating;

//    private Users author;
//    private Instructors aboutInstructor;
//    private School aboutSchool;
}
