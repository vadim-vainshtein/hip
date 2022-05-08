package ru.hip-spb.model;

public class User {

	private String login;
	private String passString;
	
	public User(String login, String passString) {

		this.login = login;
		this.passString = passString;
	}
	
	public String getLogin() {
		return login;
	}
	
	public String getPassString() {
		return passString;
	}
}
