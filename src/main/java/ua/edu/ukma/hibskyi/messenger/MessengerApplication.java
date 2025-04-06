package ua.edu.ukma.hibskyi.messenger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//TODO: add validation
//TODO: add exceptions
//TODO: add exception handling
//TODO: add security

// TODO:
//  - make email nullable and add username
//  - change UUID to other type
//  - make BaseService


@SpringBootApplication
public class MessengerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessengerApplication.class, args);
    }

}
