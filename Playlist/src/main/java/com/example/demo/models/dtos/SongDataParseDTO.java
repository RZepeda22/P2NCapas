package com.example.demo.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SongDataParseDTO {
	@NotEmpty
	private String title;
	
	@NotEmpty
	private String duration;
	
	
}
