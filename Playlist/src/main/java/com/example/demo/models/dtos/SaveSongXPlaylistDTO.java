package com.example.demo.models.dtos;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SaveSongXPlaylistDTO {
	@NotEmpty
	private LocalDateTime dateAdded;
	
	
}
