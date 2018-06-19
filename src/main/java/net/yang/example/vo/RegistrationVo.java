package net.yang.example.vo;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class RegistrationVo {

	@NotEmpty
	@Size(max=255)
	private String id;
	
	@Email
	@NotEmpty
	private String email;
	
	@NotEmpty
	@Size(max=255)
	private String password;
	
	@NotEmpty
	@Size(max=255)
	private String passwordConfirm;
	
	@AssertTrue(message="{validation.custom.message.passwordConfirmSame}")
	public boolean isPasswordConfirmSame() {
		if(password != null && passwordConfirm != null) {
			return password.equals(passwordConfirm);
		}
		return true;
	}
}
