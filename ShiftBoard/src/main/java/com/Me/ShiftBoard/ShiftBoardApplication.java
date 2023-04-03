package com.Me.ShiftBoard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ShiftBoardApplication {

	public static void main(String[] args) {

		SpringApplication.run(ShiftBoardApplication.class, args);

	}


}
