package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.dtos.SavePlaylistDTO;
import com.example.demo.models.entities.User;
import com.example.demo.services.PlaylistService;
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
	private ErrorHandlers errorHandler;
	
	@PostMapping("/playlist")
	public ResponseEntity<?> savePlaylist(@ModelAttribute @Valid SavePlaylistDTO info, String identifier, BindingResult validations){
		if(validations.hasErrors()) {
			return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
		}
		User user = userService.getUserByIdentifier(identifier);
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

}
