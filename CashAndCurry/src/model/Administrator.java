package model;

import java.time.LocalDate;

import sun.util.calendar.BaseCalendar.Date;

public class Administrator extends GenericUser {

	public Administrator() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Administrator(String username, String password, String name, String lastname, String gender,
			String dateOfBirth) {
		super(username, password, name, lastname, gender, dateOfBirth);
		// TODO Auto-generated constructor stub
	}

}
