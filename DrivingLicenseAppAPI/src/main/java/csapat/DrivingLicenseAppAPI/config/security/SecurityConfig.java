package csapat.DrivingLicenseAppAPI.config.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));        //A tamogatott origineket adjuk meg
                        config.setAllowedMethods(Collections.singletonList("*"));                            //A tamogatott http verbeket adjuk meg
                        config.setAllowCredentials(true);                                                    //A cookiekat fogadjuk
                        config.setAllowedHeaders(Collections.singletonList("*"));                            //A http headerek adjuk meg
                        config.setExposedHeaders(Arrays.asList("Authorization", "Access-Control-Expose-Headers"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                }))
                .authorizeHttpRequests((requests) -> requests.anyRequest().permitAll())
                .formLogin(Customizer.withDefaults())
                .csrf(crs -> crs.disable())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder(16, 32, 1, 1 << 12, 3);
    }

    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }
}
