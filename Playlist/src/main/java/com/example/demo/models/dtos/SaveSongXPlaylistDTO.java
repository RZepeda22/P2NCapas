package com.example.demo.models.dtos;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import com.example.demo.models.entities.Playlist;
import com.example.demo.models.entities.Song;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveSongXPlaylistDTO {
	
	@NotEmpty
	private Song song;
	
	@NotEmpty
	private Playlist playlist;
	
	@NotEmpty
	private Timestamp dateAdded;
	
	
}
