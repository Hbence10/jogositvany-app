package csapat.DrivingLicenseAppAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "reserved_date")
@Getter
@Setter
@NoArgsConstructor
@ToString
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getAllReservedDate", procedureName = "getAllReservedDate", resultClasses = ReservedDate.class),
        @NamedStoredProcedureQuery(name = "getReservedDate", procedureName = "getReservedDate", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Integer.class, mode = ParameterMode.IN)
        }, resultClasses = ReservedDate.class),
        @NamedStoredProcedureQuery(name = "deleteReservedDate", procedureName = "deleteReservedDate", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Integer.class, mode = ParameterMode.IN)
        }, resultClasses = String.class),
        @NamedStoredProcedureQuery(name = "getReservedDateByDate", procedureName = "getReservedDateByDate", parameters = {
                @StoredProcedureParameter(name = "wantedDateIN", type = Date.class, mode = ParameterMode.IN)
        })
})
public class ReservedDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "date")
    @NotNull
    private Date date;

    @Column(name = "is_full")
    @NotNull
    private Boolean isFull = false;

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
    @OneToMany(
            mappedBy = "reservedDate",
            fetch = FetchType.LAZY,
            cascade = {}
    )
    private List<ReservedHour> reservedHourList;

    //Constructorok:
    public ReservedDate(Date date) {
        this.date = date;
    }
}
