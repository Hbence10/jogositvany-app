package csapat.DrivingLicenseAppAPI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true)
public class DrivingLicenseAppApiApplication implements CommandLineRunner {

    @Value("${spring.datasource.url:NOT_FOUND}")
    private String url;

    @Value("${spring.datasource.username:NOT_FOUND}")
    private String username;

    @Override
    public void run(String... args) {
        System.out.println("DB URL = [" + url + "]");
        System.out.println("DB USER = [" + username + "]");
    }

    public static void main(String[] args) {
        SpringApplication.run(DrivingLicenseAppApiApplication.class, args);
    }
}
