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
    private Integer id;

    @Column(name = "start_hour")
    @Size(max = 2)
    @NotNull
    private Integer startHour;

    @Column(name = "start_minute")
    @Size(max = 2)
    @NotNull
    private Integer startMinute;

    @Column(name = "end_hour")
    @Size(max = 2)
    @NotNull
    private Integer endHour;

    @Column(name = "end_minute")
    @Size(max = 2)
    @NotNull
    private Integer endMinute;

    @Column(name = "is_deleted")
    @NotNull
    @JsonIgnore
    private Boolean isDeleted = false;

    @Column(name = "deleted_at")
    @Null
    @JsonIgnore
    private LocalDateTime deletedAt;

    //Kapcsolatok:
    @OneToOne(mappedBy = "reservedHour", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    private DrivingLessons drivingLessons;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "date_id")
    @JsonIgnoreProperties({})
    private ReservedDate reservedDate;
}
