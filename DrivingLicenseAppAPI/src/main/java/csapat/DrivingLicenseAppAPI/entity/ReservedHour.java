package csapat.DrivingLicenseAppAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "reserved_hour")
@Getter
@Setter
@NoArgsConstructor
@ToString
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getAllReservedHour", procedureName = "getAllReservedHour", resultClasses = ReservedHour.class),
        @NamedStoredProcedureQuery(name = "getReservedHour", procedureName = "getReservedHour", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Integer.class, mode = ParameterMode.IN)
        }, resultClasses = ReservedHour.class),
        @NamedStoredProcedureQuery(name = "deleteReservedHour", procedureName = "deleteReservedHour", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Integer.class, mode = ParameterMode.IN)
        }, resultClasses = String.class)
})
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

    @Column(name = "is_deleted")
    @NotNull
    private boolean isDeleted = false;

    @Column(name = "deleted_at")
    @Null
    private LocalDateTime deletedAt;

    //Kapcsolatok:
    @OneToOne(mappedBy = "reservedHour", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    private DrivingLessons drivingLessons;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "date_id")
    @JsonIgnoreProperties({})
    private ReservedDate reservedDate;

    //Constructorok:
    public ReservedHour(int start, int end) {
        this.start = start;
        this.end = end;
    }
}
