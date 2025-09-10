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
    public DrivingLessons() {
    }

    public DrivingLessons(int startKm, String location, String pickUpPlace, String dropOffPlace, boolean isPaid, int endKm) {
        this.startKm = startKm;
        this.location = location;
        this.pickUpPlace = pickUpPlace;
        this.dropOffPlace = dropOffPlace;
        this.isPaid = isPaid;
        this.endKm = endKm;
    }

    //Getterek & Setterek
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStartKm() {
        return startKm;
    }

    public void setStartKm(int startKm) {
        this.startKm = startKm;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPickUpPlace() {
        return pickUpPlace;
    }

    public void setPickUpPlace(String pickUpPlace) {
        this.pickUpPlace = pickUpPlace;
    }

    public String getDropOffPlace() {
        return dropOffPlace;
    }

    public void setDropOffPlace(String dropOffPlace) {
        this.dropOffPlace = dropOffPlace;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public int getEndKm() {
        return endKm;
    }

    public void setEndKm(int endKm) {
        this.endKm = endKm;
    }
}
