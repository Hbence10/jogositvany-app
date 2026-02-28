package csapat.DrivingLicenseAppAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProfileCard {

    private Long id;
    private String name;
    private String imagePath;
    private Long userId;
}
