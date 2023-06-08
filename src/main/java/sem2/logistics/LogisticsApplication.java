package sem2.logistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// http://localhost:8090/logistics-app/h2-ui
// jdbc:h2:mem:testdb
// select * from reading

@SpringBootApplication
public class LogisticsApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogisticsApplication.class, args);
    }

}