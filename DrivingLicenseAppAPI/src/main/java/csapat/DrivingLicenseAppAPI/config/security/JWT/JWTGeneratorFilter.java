package csapat.DrivingLicenseAppAPI.config.security.JWT;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.impl.TextCodec;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

public class JWTGeneratorFilter extends OncePerRequestFilter {
    private final String secret = "0fd1009ab660c931cb8030c56c7d7ca6fa2bd2b0ae8752a92c6f7b6af2a45b865a66c307";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("ad");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null){
            Environment env = getEnvironment();
            String secret = env.getProperty("JWT_SECRET", this.secret);
            SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
            String jwt = Jwts.builder()
                    .issuer("Feluton")
                    .subject("JWT_Token")
                    .claim("id", Integer.valueOf(authentication.getName()))
                    .claim("authorities", authentication.getAuthorities()
                            .stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                    .issuedAt(new Date()).expiration(new Date((new Date()).getTime() + 30000000))
                    .signWith(SignatureAlgorithm.HS256, TextCodec.BASE64.encode(secret))
                    .compact();
            response.setHeader("Authorization", jwt);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/users/login");
    }
}
