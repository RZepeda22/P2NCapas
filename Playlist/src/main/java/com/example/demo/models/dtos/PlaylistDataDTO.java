package com.example.demo.models.dtos;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class PlaylistDataDTO {
	@NotEmpty
	private String title;
	
	@NotEmpty
	private String description;
	
	@NotEmpty
	private String durationPlaylist;
	
	@NotEmpty
	private List<SongDataParseDTO> songs;
	
	
}
