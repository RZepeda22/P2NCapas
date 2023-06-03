package com.example.demo.services;

import java.util.List;

import com.example.demo.models.dtos.PlaylistInfoDTO;
import com.example.demo.models.dtos.SavePlaylistDTO;
import com.example.demo.models.entities.Playlist;
import com.example.demo.models.entities.User;


public interface PlaylistService {
	//Save
	void save(SavePlaylistDTO data, User user) throws Exception;
	
	//FindAll
	List<Playlist> findAll();
	List<Playlist> findAllByCode(String code);
	List<PlaylistInfoDTO> findAllByUser(String code);
	List<PlaylistInfoDTO> findAllByParcialTitle(String title);
	List<PlaylistInfoDTO> findAllByParcialTitleAndUser(String code, String title);
	//List<ShowSongsDTO> getSongsInPlaylistWithDatesfind(Playlist playlist);

	//Find
	Playlist findOneByCode(String code);
	Playlist findOneByTitle(String title);
	Playlist findPlaylistByTitlePlaylistAndUser(String titlePlaylist, User user);

	//Data
	String calculateDurationOfPlaylist(String code);
	
	//Verification
	boolean existPlaylistInUser(String titlePlaylist, User user);
	//boolean existPlaylistInEmail(String titlePlaylist, String email);
	
	Playlist getPlaylistByIdentifier(String identifier);
	
}
