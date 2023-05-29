package com.example.demo.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserDataDTO {
	
	@NotEmpty
	private String username;
	
	@NotEmpty
	@Email
	private String email;
	
	@NotEmpty
	private String password;
}