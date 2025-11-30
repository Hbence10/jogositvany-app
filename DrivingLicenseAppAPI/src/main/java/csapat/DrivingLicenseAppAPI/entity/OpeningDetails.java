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
    @JsonIgnore
    private Boolean isDeleted = false;

    @Column(name = "deleted_at")
    @Null
    @JsonIgnore
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
