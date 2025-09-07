package csapat.DrivingLicenseAppAPI.entity;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalTime;

@Entity
@Table(name = "opening_details")
public class OpeningDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "opening_time")
    @Temporal(TemporalType.TIME)
    @NotNull
    private LocalTime openingTime;

    @Column(name = "close_time")
    @Temporal(TemporalType.TIME)
    @NotNull
    private LocalTime closeTime;

    @Column(name = "day")
    @Size(max = 100)
    @NotNull
    private String day;

    private School school;
}
