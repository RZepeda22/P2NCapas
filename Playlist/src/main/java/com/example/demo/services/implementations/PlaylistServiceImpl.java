package com.example.demo.services.implementations;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.models.dtos.SavePlaylistDTO;
import com.example.demo.models.entities.Playlist;
import com.example.demo.models.entities.SongXPlaylist;
import com.example.demo.models.entities.User;
import com.example.demo.repositories.PlaylistRepository;
import com.example.demo.repositories.SongXPlaylistRepository;
import com.example.demo.services.PlaylistService;

import jakarta.transaction.Transactional;

public class PlaylistServiceImpl implements PlaylistService{
	@Autowired
	private PlaylistRepository playlistRepository;
	
	@Autowired
	private SongXPlaylistRepository playSongRepository;

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void save(SavePlaylistDTO data, User user) throws Exception {
		Playlist playlist = new Playlist(
				data.getTitle(),
				data.getDescription(),
				user
			);
	
		playlistRepository.save(playlist);
		
	}

	@Override
	public List<Playlist> findAll() {
		return playlistRepository.findAll();
	}

	@Override
	public List<Playlist> findAllByCode(String code) {
		UUID id = UUID.fromString(code);
		List<Playlist> playlistSearch = playlistRepository.findAll();
		playlistSearch = playlistSearch.stream()
						.filter(playlist -> playlist.getCode().equals(id))
						.collect(Collectors.toList());
		
		return playlistSearch;
	}


	

	@Override
	public Playlist findOneByCode(String code) {
		UUID id = UUID.fromString(code);
		List<Playlist> playlistSearch = playlistRepository.findAll();
		Playlist tempPlaylistSearch = playlistSearch.stream()
						.filter(playlist -> playlist.getCode().equals(id))
						.findAny()
						.orElse(null);
		
		return tempPlaylistSearch;
	}

	@Override
	public List<SongXPlaylist> getSongsInPlaylistWithDatesfind(Playlist playlist) {
		List<SongXPlaylist> newSongs = playSongRepository.findByPlaylist(playlist);
	    return newSongs;
	    
	}



	
	
	
}
