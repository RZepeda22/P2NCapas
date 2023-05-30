package com.example.demo.services.implementations;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.dtos.GetPlaylistDataDTO;
import com.example.demo.models.dtos.SavePlaylistDTO;
import com.example.demo.models.dtos.ShowSongsDTO;
import com.example.demo.models.entities.Playlist;
import com.example.demo.models.entities.SongXPlaylist;
import com.example.demo.models.entities.User;
import com.example.demo.repositories.PlaylistRepository;
import com.example.demo.repositories.SongXPlaylistRepository;
import com.example.demo.services.PlaylistService;
import com.example.demo.models.dtos.SongDataParseDTO;

import jakarta.transaction.Transactional;

@Service
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
	public List<ShowSongsDTO> getSongsInPlaylistWithDatesfind(Playlist playlist) {
		List<SongXPlaylist> newSongs = playSongRepository.findByPlaylist(playlist);
		
		List<ShowSongsDTO> newData = new ArrayList<>();
		
		newSongs.stream().forEach(data -> {
			String formattedDuration = String.format("%02d:%02d", data.getSong().getDuration() / 60, data.getSong().getDuration() % 60);
			SongDataParseDTO newSongData = new SongDataParseDTO(
					data.getSong().getTitle(), formattedDuration);
			newData.add(new ShowSongsDTO(newSongData, data.getPlaylist(), data.getDateAdded()));
		});
		
		
	    return newData;
	    
	}

	@Override
	public List<GetPlaylistDataDTO> findByTitleContainingIgnoreCase(String title) {
		List<Playlist> allPlaylistsByTitle = playlistRepository.findByTitleContainingIgnoreCase(title);
		List<GetPlaylistDataDTO> allPlaylistByTitleAndUser  = new ArrayList<>();
		
		allPlaylistsByTitle.stream().forEach(data ->{
			allPlaylistByTitleAndUser.add(new GetPlaylistDataDTO(
					data.getTitle(),
					data.getDescription()));
		});
		return allPlaylistByTitleAndUser;
	}

	@Override
	public List<GetPlaylistDataDTO> findByTitleAndAlsoIdentifier(User user, String title) {
		List<Playlist> allPlaylistsByTitle = playlistRepository.findByTitleContainingIgnoreCase(title);
		List<GetPlaylistDataDTO> allPlaylistByTitleAndUser  = new ArrayList<>();
		allPlaylistsByTitle.stream().forEach(data -> {
			if(data.getUser().getEmail().equals(user.getEmail())) {
				allPlaylistByTitleAndUser.add(new GetPlaylistDataDTO(
						data.getTitle(),
						data.getDescription()));
			}
			
		});
		return allPlaylistByTitleAndUser;
	}

	@Override
	public List<GetPlaylistDataDTO> findByUserOnly(User user) {
		List<Playlist> allPlaylistsByTitle = playlistRepository.findAll();
		List<GetPlaylistDataDTO> allPlaylistByTitleAndUser  = new ArrayList<>();
		allPlaylistsByTitle.stream().forEach(data -> {
			if(data.getUser().getEmail().equals(user.getEmail())) {
				allPlaylistByTitleAndUser.add(new GetPlaylistDataDTO(
						data.getTitle(),
						data.getDescription()));
			}
			
		});
		return allPlaylistByTitleAndUser;
	}



	
	
	
}
