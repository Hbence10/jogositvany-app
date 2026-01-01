package csapat.DrivingLicenseAppAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.Date;

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
    private Integer id;

    @Column(name = "start_time")
    @NotNull
    @Temporal(TemporalType.TIME)
    private Date startTime;

    @Column(name = "end_time")
    @NotNull
    @Temporal(TemporalType.TIME)
    private Date endTime;

    @Column(name = "is_deleted")
    @NotNull
    @JsonIgnore
    private Boolean isDeleted = false;

    @Column(name = "deleted_at")
    @Null
    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

    //Kapcsolatok:
    @OneToOne(mappedBy = "reservedHour", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    private DrivingLessons drivingLessons;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "date_id")
    @JsonIgnoreProperties({})
    private ReservedDate reservedDate;

    //Constructorok
    public ReservedHour(Date startTime, Date endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
