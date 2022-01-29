package dtos;

import model.Manager;

public class UpdateManagerDTO extends Manager{
	private String oldPassword;

	public UpdateManagerDTO() {
		super();
	}

	public UpdateManagerDTO(String oldPassword) {
		super();
		this.oldPassword = oldPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	
}
