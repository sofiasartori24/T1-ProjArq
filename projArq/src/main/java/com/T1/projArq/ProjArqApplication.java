package com.T1.projArq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ProjArqApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjArqApplication.class, args);
	}

	@RestController
    class HelloWorldController {
        @GetMapping("/hello")
        public String hello() {
            return "Hello World - Sofia";
        }
    }

}
