package csapat.DrivingLicenseAppAPI.config.security.JWT;

import com.auth0.jwt.algorithms.Algorithm;
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


import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtUtilsService {

    private final UserRepository userRepository;
    private static final String AUTH = "auth";

    public String createJwtToken(UserDetails principal) {
        Users loggedUsers = userRepository.findByEmail(principal.getUsername()).orElseThrow(() -> new UsernameNotFoundException(""));
        List<Map<String, String>> subalterns = new ArrayList<>();
        Map<String, String> superiorDetails = new HashMap<>();

        return JWT.create()
                .withSubject(loggedUsers.getEmail())
                .withArrayClaim(AUTH, principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new))
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(2)))
                .withIssuer("Feluton")
                .sign(Algorithm.HMAC256("ZlM@j<?KSh>[KZomr_gd]x#pF}s.VjX:P4[XX__wk|*\n"));
    }

    public UserDetails parseJwt(String jwtToken) {
        DecodedJWT jwt = JWT.require(Algorithm.HMAC256("ZlM@j<?KSh>[KZomr_gd]x#pF}s.VjX:P4[XX__wk|*\n")).withIssuer("Feluton").build().verify(jwtToken);
        return new User(jwt.getSubject(), "dummy", jwt.getClaim(AUTH).asList(String.class).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    }
}
