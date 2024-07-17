package mackb.nl.mackbpage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:hidden-application.properties")  // Tells Spring to load the properties from the hidden-application file. This will contain server info.
public class MackbPageApplication {

    public static void main(String[] args) {
        SpringApplication.run(MackbPageApplication.class, args);
    }
}