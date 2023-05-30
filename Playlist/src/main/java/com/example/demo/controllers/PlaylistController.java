package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.dtos.SavePlaylistDTO;
import com.example.demo.models.entities.Playlist;
import com.example.demo.models.entities.Song;
import com.example.demo.models.entities.User;
import com.example.demo.services.PlaylistService;
import com.example.demo.services.SongService;
import com.example.demo.services.SongXPlaylistService;
import com.example.demo.services.UserService;
import com.example.demo.utils.ErrorHandlers;

import jakarta.validation.Valid;

@RestController
@RequestMapping("")
public class PlaylistController {
	
	@Autowired
	PlaylistService playlistService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	SongService songService;
	
	@Autowired
	SongXPlaylistService songxplayService;
	
	@Autowired
	private ErrorHandlers errorHandler;
	
	@PostMapping("/playlist")
	public ResponseEntity<?> savePlaylist(@ModelAttribute @Valid SavePlaylistDTO info, BindingResult validations){
		if(validations.hasErrors()) {
			return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
		}
		User user = userService.getUserByIdentifier(info.getIdentifier());
		if(user == null) {
			return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
		}
		
		
		try {
			playlistService.save(info, user);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>("Saved Playlist", HttpStatus.OK);
	}
	
	@PostMapping("/playlist/{code}")
	public ResponseEntity<?> saveSongInPlaylist(@PathVariable(name = "code") String playListCode, String songCode){
		Song songSearch = songService.findOneByCode(songCode);
		Playlist playlistSearch = playlistService.findOneByCode(playListCode);
		if(songSearch == null || playlistSearch == null) {
			return new ResponseEntity<>("Song or playlist not found", HttpStatus.NOT_FOUND);
		}
		
	}

}
