package com.example.waiting_room;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class WaitingRoomApplication {

    public static void main(String[] args) {
        SpringApplication.run(WaitingRoomApplication.class, args);
    }

}
