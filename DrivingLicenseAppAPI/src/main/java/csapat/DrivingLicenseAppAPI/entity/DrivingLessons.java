package csapat.DrivingLicenseAppAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "driving_lessons")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class DrivingLessons {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "start_km")
    @NotNull
    @Size(max = 7)
    private int startKm;

    @Column(name = "end_km")
    @NotNull
    @Size(max = 7)
    private int endKm;

    @Column(name = "location")
    @NotNull
    @Size(max = 100)
    private String location;

    @Column(name = "pick_up_place")
    @NotNull
    @Size(max = 100)
    private String pickUpPlace;

    @Column(name = "drop_off_place")
    @NotNull
    @Size(max = 100)
    private String dropOffPlace;

    @Column(name = "lesson_hour_number")
    @NotNull
    private int lessonHourNumber;

    @Column(name = "is_paid")
    @NotNull
    private boolean isPaid = false;

    //Kapcsolatok:
    @ManyToOne(cascade = {})
    @JoinColumn(name = "status_id")
    private Status drivingLessonStatus;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "category_id")
    private DrivingLicenseCategory category;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    //
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hour_id")
    @JsonIgnoreProperties({})
    private ReservedHour reservedHour;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "drivingLessons")
    @JsonIgnoreProperties({})
    private Students dStudent;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "instructorDrivingLessons")
    @JsonIgnoreProperties({})
    private Instructors dInstructor;

    public DrivingLessons(int startKm, int endKm, String location, String pickUpPlace, String dropOffPlace, int lessonHourNumber, boolean isPaid) {
        this.startKm = startKm;
        this.endKm = endKm;
        this.location = location;
        this.pickUpPlace = pickUpPlace;
        this.dropOffPlace = dropOffPlace;
        this.lessonHourNumber = lessonHourNumber;
        this.isPaid = isPaid;
    }
}
