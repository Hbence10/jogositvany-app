package csapat.DrivingLicenseAppAPI.config.security.JWT;

import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import csapat.DrivingLicenseAppAPI.config.security.JWT.RefreshToken.RefreshToken;
import csapat.DrivingLicenseAppAPI.entity.Users;
import csapat.DrivingLicenseAppAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;


import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtUtilsService {

    private final UserRepository userRepository;
    private static final String AUTH = "auth";
    private final JwtPropertyConfiguration jwtProperties;
    private final ObjectMapper mapper;

    public String createJwtToken(UserDetails principal) {
        System.out.println(principal.getUsername());
        Users loggedUsers = userRepository.findByEmail(principal.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        System.out.println(jwtProperties.getIssuer());

        return JWT.create()
                .withSubject(loggedUsers.getEmail())
                .withArrayClaim(AUTH, principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new))
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getExpiredTime()))
                .withIssuer(jwtProperties.getIssuer())
                .sign(Algorithm.HMAC256(jwtProperties.getSecret()));
    }

    public UserDetails parseJwt(String jwtToken) {
        DecodedJWT jwt = JWT.require(Algorithm.HMAC256(jwtProperties.getSecret())).withIssuer(jwtProperties.getIssuer()).build().verify(jwtToken);
        return new User(jwt.getSubject(), "dummy", jwt.getClaim(AUTH).asList(String.class).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    }

    public String regenerateJwtToken(String refreshTokenValue) {
        if (refreshTokenValue != null) {
            byte[] decodedBytes = Base64.getUrlDecoder().decode(refreshTokenValue);
            String refreshTokenString = new String(decodedBytes, StandardCharsets.UTF_8);

            try {
                RefreshToken refreshToken = mapper.readValue(refreshTokenString, RefreshToken.class);
                if (!refreshToken.getExpiredDate().isBefore(Instant.now())) {

                    Users loggedUser = userRepository.findByEmail(refreshToken.getEmail()).orElse(null);
                    if (loggedUser != null && !loggedUser.getIsDeleted()) {
                        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(loggedUser.getRole().getName()));
                        String newJwt = createJwtToken(new User(loggedUser.getEmail(), loggedUser.getPassword(), authorities));
                        return newJwt;
                    }
                }
            } catch (JsonProcessingException e) {
                return null;
            }
        }

        return null;
    }
}
