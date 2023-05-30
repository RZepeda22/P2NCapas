package com.example.demo.services;

import java.util.List;

import com.example.demo.models.dtos.GetPlaylistDataDTO;
import com.example.demo.models.dtos.SavePlaylistDTO;
import com.example.demo.models.dtos.ShowSongsDTO;
import com.example.demo.models.entities.Playlist;
import com.example.demo.models.entities.SongXPlaylist;
import com.example.demo.models.entities.User;

public interface PlaylistService {
	//Save
	void save(SavePlaylistDTO data, User user) throws Exception;
	
	
	//FindAll
	List<Playlist> findAll();
	List<Playlist> findAllByCode(String code);
	List<ShowSongsDTO> getSongsInPlaylistWithDatesfind(Playlist playlist);
	List<GetPlaylistDataDTO> findByTitleContainingIgnoreCase(String title);
	List<GetPlaylistDataDTO> findByTitleAndAlsoIdentifier(User user, String title);
	List<GetPlaylistDataDTO> findByUserOnly(User user);
	
	//Find
	Playlist findOneByCode(String code);
	
}
