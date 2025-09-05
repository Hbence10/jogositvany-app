package csapat.DrivingLicenseAppAPI.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "instructor")
public class Instructors {

    private int id;
    private String promoText;

    private Users user;
    private School school;
    private Vehicle vehicle;
}
