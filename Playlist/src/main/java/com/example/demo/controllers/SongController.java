package com.example.demo.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.dtos.MessageDTO;
import com.example.demo.models.dtos.SongDataParseDTO;
import com.example.demo.services.SongService;

@RestController
@RequestMapping("")
public class SongController {
	@Autowired
	private SongService songService;
	
	
	
	@GetMapping("/song")
	public ResponseEntity<?> findAllSongsAndParcialTitle(String title){
		
		if(title == null) {
			List<SongDataParseDTO> songs = songService.findAll();
			return new ResponseEntity<>(songs, HttpStatus.OK);
		}
		
		List<SongDataParseDTO> foundSongs = songService.findAllByParcialTitle(title);
		
		if(foundSongs == null) {
			return new ResponseEntity<>(new MessageDTO("Songs not found"), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(foundSongs, HttpStatus.OK);
	}
	
}