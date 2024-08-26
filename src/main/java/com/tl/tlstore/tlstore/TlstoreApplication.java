package com.tl.tlstore.tlstore;

import com.tl.tlstore.tlstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TlstoreApplication {

	public static void main(String[] args) {
		// Run the application
		SpringApplication.run(TlstoreApplication.class, args);
	}

}
