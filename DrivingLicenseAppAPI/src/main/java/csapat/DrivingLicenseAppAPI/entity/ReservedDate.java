package csapat.DrivingLicenseAppAPI.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "reserved_date")
@Getter
@Setter
@NoArgsConstructor
@ToString
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "", procedureName = "", resultClasses = String.class),
        @NamedStoredProcedureQuery(name = "", procedureName = "", parameters = {
                @StoredProcedureParameter(name = "", type = Integer.class, mode = ParameterMode.IN)
        }, resultClasses = User.class),
        @NamedStoredProcedureQuery(name = "", procedureName = "", parameters = {
                @StoredProcedureParameter(name = "", type = Integer.class, mode = ParameterMode.IN)
        }, resultClasses = String.class)
})
public class ReservedDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "date")
    @NotNull
    private Date date;

    @Column(name = "is_full")
    @NotNull
    private boolean isFull = false;

    @Column(name = "is_deleted")
    @NotNull
    private boolean isDeleted = false;

    @Column(name = "deleted_at")
    @Null
    private LocalDateTime deletedAt;

    //Kapcsolatok:
    @OneToMany(
            mappedBy = "reservedDate",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    private List<ReservedHour> reservedHourList;

    //Constructorok:
    public ReservedDate(Date date, boolean isFull) {
        this.date = date;
        this.isFull = isFull;
    }
}
