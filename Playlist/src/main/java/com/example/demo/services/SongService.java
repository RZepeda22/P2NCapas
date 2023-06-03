package com.example.demo.services;

import java.util.List;

import com.example.demo.models.dtos.CodeDataDTO;
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
	List<SongDataParseDTO> findAllByParcialTitle(String title);
	List<SongDataParseDTO> findAllSongByListCode(List<CodeDataDTO> songsCodes);
	
	
	//Find
	Song findOneByCode(String code);
	Song findOneByTitle(String title);
	Song getSongByIdentifier(String identifier);
}

