package movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import movie.beans.Member;
import movie.controller.BeanConfiguration;
import movie.repository.MemberRepository;

@SpringBootApplication
public class MovieRentalApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieRentalApplication.class, args);
	}
	
}
