package com.example.demo.models.dtos;

import java.sql.Timestamp;

import com.example.demo.models.entities.Playlist;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShowSongsDTO {
	private SongDataParseDTO song;
	private Playlist playlist;
	private Timestamp dateAdded;
}
