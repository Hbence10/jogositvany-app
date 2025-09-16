package csapat.DrivingLicenseAppAPI.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "driving_lessons")
@Getter
@Setter
@NoArgsConstructor
public class DrivingLessons {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "start_km")
    @NotNull
    @Size(max = 7)
    private int startKm;

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

    @Column(name = "is_paid")
    @NotNull
    private boolean isPaid = false;

    @Column(name = "end_km")
    @NotNull
    @Size(max = 7)
    private int endKm;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hour_id")
    private ReservedHour reservedHour;

    //Constructorok:
    public DrivingLessons(int startKm, String location, String pickUpPlace, String dropOffPlace, boolean isPaid, int endKm) {
        this.startKm = startKm;
        this.location = location;
        this.pickUpPlace = pickUpPlace;
        this.dropOffPlace = dropOffPlace;
        this.isPaid = isPaid;
        this.endKm = endKm;
    }

    @Override
    public String toString() {
        return "DrivingLessons{" +
                "id=" + id +
                ", startKm=" + startKm +
                ", location='" + location + '\'' +
                ", pickUpPlace='" + pickUpPlace + '\'' +
                ", dropOffPlace='" + dropOffPlace + '\'' +
                ", isPaid=" + isPaid +
                ", endKm=" + endKm +
                ", drivingLessonStatus=" + drivingLessonStatus +
                ", category=" + category +
                ", paymentMethod=" + paymentMethod +
                ", reservedHour=" + reservedHour +
                '}';
    }
}
