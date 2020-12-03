package movie.controller;

import movie.beans.Member;
import movie.beans.Employee;

public class BeanConfiguration {
	
	public Member member() {
		Member bean = new Member();
		return bean;
		}

	public Employee employee() {
		Employee bean = new Employee();
		return bean;
		}
}
