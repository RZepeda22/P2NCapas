package com.example.demo.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlaylistInfoDTO {
	@NotEmpty
	private String code;
	
	@NotEmpty
	private String title;
	
	@NotEmpty
	private String description;
}
