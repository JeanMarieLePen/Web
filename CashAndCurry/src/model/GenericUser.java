package model;

import java.time.LocalDate;

import sun.util.calendar.BaseCalendar.Date;
import sun.util.calendar.BaseCalendar;

public class GenericUser {

	private int id;
	private boolean obrisan = false;
	private String username;
	private String password;
	private String name;
	private String lastname;
	private String gender;
	private String dateOfBirth;
	
	public GenericUser() {
		
	}
	public GenericUser(boolean obrisan, String username, String password, String name, String lastname, String gender,
			String dateOfBirth) {
		super();
		this.obrisan = obrisan;
		this.username = username;
		this.password = password;
		this.name = name;
		this.lastname = lastname;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
	}
//	public GenericUser(String username, String password, String name, String lastname, String gender,
//			String dateOfBirth) {
//		super();
//		this.username = username;
//		this.password = password;
//		this.name = name;
//		this.lastname = lastname;
//		this.gender = gender;
//		this.dateOfBirth = dateOfBirth;
//	}
//	
//	public String getUsername() {
//		return username;
//	}
//	public void setUsername(String username) {
//		this.username = username;
//	}
//	public String getPassword() {
//		return password;
//	}
//	public void setPassword(String password) {
//		this.password = password;
//	}
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}
//	public String getLastname() {
//		return lastname;
//	}
//	public void setLastname(String lastname) {
//		this.lastname = lastname;
//	}
//	public String getGender() {
//		return gender;
//	}
//	public void setGender(String gender) {
//		this.gender = gender;
//	}
//	public String getDateOfBirth() {
//		return dateOfBirth;
//	}
//	public void setDateOfBirth(String dateOfBirth) {
//		this.dateOfBirth = dateOfBirth;
//	}
//	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isObrisan() {
		return obrisan;
	}

	public void setObrisan(boolean obrisan) {
		this.obrisan = obrisan;
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

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
}
