package project.runner;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class RunnerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RunnerApplication.class, args);
		//TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kathmandu"));
		System.out.println("Hello there...");
	}

}
