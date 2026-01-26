package csapat.DrivingLicenseAppAPI.config.security.JWT;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "jwt")
@Component
@Getter
@Setter
public class JwtPropertyConfiguration {
    private String secret;
    private Long expiredTime;
    private String issuer;
}
