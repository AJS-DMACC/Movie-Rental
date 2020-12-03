package movie.controller;

import movie.beans.Member;
import movie.beans.Movie;

public class BeanConfiguration {
	
	public Member member() {
		Member bean = new Member();
		return bean;
		}
	
	public Movie movie() {
		Movie bean = new Movie();
		return bean;
		}

}
