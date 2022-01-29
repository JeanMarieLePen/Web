package dtos;

import model.DeliveryMan;

public class UpdateDeliveryManDTO extends DeliveryMan{
	private String oldPassword;

	public UpdateDeliveryManDTO(String oldPassword) {
		super();
		this.oldPassword = oldPassword;
	}

	public UpdateDeliveryManDTO() {
		super();
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	
}
