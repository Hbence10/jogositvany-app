package csapat.DrivingLicenseAppAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class ProfileCard {

    private Long id;
    private String name;
    private String imagePath;
    private Long userId;

    public ProfileCard(Long id, String name, String imagePath, Long userId) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
