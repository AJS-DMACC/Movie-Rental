package movie.controller;

import movie.beans.Member;

public class BeanConfiguration {
	
	public Member member() {
		Member bean = new Member( );
		return bean;
		}

}
