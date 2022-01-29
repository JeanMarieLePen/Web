package dtos;

import model.Administrator;

public class UpdateAdminDTO extends Administrator {
	private String oldPassword;

	public UpdateAdminDTO() {
		super();
	}

	public UpdateAdminDTO(String oldPassword) {
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
