package csapat.DrivingLicenseAppAPI.config.security.JWT;

import com.fasterxml.jackson.databind.ObjectMapper;
import csapat.DrivingLicenseAppAPI.config.security.JWT.RefreshToken.RefreshToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JWTValidatorFilter extends OncePerRequestFilter {

    private final JwtUtilsService jwtService;
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";
    private final ObjectMapper mapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(AUTHORIZATION);
        System.out.println("header: " + header);
        if (header != null && header.startsWith(BEARER)) {
            String jwt = header.substring(BEARER.length());
            System.out.println(jwt);
            UserDetails principal;
            try {
                principal = jwtService.parseJwt(jwt);
            } catch (Exception e) {
                String newJwt = jwtService.regenerateJwtToken(request.getHeader("refreshToken"));
                principal = jwtService.parseJwt(newJwt);
                response.setHeader("Bearer ", newJwt);
            }

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        System.out.println(request.getServletPath());
        ArrayList<String> allowedUrlPaths = new ArrayList<String>(Arrays.asList(
                "/users/register/student",
                "/users/register/instructor",
                "/users/login"
        ));

        return allowedUrlPaths.contains(request.getServletPath());
    }
}
