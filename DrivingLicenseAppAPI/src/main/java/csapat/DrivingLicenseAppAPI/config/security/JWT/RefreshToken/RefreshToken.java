package csapat.DrivingLicenseAppAPI.config.security.JWT.RefreshToken;

import lombok.*;

import java.time.Instant;


@NoArgsConstructor
public class RefreshToken {

    private Long id;
    private String email;
    private String tokenValue;
    private Instant expiredDate;

    public RefreshToken(String email, String tokenValue, Instant expiredDate) {
        this.email = email;
        this.tokenValue = tokenValue;
        this.expiredDate = expiredDate;
    }

    public RefreshToken(Long id, String email, String tokenValue, Instant expiredDate) {
        this.id = id;
        this.email = email;
        this.tokenValue = tokenValue;
        this.expiredDate = expiredDate;
    }

    public RefreshToken() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public Instant getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Instant expiredDate) {
        this.expiredDate = expiredDate;
    }
}
