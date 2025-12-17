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
import java.util.List;

@Entity
@Table(name = "payment_method")
@Getter
@Setter
@NoArgsConstructor
@ToString
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getAllPayMethod", procedureName = "getAllPayMethod", resultClasses = PaymentMethod.class),
        @NamedStoredProcedureQuery(name = "getPaymentMethod", procedureName = "getPaymentMethod", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Integer.class, mode = ParameterMode.IN)
        }, resultClasses = PaymentMethod.class),
        @NamedStoredProcedureQuery(name = "deletePaymentMethod", procedureName = "deletePaymentMethod", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Integer.class, mode = ParameterMode.IN)
        }, resultClasses = String.class)
})
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    @Size(max = 100)
    @NotNull
    private String name;

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
            mappedBy = "paymentMethod",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JsonIgnore
    private List<DrivingLessons> drivingLessonsList;

    //Constructorok:
    public PaymentMethod(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
