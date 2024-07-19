package mackb.nl.mackbpage.business.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

// Security class to configure Spring Security
@Configuration
@EnableWebSecurity                                                                                  // Enables Spring Security's web security support.
public class SecurityConfig {

    @Bean                                                                                           // Method is a bean producer.
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {            // Underneath here I'll configure the security filter for the webpages.
        http
//                .csrf().disable()  //TEST Disable CSRF protection
                .authorizeHttpRequests(authorize -> authorize                                       // Starts configuration for request authorization.
                        .requestMatchers("/", "/index", "/register","/login", "/css/**").permitAll()    // Any page added here can be accessed by anyone
                        .anyRequest().authenticated()                                               // All pages that are not added in the line above will need authentication
                )
                .formLogin(form -> form                                                             // Configures form based login
                        .loginPage("/login")                                                        // Specifies the login page to be used
                        .permitAll()                                                                // Allows everyone to see this page
                        .defaultSuccessUrl("/tools", true)                // Redirects to success page after successful login.
                );
        return http.build();                                                                        // Builds and returns the securityFilterChain.
    }
}