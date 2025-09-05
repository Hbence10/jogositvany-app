package csapat.DrivingLicenseAppAPI.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "review")
public class Review {

    private int id;
    private String text;
    private LocalDate createdAt;
    private float rating;

    private Users author;
    private Users about;
}
