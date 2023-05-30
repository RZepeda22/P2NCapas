package com.example.demo.services;

import java.util.List;
import com.example.demo.models.dtos.SaveSongXPlaylistDTO;
import com.example.demo.models.entities.Playlist;
import com.example.demo.models.entities.Song;
import com.example.demo.models.entities.SongXPlaylist;

public interface SongXPlaylistService {
	//Save
	void save(SaveSongXPlaylistDTO data,Playlist playlist, Song song) throws Exception;
	
	//Delete
	void deleteOneByCode(String code);
	void deleteOneBySong(String songCode);
	void deleteOneByPlaylist(String playlistCode);
	void deleteOneByDateAdded(String dateAdded);
	
	//FindAll
	List<SongXPlaylist> findAll();
	List<SongXPlaylist> findAllByCode(String code);
	List<SongXPlaylist> findAllBySong(String songCode);
	List<SongXPlaylist> findAllByPlaylist(String playlistCode);
	List<SongXPlaylist> findAllByDateAdded(String dateAdded);
	
	//Find
	SongXPlaylist findOneByCode(String code);
	SongXPlaylist findOneBySong(String songCode);
	SongXPlaylist findOneByPlaylist(String playlistCode);
	SongXPlaylist findOneByDateAdded(String dateAdded);
	
}
