package csapat.DrivingLicenseAppAPI.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalTime;

@Entity
@Table(name = "opening_details")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class OpeningDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "opening_time")
    @NotNull
    private LocalTime openingTime;

    @Column(name = "close_time")
    @NotNull
    private LocalTime closeTime;

    @Column(name = "day")
    @Size(max = 100)
    @NotNull
    private String day;

    //Kapcsolatok:
    @ManyToOne(cascade = {})
    @JoinColumn(name = "school_id")
    private School schoolOpeningDetail;

    //Constructorok:
    public OpeningDetails(int id, LocalTime openingTime, LocalTime closeTime, String day) {
        this.id = id;
        this.openingTime = openingTime;
        this.closeTime = closeTime;
        this.day = day;
    }
}
