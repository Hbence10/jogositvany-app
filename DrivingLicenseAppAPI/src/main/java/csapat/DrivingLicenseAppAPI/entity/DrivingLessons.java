package csapat.DrivingLicenseAppAPI.entity;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "driving_lessons")
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

//    private Status status;
//    private DrivingLicenseCategory category;
//    private PaymentMethod paymentMethod;
//    private ReservedHour reservedHour;
}
