package csapat.DrivingLicenseAppAPI.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "driving_lessons")
public class DrivingLessons {

    private int id;
    private int startKm;
    private String location;
    private String pickUpPlace;
    private String dropOffPlace;
    private boolean isPaid = false;
    private int endKm;

    private Status status;
    private DrivingLicenseCategory category;
    private PaymentMethod paymentMethod;
    private ReservedHour reservedHour;
}
