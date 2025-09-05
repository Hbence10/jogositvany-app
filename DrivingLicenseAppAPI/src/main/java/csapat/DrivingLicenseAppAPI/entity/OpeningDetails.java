package csapat.DrivingLicenseAppAPI.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalTime;

@Entity
@Table(name = "opening_details")
public class OpeningDetails {

    private int id;
    private LocalTime openingTime;
    private LocalTime closeTime;
    private String day;

    private School school;
}
