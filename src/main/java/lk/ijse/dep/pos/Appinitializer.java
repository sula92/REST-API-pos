package lk.ijse.dep.pos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class Appinitializer {

    public static void main(String[] args) {

        SpringApplication.run(Appinitializer.class,args);

    }


}
