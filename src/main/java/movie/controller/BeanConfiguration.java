package movie.controller;

import movie.beans.Employee;
import movie.beans.Member;
import movie.beans.Movie;
import movie.beans.Payment;

public class BeanConfiguration {
	
	public Member member() {
		Member bean = new Member();
		return bean;
		}
	
	public Movie movie() {
		Movie bean = new Movie();
		return bean;
		}

	public Payment payment() {
		Payment bean = new Payment();
		return bean;
	}
	
	public Employee employee() {
		Employee bean = new Employee();
		return bean;
	}
}
