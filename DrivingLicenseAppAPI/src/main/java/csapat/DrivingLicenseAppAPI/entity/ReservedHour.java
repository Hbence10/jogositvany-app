package csapat.DrivingLicenseAppAPI.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "reserved_hour")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReservedHour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "start")
    @Size(max = 2)
    @NotNull
    private int start;

    @Column(name = "end")
    @Size(max = 2)
    @NotNull
    private int end;

    //Kapcsolatok:
    @OneToOne(mappedBy = "reservedHour", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH}) //Az Instructor class-ban levo field-re mutat
    private DrivingLessons drivingLessons;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "date_id")
    private ReservedDate reservedDate;

    //Constructorok:
    public ReservedHour(int start, int end) {
        this.start = start;
        this.end = end;
    }
}
