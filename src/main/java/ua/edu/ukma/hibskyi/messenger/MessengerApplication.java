package ua.edu.ukma.hibskyi.messenger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// TODO:
//  - add thymeleaf and views controllers
//  - authorisation error and validation
//  - mergers for updates
//  - AJAX

@SpringBootApplication
public class MessengerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessengerApplication.class, args);
    }

}
