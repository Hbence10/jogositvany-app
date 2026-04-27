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
@NoArgsConstructor
@ToString
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getAllReservedDate", procedureName = "getAllReservedDate", resultClasses = ReservedDate.class),
        @NamedStoredProcedureQuery(name = "getReservedDate", procedureName = "getReservedDate", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Long.class, mode = ParameterMode.IN)
        }, resultClasses = ReservedDate.class),
        @NamedStoredProcedureQuery(name = "deleteReservedDate", procedureName = "deleteReservedDate", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Long.class, mode = ParameterMode.IN)
        }),
        @NamedStoredProcedureQuery(name = "getReservedDateByDate", procedureName = "getReservedDateByDate", parameters = {
                @StoredProcedureParameter(name = "wantedDateIN", type = Date.class, mode = ParameterMode.IN)})
})
public class ReservedDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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
    @OneToMany(mappedBy = "reservedDate", fetch = FetchType.LAZY)
    private List<ReservedHour> reservedHourList;

    //Constructorok:
    public ReservedDate(Date date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getFull() {
        return isFull;
    }

    public void setFull(Boolean full) {
        isFull = full;
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

    public List<ReservedHour> getReservedHourList() {
        return reservedHourList;
    }

    public void setReservedHourList(List<ReservedHour> reservedHourList) {
        this.reservedHourList = reservedHourList;
    }
}
