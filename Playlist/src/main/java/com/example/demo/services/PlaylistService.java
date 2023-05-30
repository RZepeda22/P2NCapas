package com.example.demo.services;

import java.util.List;
import com.example.demo.models.dtos.SavePlaylistDTO;
import com.example.demo.models.entities.Playlist;
import com.example.demo.models.entities.User;

public interface PlaylistService {
	//Save
	void save(SavePlaylistDTO data, User user) throws Exception;
	
	
	//FindAll
	List<Playlist> findAll();
	List<Playlist> findAllByCode(String code);
	
	//Find
	Playlist findOneByCode(String code);
	
}
