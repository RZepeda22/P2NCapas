package com.example.demo.models.dtos;

import java.util.Date;
import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SaveSongXPlaylistDTO {
	@NotEmpty
	private Date dateAdded;
	
	
}
