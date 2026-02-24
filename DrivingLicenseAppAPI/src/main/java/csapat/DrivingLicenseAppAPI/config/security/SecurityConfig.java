package csapat.DrivingLicenseAppAPI.config.security;

import csapat.DrivingLicenseAppAPI.config.security.JWT.JWTGeneratorFilter;
import csapat.DrivingLicenseAppAPI.config.security.JWT.JWTValidatorFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTGeneratorFilter jwtGeneratorFilter;
    private final JWTValidatorFilter jwtValidatorFilter;
    private final UserSetter userSetter;

    @Profile("prod")
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("Security enabled!");
        http
                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));        //A tamogatott origineket adjuk meg
                        config.setAllowedMethods(Collections.singletonList("*"));                            //A tamogatott http verbeket adjuk meg
                        config.setAllowCredentials(true);                                                    //A cookiekat fogadjuk
                        config.setAllowedHeaders(List.of("*"));                            //A http headerek adjuk meg
                        config.setExposedHeaders(Arrays.asList("Authorization", "refreshToken", "Bearer ", "PageNumber"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                }))
                .authorizeHttpRequests((requests) -> requests
                        //DrivingLessonController:
                        .requestMatchers("/drivingLesson/categories/school/**").permitAll()
                        .requestMatchers("/drivingLesson/cancel/**").hasRole("instructor")
                        .requestMatchers(HttpMethod.PUT, "/drivingLesson/*").hasRole("instructor")
                        .requestMatchers("/drivingLesson/reservedHour").hasAnyRole("instructor", "student")
                        .requestMatchers(HttpMethod.GET, "/drivingLesson/*").hasAnyRole("instructor", "student")
                        //InstructorController:
                        .requestMatchers("/instructor/handleJoinRequest").hasRole("instructor")
                        .requestMatchers("/instructor/*/joinRequest").hasRole("instructor")
                        .requestMatchers("/instructor/*/drivingLessonRequest").hasRole("instructor")
                        .requestMatchers(HttpMethod.PUT, "/instructor/*").hasRole("instructor")
                        .requestMatchers("/instructor/handleDrivingLessonRequest").hasRole("instructor")
                        .requestMatchers("/instructor").authenticated()
                        .requestMatchers(HttpMethod.GET,"/instructor/*").authenticated()
                        .requestMatchers("/instructor/*/students").hasRole("instructor")
                        .requestMatchers("/instructor/kickout").hasRole("instructor")
                        //OtherStuffController:
                        .requestMatchers("/vehicleType", "/town", "/status", "/paymentMethod", "/fuelType", "/education", "/email").permitAll()
                        //RequestController:
                        .requestMatchers("/request/school").hasAnyRole("instructor", "user")
                        .requestMatchers("/request/instructor").hasRole("student")
                        .requestMatchers("/request/drivingLesson").hasRole("student")
                        //ReviewController:
                        .requestMatchers(HttpMethod.GET, "/review").authenticated()
                        .requestMatchers(HttpMethod.POST, "/review").hasRole("student")
                        .requestMatchers("/review/*").hasRole("student")
                        //SchoolController:
                        .requestMatchers(HttpMethod.POST, "/school/*/joinRequest").hasAnyRole("school_admin", "school_owner")
                        .requestMatchers(HttpMethod.PUT, "/school/*").hasAnyRole("school_admin", "school_owner")
                        .requestMatchers("/school/*/coverImg").hasAnyRole("school_admin", "school_owner")
                        .requestMatchers("/school/*/openingDetails").hasAnyRole("school_admin", "school_owner")
                        .requestMatchers(HttpMethod.GET, "/school/*/joinRequest").hasAnyRole("school_admin", "school_owner")
                        .requestMatchers(HttpMethod.DELETE, "/school/*").hasAnyRole("administrator", "school_owner")
                        .requestMatchers("/school/search").authenticated()
                        .requestMatchers(HttpMethod.GET, "/school/*").authenticated()
                        .requestMatchers(HttpMethod.POST, "/school").hasRole("administrator")
                        .requestMatchers("/school/users").hasAnyRole("school_admin", "school_owner")
                        .requestMatchers("/school/kickout").hasAnyRole("school_admin", "school_owner")
                        .requestMatchers(HttpMethod.GET, "/school").hasRole("administrator")
                        .requestMatchers("/school/admin").hasRole("school_owner")

                        //StudentController:
                        //UserController
                        .requestMatchers("/users/login", "/users/register/**", "/users/getVerificationCode", "/users/checkVerificationCode", "/users/passwordReset").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/user/*").authenticated()
                        .requestMatchers("/users/pfp/*").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/users/*").authenticated()
                        .requestMatchers(HttpMethod.GET, "/users/*").authenticated()
                        .requestMatchers("/users").hasRole("administrator")

                        //egyeb:
                        .requestMatchers("/pfp/**").permitAll()
                        .requestMatchers("/coverImages/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs*/**").permitAll()
                        .anyRequest().authenticated()

                )
                .authenticationProvider(authProvider())
                .addFilterAfter(jwtGeneratorFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(jwtValidatorFilter, BasicAuthenticationFilter.class)
//                .formLogin(Customizer.withDefaults())
                .formLogin(f -> f.disable())
                .csrf(crs -> crs.disable())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Profile({"dev", "test"})
    @Bean
    SecurityFilterChain securityFilterChainDev(HttpSecurity http) throws Exception {
        System.out.println("Security disabled");
        http
                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));        //A tamogatott origineket adjuk meg
                        config.setAllowedMethods(Collections.singletonList("*"));                            //A tamogatott http verbeket adjuk meg
                        config.setAllowCredentials(true);                                                    //A cookiekat fogadjuk
                        config.setAllowedHeaders(Collections.singletonList("*"));                            //A http headerek adjuk meg
                        config.setExposedHeaders(Arrays.asList("Authorization", "refreshToken", "Bearer ", "PageNumber"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                }))
                .authorizeHttpRequests((requests) ->
                        requests.anyRequest().permitAll()
                )
//                .formLogin(Customizer.withDefaults())
                .formLogin(f -> f.disable())
                .csrf(crs -> crs.disable())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder(16, 32, 1, 1 << 12, 3);
    }

    @Bean
    @Profile("prod")
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }

    @Bean
    @Profile("prod")
    AuthenticationProvider authProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userSetter);
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }
}
