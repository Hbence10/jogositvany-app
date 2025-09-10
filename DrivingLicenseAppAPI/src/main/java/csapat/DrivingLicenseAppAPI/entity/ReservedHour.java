package csapat.DrivingLicenseAppAPI.entity;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "reserved_hour")
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
    public ReservedHour() {
    }

    public ReservedHour(int start, int end) {
        this.start = start;
        this.end = end;
    }

    //Getterek & Setterek:
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
