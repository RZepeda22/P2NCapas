package com.example.demo.services;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.models.dtos.CodeDataDTO;
import com.example.demo.models.entities.Playlist;
import com.example.demo.models.entities.Song;

public interface SongXPlaylistService {
	//Save
	void save(LocalDateTime date,Playlist playlist, Song song) throws Exception;
	
	//FindAll
	List<CodeDataDTO> findAllSongCodeByPlaylist(String code);
	
	boolean existSongInPlaylist(String playlist, String song);
}
