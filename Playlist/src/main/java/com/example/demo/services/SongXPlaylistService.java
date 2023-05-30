package com.example.demo.services;

import java.time.LocalDateTime;
import java.util.List;
import com.example.demo.models.dtos.SaveSongXPlaylistDTO;
import com.example.demo.models.entities.Playlist;
import com.example.demo.models.entities.Song;
import com.example.demo.models.entities.SongXPlaylist;

public interface SongXPlaylistService {
	//Save
	void save(LocalDateTime date,Playlist playlist, Song song) throws Exception;
	
}
