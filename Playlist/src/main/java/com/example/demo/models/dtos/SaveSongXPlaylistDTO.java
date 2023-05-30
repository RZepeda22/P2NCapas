package com.example.demo.models.dtos;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SaveSongXPlaylistDTO {
	@NotEmpty
	private LocalDateTime dateAdded;
	
	
}
