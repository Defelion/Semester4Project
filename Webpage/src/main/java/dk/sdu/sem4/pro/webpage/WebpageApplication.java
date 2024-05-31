package dk.sdu.sem4.pro.webpage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class WebpageApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {

        SpringApplication.run(WebpageApplication.class, args);
    }

}
