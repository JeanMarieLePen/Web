package model;

import sun.util.calendar.BaseCalendar.Date;

public class GenericUser {

	
	private String username;
	private String password;
	private String name;
	private String lastname;
	private String gender;
	private Date dateOfBirth;
	
	public GenericUser() {
		
	}
	public GenericUser(String username, String password, String name, String lastname, String gender,
			Date dateOfBirth) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.lastname = lastname;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	
}
