package ua.edu.ukma.hibskyi.messenger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// TODO:
//  - add thymeleaf and views controllers
//  - improve security and validation (authorisation error and validation; validation for password)
//  - mergers for updates
//  - register page

@SpringBootApplication
public class MessengerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessengerApplication.class, args);
    }

}
