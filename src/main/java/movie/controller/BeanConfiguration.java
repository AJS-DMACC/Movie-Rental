package movie.controller;

import org.springframework.context.annotation.Bean;

import movie.beans.Member;
import movie.beans.Rental;

public class BeanConfiguration {
	
	public Member member() {
		Member bean = new Member();
		return bean;
		}

	@Bean
	public Rental rental() {
		Rental bean = new Rental();
		return bean;	
	}
	
}
