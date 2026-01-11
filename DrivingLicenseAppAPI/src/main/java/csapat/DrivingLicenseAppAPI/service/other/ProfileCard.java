package csapat.DrivingLicenseAppAPI.service.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProfileCard {

    private Integer id;
    private String name;
    private String imagePath;
    private Integer userId;
}
