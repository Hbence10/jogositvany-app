package csapat.DrivingLicenseAppAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "opening_detail")
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
    private Integer openingTime;

    @Column(name = "close_time")
    @NotNull
    private Integer closeTime;

    @Column(name = "day")
    @Size(max = 100)
    @NotNull
    private String day;

    @Column(name = "is_deleted")
    @NotNull
    private boolean isDeleted = false;

    @Column(name = "deleted_at")
    @Null
    private LocalDateTime deletedAt;

    //Kapcsolatok:
    @ManyToOne(cascade = {})
    @JoinColumn(name = "school_id")
    private School schoolOpeningDetail;

    //Constructorok:
    public OpeningDetails(Integer openingTime, Integer closeTime, String day, boolean isDeleted, LocalDateTime deletedAt) {
        this.openingTime = openingTime;
        this.closeTime = closeTime;
        this.day = day;
        this.isDeleted = isDeleted;
        this.deletedAt = deletedAt;
    }
}
