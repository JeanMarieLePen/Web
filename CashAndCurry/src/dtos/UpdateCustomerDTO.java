package dtos;

import model.Customer;

public class UpdateCustomerDTO extends Customer {

	private String oldPassword;

	public UpdateCustomerDTO() {
		super();
	}

	public UpdateCustomerDTO(String oldPassword) {
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
