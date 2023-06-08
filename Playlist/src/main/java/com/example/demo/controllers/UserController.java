package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.dtos.ErrorsDTO;
import com.example.demo.models.dtos.LogInUserDataDTO;
import com.example.demo.models.dtos.MessageDTO;
import com.example.demo.models.dtos.PlaylistInfoDTO;
import com.example.demo.models.dtos.TokenDTO;
import com.example.demo.models.dtos.UserDataDTO;
import com.example.demo.models.entities.Token;
import com.example.demo.models.entities.User;
import com.example.demo.services.JWTTokenProviderService;
import com.example.demo.services.PlaylistService;
import com.example.demo.services.UserService;
import com.example.demo.utils.ErrorHandlers;

import jakarta.validation.Valid;

@RestController
@RequestMapping("")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private PlaylistService playlistService;
	
	@Autowired
	private ErrorHandlers errorHandler;
	
	@Autowired
	private JWTTokenProviderService JWTTokenGenerator;
	
	
	@PostMapping("/auth/signup")
	public ResponseEntity<?> signup(@ModelAttribute @Valid UserDataDTO info, BindingResult validations) {
		if(validations.hasErrors()) {
			return new ResponseEntity<>(new ErrorsDTO(
					errorHandler.mapErrors(validations.getFieldErrors())), 
					HttpStatus.BAD_REQUEST);
		}
		User tempEmail = userService.getUserByIdentifier(info.getEmail());
		User tempUserName = userService.getUserByIdentifier(info.getUsername());
		if(tempEmail != null || tempUserName != null) {
			return new ResponseEntity<>("El usuario ya existe", 
					HttpStatus.BAD_REQUEST);
		}
		
		try {
			
			userService.register(info);
			return new ResponseEntity<>(new MessageDTO("User Created"), HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new MessageDTO("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/auth/login")
	public ResponseEntity<?> login(@ModelAttribute @Valid LogInUserDataDTO info, BindingResult validations){
		if(validations.hasErrors()) {
			return new ResponseEntity<>(new ErrorsDTO(
					errorHandler.mapErrors(validations.getFieldErrors())), 
					HttpStatus.BAD_REQUEST);
		}
		
		User user = userService.getUserByIdentifier(info.getIdentifier());
		//System.out.println(userService.comparePassword(info.getPassword(), user.getPassword()));
		if(user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		if(!userService.comparePassword(info.getPassword(), user.getPassword())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		try {
			Token token = userService.registerToken(user);
			System.out.println(token);
			return new ResponseEntity<>(new TokenDTO(token), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	@GetMapping("/user/playlist")
	public ResponseEntity<?> getPlaylists(String identifier, String playlistTitle){
		System.out.println(identifier);
		System.out.println(playlistTitle);
		if((identifier == "" && playlistTitle == "") || (identifier == null && playlistTitle == null)) {
			return new ResponseEntity<>("Empty fields, cannot retrieve data", HttpStatus.BAD_REQUEST);
		}
		
		if(identifier == "" || identifier == null) {
			List<PlaylistInfoDTO> playlistFound = playlistService.findAllByParcialTitle(playlistTitle);
			if(playlistFound.isEmpty()) {
				return new ResponseEntity<>("Not found playlist with title", HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(playlistFound, HttpStatus.OK);
		}
		
		

		if(playlistTitle == "" || playlistTitle == null) {
			System.out.println("Entro");
			User user = userService.getUserByIdentifier(identifier);
			List<PlaylistInfoDTO> playlistFound = playlistService.findAllByUser(user.getCode().toString());
			if(playlistFound.isEmpty()) {
				return new ResponseEntity<>("Not found playlist with title for this user", HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(playlistFound, HttpStatus.OK);
		}
		
		User user = userService.getUserByIdentifier(identifier);
		
		List<PlaylistInfoDTO> playlistFound = playlistService.findAllByParcialTitleAndUser(user.getCode().toString(), playlistTitle);
		if(playlistFound.isEmpty()) {
			return new ResponseEntity<>("Not found playlist with title for this user", HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(playlistFound, HttpStatus.OK);
	}
}
