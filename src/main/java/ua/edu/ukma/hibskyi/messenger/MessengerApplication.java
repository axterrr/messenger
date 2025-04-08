package ua.edu.ukma.hibskyi.messenger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// TODO:
//  - add thymeleaf and views controllers
//  - update security
//  - mergers for updates
//  - done mappers / fix potential problems with lazy initialization

@SpringBootApplication
public class MessengerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessengerApplication.class, args);
    }

}
