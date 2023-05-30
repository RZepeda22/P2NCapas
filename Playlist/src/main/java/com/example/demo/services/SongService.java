package com.example.demo.services;

import java.util.List;
import com.example.demo.models.dtos.SaveSongDTO;
import com.example.demo.models.dtos.SongDataParseDTO;
import com.example.demo.models.entities.Song;

public interface SongService {
	//Save
	void save(SaveSongDTO data) throws Exception;
	
	//Delete
	void deleteOneByCode(String code);
	void deleteOneByTitle(String title);
	void deleteOneByDuration(int duration);
	
	//FindAll
	List<SongDataParseDTO> findAll();
	List<Song> findAllByCode(String code);
	List<Song> findAllByTitle(String title);
	List<Song> findAllByDuration(int duration);
	List<SongDataParseDTO> findAllByParcialTitle(String title);
	
	//Find
	Song findOneByCode(String code);
	Song findOneByTitle(String title);
}

