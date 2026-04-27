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
@ToString
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getAllOpeningDetail", procedureName = "getAllOpeningDetail", resultClasses = OpeningDetails.class),
        @NamedStoredProcedureQuery(name = "getOpeningDetail", procedureName = "getOpeningDetail", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Long.class, mode = ParameterMode.IN)
        }, resultClasses = OpeningDetails.class),
        @NamedStoredProcedureQuery(name = "deleteOpeningDetail", procedureName = "deleteOpeningDetail", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Long.class, mode = ParameterMode.IN)
        })
})
public class OpeningDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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
    @ManyToOne
    @JoinColumn(name = "school_id")
    @JsonIgnore
    private School schoolOpeningDetail;

    public OpeningDetails(Long id, Date openingTime, Date closeTime, String day, Boolean isClosed, Boolean isDeleted, Date deletedAt, School schoolOpeningDetail) {
        this.id = id;
        this.openingTime = openingTime;
        this.closeTime = closeTime;
        this.day = day;
        this.isClosed = isClosed;
        this.isDeleted = isDeleted;
        this.deletedAt = deletedAt;
        this.schoolOpeningDetail = schoolOpeningDetail;
    }

    public OpeningDetails() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(Date openingTime) {
        this.openingTime = openingTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Boolean getClosed() {
        return isClosed;
    }

    public void setClosed(Boolean closed) {
        isClosed = closed;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public School getSchoolOpeningDetail() {
        return schoolOpeningDetail;
    }

    public void setSchoolOpeningDetail(School schoolOpeningDetail) {
        this.schoolOpeningDetail = schoolOpeningDetail;
    }
}
