package com.example.demo.services.implementations;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.models.dtos.SavePlaylistDTO;
import com.example.demo.models.entities.Playlist;
import com.example.demo.models.entities.User;
import com.example.demo.repositories.PlaylistRepository;
import com.example.demo.services.PlaylistService;

import jakarta.transaction.Transactional;

public class PlaylistServiceImpl implements PlaylistService{
	@Autowired
	private PlaylistRepository playlistRepository;

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
	@Transactional(rollbackOn = Exception.class)
	public void deleteOneByCode(String code) {
		UUID id = UUID.fromString(code);
		playlistRepository.deleteById(id);
		
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void deleteOneByTitle(String title) {
		List<Playlist> playlistSearch = playlistRepository.findAll();
		Playlist tempPlaylist = playlistSearch.stream()
						.filter(playlist -> playlist.getTitle().equals(title))
						.findAny()
						.orElse(null);
		playlistRepository.deleteById(tempPlaylist.getCode());
		
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void deleteOneByDescription(String description) {
		List<Playlist> playlistSearch = playlistRepository.findAll();
		Playlist tempPlaylist = playlistSearch.stream()
						.filter(playlist -> playlist.getDescription().equals(description))
						.findAny()
						.orElse(null);
		playlistRepository.deleteById(tempPlaylist.getCode());
		
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void deleteOneByUser(String userCode) {
		/*UUID id = UUID.fromString(userCode);
		List<Playlist> playlistSearch = playlistRepository.findAll();
		Playlist tempPlaylist = playlistSearch.stream()
						.filter(playlist -> playlist.getUserCode().equals(id))
						.findAny()
						.orElse(null);
		playlistRepository.deleteById(tempPlaylist.getCode());*/
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
	public List<Playlist> findAllByTitle(String title) {
		List<Playlist> playlistSearch = playlistRepository.findAll();
		playlistSearch = playlistSearch.stream()
						.filter(playlist -> playlist.getTitle().equals(title))
						.collect(Collectors.toList());
		
		return playlistSearch;
	}

	@Override
	public List<Playlist> findAllByUser(String code) {
		/*UUID id = UUID.fromString(code);
		List<Playlist> playlistSearch = playlistRepository.findAll();
		playlistSearch = playlistSearch.stream()
						.filter(playlist -> playlist.getUserCode().equals(id))
						.collect(Collectors.toList());
		
		return playlistSearch;*/
		return null;
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
	public Playlist findOneByTitle(String title) {
		/*List<Playlist> playlistSearch = playlistRepository.findAll();
		Playlist tempPlaylistSearch = playlistSearch.stream()
						.filter(playlist -> playlist.getTitle().equals(title))
						.findAny()
						.orElse(null);
		
		return tempPlaylistSearch;*/
		return null;
	}

	@Override
	public Playlist findOneByUser(String code) {
		/*UUID id = UUID.fromString(code);
		List<Playlist> playlistSearch = playlistRepository.findAll();
		Playlist tempPlaylistSearch = playlistSearch.stream()
						.filter(playlist -> playlist.getUserCode().equals(id))
						.findAny()
						.orElse(null);
		
		return tempPlaylistSearch;*/
		return null;
	}

	
	
	
}
