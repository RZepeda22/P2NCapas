package com.example.demo.models.dtos;

import java.util.UUID;

import com.example.demo.models.entities.User;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SavePlaylistDTO {
	@NotEmpty
	private String title;
	
	@NotEmpty
	private String description;
	
	
	
}
