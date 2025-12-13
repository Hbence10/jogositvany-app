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
@Table(name = "driving_lesson")
@Getter
@Setter
@NoArgsConstructor
@ToString
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getAllDrivingLesson", procedureName = "getAllDrivingLesson", resultClasses = DrivingLessons.class),
        @NamedStoredProcedureQuery(name = "getDrivingLessonByID", procedureName = "getDrivingLessonByID", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Integer.class, mode = ParameterMode.IN)
        }, resultClasses = DrivingLessons.class),
        @NamedStoredProcedureQuery(name = "deleteDrivingLesson", procedureName = "deleteDrivingLesson", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Integer.class, mode = ParameterMode.IN)
        }, resultClasses = String.class)
})
public class DrivingLessons {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "start_km")
    @NotNull
    @Size(max = 7)
    private Integer startKm;

    @Column(name = "end_km")
    @NotNull
    @Size(max = 7)
    private Integer endKm;

    @Column(name = "location")
    @NotNull
    @Size(max = 100)
    private String location;

    @Column(name = "pick_up_place")
    @NotNull
    @Size(max = 100)
    private String pickUpPlace;

    @Column(name = "drop_off_place")
    @NotNull
    @Size(max = 100)
    private String dropOffPlace;

    @Column(name = "lesson_hour_number")
    @NotNull
    private Integer lessonHourNumber;

    @Column(name = "is_paid")
    @NotNull
    private Boolean isPaid = false;

    @Column(name = "is_end")
    @NotNull
    private Boolean isEnd = false;

    @Column(name = "is_cancelled")
    @NotNull
    @JsonIgnore
    private Boolean isCancelled = false;

    @Column(name = "cancelled_at")
    @Null
    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    private Date cancelledAt;

    //Kapcsolatok:
    @ManyToOne(cascade = {})
    @JoinColumn(name = "status_id")
    private Status drivingLessonStatus;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hour_id")
    @JsonIgnoreProperties({})
    private ReservedHour reservedHour;

    @ManyToOne(cascade = {CascadeType.DETACH})
    @JoinColumn(name = "student_id")
    @JsonIgnoreProperties({})
    private Students dstudent;

    @ManyToOne(cascade = {CascadeType.DETACH})
    @JoinColumn(name = "instructor_id")
    private Instructors dinstructor;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "type_id")
    @JsonIgnoreProperties({})
    private DrivingLessonType drivingLessonType;

    public DrivingLessons(int startKm, int endKm, String location, String pickUpPlace, String dropOffPlace, int lessonHourNumber, boolean isPaid) {
        this.startKm = startKm;
        this.endKm = endKm;
        this.location = location;
        this.pickUpPlace = pickUpPlace;
        this.dropOffPlace = dropOffPlace;
        this.lessonHourNumber = lessonHourNumber;
        this.isPaid = isPaid;
    }
}
