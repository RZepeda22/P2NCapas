package com.example.demo.controllers;

import java.time.LocalDateTime;
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

import com.example.demo.models.dtos.CodeDataDTO;
import com.example.demo.models.dtos.PlaylistDataDTO;
import com.example.demo.models.dtos.PlaylistInfoDTO;
import com.example.demo.models.dtos.SavePlaylistDTO;
import com.example.demo.models.dtos.SongDataParseDTO;
import com.example.demo.models.entities.Playlist;
import com.example.demo.models.entities.Song;
import com.example.demo.models.entities.User;
import com.example.demo.services.PlaylistService;
import com.example.demo.services.SongService;
import com.example.demo.services.SongXPlaylistService;
import com.example.demo.services.UserService;
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
	
	/*@Autowired
	private ErrorHandlers errorHandler;*/
	
	@PostMapping("/playlist")
	public ResponseEntity<?> savePlaylist(@ModelAttribute @Valid SavePlaylistDTO info, BindingResult validations){
		if(validations.hasErrors()) {
			return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
		}
		
		User user = userService.findUserAuthenticated();
		
		if(user == null) {
			return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
		}
		
		if(playlistService.existPlaylistInUser(info.getTitle(), user)) 
		{
			Playlist playlistSearch = playlistService.findPlaylistByTitlePlaylistAndUser(info.getTitle(), user);
			
			return new ResponseEntity<>( "The playlist is already created for this user \n"
										+ "Code: " + playlistSearch.getCode().toString() + "\n"
										+ "Title: " + playlistSearch.getTitle() + "\n"
										+ "Description: " + playlistSearch.getDescription()  , HttpStatus.FOUND);
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
	 

		Song songSearch = songService.getSongByIdentifier(songCode);
		
		
		
		
		if(songCode == "" ) {
			return new ResponseEntity<>("Song is empty", HttpStatus.NOT_FOUND);
			
		}
		String tempCode = playListCode;
		System.out.println(tempCode);
		Playlist playlistSearch = playlistService.getPlaylistByIdentifier(tempCode);
		
		
		if(playlistSearch == null  ) {
			return new ResponseEntity<>("Playlist not found", HttpStatus.NOT_FOUND);
			
		}
		if(songSearch == null ) {
			return new ResponseEntity<>("Song not found", HttpStatus.NOT_FOUND);
			
		}

		if(songxplayService.existSongInPlaylist(playlistSearch.getCode().toString(), songSearch.getCode().toString())) {
			return new ResponseEntity<>("Song in playlist already exist", HttpStatus.FOUND);
		}
		LocalDateTime now = LocalDateTime.now();
		
		try {
			songxplayService.save(now, playlistSearch, songSearch);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Internal server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(songSearch.getTitle() + " Song added to " + playlistSearch.getTitle(), HttpStatus.OK);
		
	}
	 
	 @GetMapping("/playlist/{code}")
		public ResponseEntity<?> getAllThePlaylist(@PathVariable(name = "code") String playListCode){
		 	Playlist playlist = playlistService.getPlaylistByIdentifier(playListCode);
		 	if(playlist == null) {
				return new ResponseEntity<>("Playlist Not Found", HttpStatus.NOT_FOUND);
			}
			
			//Playlist playlist = playlistService.findOneByCode(playListCode);
			
			
			List<CodeDataDTO> codes = songxplayService.findAllSongCodeByPlaylist(playlist.getCode().toString());
			List<SongDataParseDTO> foundSongs = songService.findAllSongByListCode(codes);
			String durationPlaylist = playlistService.calculateDurationOfPlaylist(playlist.getCode().toString());
			return new ResponseEntity<>(new PlaylistDataDTO(playlist.getTitle(), playlist.getDescription(),durationPlaylist,foundSongs), HttpStatus.OK);
		}
	
	 

}