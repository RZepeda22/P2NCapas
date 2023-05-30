package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.dtos.ErrorsDTO;
import com.example.demo.models.dtos.GetUserDataDTO;
import com.example.demo.models.dtos.MessageDTO;
import com.example.demo.models.dtos.SaveSongDTO;
import com.example.demo.models.dtos.UserDataDTO;
import com.example.demo.models.entities.Song;
import com.example.demo.models.entities.User;
import com.example.demo.services.SongService;
import com.example.demo.services.UserService;
import com.example.demo.utils.ErrorHandlers;

import jakarta.validation.Valid;

@RestController
@RequestMapping("")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private ErrorHandlers errorHandler;
	
	
	@PostMapping("/auth/signup")
	public ResponseEntity<?> signup(@ModelAttribute @Valid UserDataDTO info, BindingResult validations) {
		if(validations.hasErrors()) {
			return new ResponseEntity<>(new ErrorsDTO(
					errorHandler.mapErrors(validations.getFieldErrors())), 
					HttpStatus.BAD_REQUEST);
		}
		User tempUser = userService.findOneByEmail(info.getEmail());
		if(tempUser != null) {
			return new ResponseEntity<>(new ErrorsDTO(
					errorHandler.mapErrors(validations.getFieldErrors())), 
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
	
	@GetMapping("user/playlist")
	public ResponseEntity<?> saveCategory(@ModelAttribute @Valid UserDataDTO info, BindingResult validations) {
		if(validations.hasErrors()) {
			return new ResponseEntity<>(new ErrorsDTO(
					errorHandler.mapErrors(validations.getFieldErrors())), 
					HttpStatus.BAD_REQUEST);
		}
		
		try {
			
			userService.register(info);
			return new ResponseEntity<>(new MessageDTO("Song Created"), HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new MessageDTO("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
