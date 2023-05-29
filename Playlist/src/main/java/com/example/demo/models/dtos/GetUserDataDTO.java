package com.example.demo.models.dtos;

import lombok.Data;

@Data
public class GetUserDataDTO {
	private String username;
	private String email;
	private String password;
}
