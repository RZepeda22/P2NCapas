package com.example.demo.services;

import java.util.List;
import com.example.demo.models.dtos.SavePlaylistDTO;
import com.example.demo.models.entities.Playlist;
import com.example.demo.models.entities.User;

public interface PlaylistService {
	//Save
	void save(SavePlaylistDTO data, User user) throws Exception;
	
	//Delete
	void deleteOneByCode(String code);
	void deleteOneByTitle(String title);
	void deleteOneByDescription(String description);
	void deleteOneByUser(String userCode);
	
	//FindAll
	List<Playlist> findAll();
	List<Playlist> findAllByCode(String code);
	List<Playlist> findAllByTitle(String title);
	List<Playlist> findAllByUser(String code);
	
	//Find
	Playlist findOneByCode(String code);
	Playlist findOneByTitle(String title);
	Playlist findOneByUser(String code);
	
}
