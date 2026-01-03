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
import java.util.Date;

@Entity
@Table(name = "opening_detail")
@Getter
@Setter
@NoArgsConstructor
@ToString
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getAllOpeningDetail", procedureName = "getAllOpeningDetail", resultClasses = OpeningDetails.class),
        @NamedStoredProcedureQuery(name = "getOpeningDetail", procedureName = "getOpeningDetail", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Integer.class, mode = ParameterMode.IN)
        }, resultClasses = OpeningDetails.class),
        @NamedStoredProcedureQuery(name = "deleteOpeningDetail", procedureName = "deleteOpeningDetail", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Integer.class, mode = ParameterMode.IN)
        }, resultClasses = String.class)
})
public class OpeningDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "opening_time")
    @Null
    @Temporal(TemporalType.TIME)
    private Date openingTime;

    @Column(name = "close_time")
    @Null
    @Temporal(TemporalType.TIME)
    private Date closeTime;

    @Column(name = "day")
    @Size(max = 100)
    @NotNull
    private String day;

    @Column(name = "is_closed")
    private Boolean isClosed;

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
    @ManyToOne(cascade = {})
    @JoinColumn(name = "school_id")
    @JsonIgnore
    private School schoolOpeningDetail;
}
