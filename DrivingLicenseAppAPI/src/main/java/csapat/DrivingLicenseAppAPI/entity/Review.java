package csapat.DrivingLicenseAppAPI.entity;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "text")
    @NotNull
    private String text;

    @Column(name = "created_at")
    @NotNull
    private LocalDate createdAt;

    @Column(name = "rating")
    @NotNull
    private float rating;

    //Kapcsolatok:
    @ManyToOne(cascade = {})
    @JoinColumn(name = "author_id")
    private Students reviewAuthor;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "about_id", insertable = false, updatable = false)
    private Instructors aboutInstructor;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "about_id", insertable = false, updatable = false)
    private School aboutSchool;

    //Constructorok
    public Review() {
    }

    public Review(String text, LocalDate createdAt, float rating) {
        this.text = text;
        this.createdAt = createdAt;
        this.rating = rating;
    }

    //Getterek & Setterek:
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
