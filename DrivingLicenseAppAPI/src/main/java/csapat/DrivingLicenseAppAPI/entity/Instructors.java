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
    private int id;

    @Column(name = "promo_text")
    @NotNull
    private String promoText;

    @Column(name = "is_deleted")
    @NotNull
    private boolean isDeleted = false;

    @Column(name = "deleted_at")
    @Null
    private LocalDateTime deletedAt;

    //Kapcsolatok:
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({})
    private User instructorUser;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "school_id")
    @JsonIgnoreProperties({"owner", "instructorsList", "studentsList"})
    private School instructorSchool;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @OneToMany(
            mappedBy = "aboutInstructor",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JsonIgnoreProperties({})
    private List<Review> reviewList;

    @OneToMany(
            mappedBy = "studentInstructor",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JsonIgnoreProperties({})
    private List<Students> students;

    @OneToMany(
            mappedBy = "dInstructor",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JsonIgnoreProperties({})
    private List<DrivingLessons> instructorDrivingLessons;

    //Constructorok:
    public Instructors(String promoText) {
        this.promoText = promoText;
    }
}
