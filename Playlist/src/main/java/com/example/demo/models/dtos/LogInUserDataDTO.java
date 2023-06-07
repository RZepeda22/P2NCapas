package com.example.demo.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogInUserDataDTO {
	
	@NotEmpty
	private String identifier;
	
	private String token;
	
	@NotEmpty
	private String password;

}
