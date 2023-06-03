package com.example.demo.services.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.dtos.PlaylistInfoDTO;
import com.example.demo.models.dtos.SavePlaylistDTO;
import com.example.demo.models.entities.Playlist;
import com.example.demo.models.entities.SongXPlaylist;
import com.example.demo.models.entities.User;
import com.example.demo.repositories.PlaylistRepository;
import com.example.demo.services.PlaylistService;

import jakarta.transaction.Transactional;

@Service
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
	public List<Playlist> findAll() {
		return playlistRepository.findAll();
	}

	@Override
	public Playlist findOneByCode(String code) {
		UUID id = UUID.fromString(code);
		System.out.println("La playlist");
		System.out.println(id);
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
	public List<Playlist> findAllByCode(String code) {
		UUID id = UUID.fromString(code);
		List<Playlist> playlistSearch = playlistRepository.findAll();
		playlistSearch = playlistSearch.stream()
						.filter(playlist -> playlist.getCode().equals(id))
						.collect(Collectors.toList());
		
		return playlistSearch;
	}

	@Override
	public String calculateDurationOfPlaylist(String code) {
		UUID id = UUID.fromString(code);
		List<Playlist> playlistSearch = playlistRepository.findAll();

		
		Playlist tempPlaylistSearch  = playlistSearch.stream()
					.filter(playlist -> playlist.getCode().equals(id))
					.findAny()
					.orElse(null);
			if(tempPlaylistSearch==null) {
				return "Playlist not found";
			}

		
		List<SongXPlaylist> songXPlaylistSearch = tempPlaylistSearch.getRelationship();
		int[] allDuration = {0};
		songXPlaylistSearch.stream()
		   .forEach(songXPlaylist -> {
			   
			   if(songXPlaylist.getPlaylist().getCode().equals(id)) {
				   allDuration[0] = allDuration[0] + songXPlaylist.getSong().getDuration();
				  
			   }
		   });
			int horas = (allDuration[0] / 3600);
			int min = (allDuration[0] - (3600*horas)) / 60;
			int seg = allDuration[0] -((horas*3600)+(min*60));
		//String formattedDuration = String.format("%02d:%02d:%02d", allDuration[0] / 3600,allDuration[0] / 60, allDuration[0] % 60);
		String formattedDuration = String.format("%02dh:%02dm:%02ds", horas, min, seg);
		return formattedDuration;
	}

	@Override
	public List<PlaylistInfoDTO> findAllByUser(String code) {
		UUID id = UUID.fromString(code);
		List<Playlist> playlistSearch = playlistRepository.findAll();
		List<PlaylistInfoDTO> parseData = new ArrayList<>();
		playlistSearch.stream()
		.forEach(playlist -> {
			if(playlist.getUser().getCode().equals(id)) {
				parseData.add(new PlaylistInfoDTO(playlist.getCode().toString(),playlist.getTitle(), playlist.getDescription()));
			}
			
			});
		
		return parseData;
	}

	@Override
	public List<PlaylistInfoDTO> findAllByParcialTitle(String title) {
		List<Playlist> playlists = playlistRepository.findByTitleContainingIgnoreCase(title);
		List<PlaylistInfoDTO> parseData = new ArrayList<>();
		playlists.stream()
			.forEach(playlist -> {
				parseData.add(new PlaylistInfoDTO(playlist.getCode().toString(),playlist.getTitle(), playlist.getDescription()));
				});
		return parseData;
	}

	@Override
	public List<PlaylistInfoDTO> findAllByParcialTitleAndUser(String code, String title) {
		UUID id = UUID.fromString(code);
		List<Playlist> playlists = playlistRepository.findByTitleContainingIgnoreCase(title);
		List<PlaylistInfoDTO> parseData = new ArrayList<>();
		playlists = playlists.stream()
					.filter(playlist -> playlist.getUser().getCode().equals(id))
					.collect(Collectors.toList());
		//System.out.println("Tamanio una vez filtrada " +playlists.size());
		playlists.stream()
				.forEach(playlist -> {
					parseData.add(new PlaylistInfoDTO(playlist.getCode().toString(),playlist.getTitle(), playlist.getDescription()));
				});
		return parseData;
	}

	@Override
	public boolean existPlaylistInUser(String titlePlaylist, User user) {
		
		//UUID idUser = UUID.fromString(user);
		List<Playlist> playlistSearch = playlistRepository.findAll();
		boolean exist = true;
		
		playlistSearch = playlistSearch.stream()
		.filter(playlist -> playlist.getUser().getCode().equals(user.getCode()) && playlist.getTitle().equals(titlePlaylist) && playlist.getUser().getEmail().equals(user.getEmail()))
		.collect(Collectors.toList());
		
		/*playlistSearch.stream().
		forEach(play -> System.out.println(play.getCode().toString()+", "+play.getTitle()+", "+play.getDescription()));
		*/
		if(playlistSearch.isEmpty()) 
		{
			exist = false;
		}
		
		
		return exist;
	
	}

	@Override
	public Playlist getPlaylistByIdentifier(String identifier) {
		Playlist tempPlaylist =  null;
		List<Playlist> playlistSearch = playlistRepository.findAll();
		if(playlistSearch.stream().anyMatch(playlist-> playlist.getTitle().equals(identifier))) {
			tempPlaylist = playlistSearch.stream()
					.filter(playlist -> playlist.getTitle().equals(identifier))
					.findAny()
					.orElse(null);
		}else {
			UUID id = UUID.fromString(identifier);
			tempPlaylist = playlistSearch.stream()
					.filter(playlist -> playlist.getCode().equals(id))
					.findAny()
					.orElse(null);
		}
		return tempPlaylist;
	}

	@Override
	public Playlist findPlaylistByTitlePlaylistAndUser(String titlePlaylist, User user) {
		List<Playlist> playlistSearch = playlistRepository.findAll();
		Playlist userPlaylist;
		
		userPlaylist = playlistSearch.stream()
				.filter(playlist -> playlist.getUser().getCode().equals(user.getCode()) && playlist.getTitle().equals(titlePlaylist) && playlist.getUser().getEmail().equals(user.getEmail()))
				.findAny()
				.orElse(null);
		
		
		
		
		return userPlaylist;
	}

	

	/*@Override
	public boolean existPlaylistInEmail(String titlePlaylist, String email) {
		
		List<Playlist> playlistSearch = playlistRepository.findAll();
		boolean exist = true;
		
		playlistSearch = playlistSearch.stream()
		.filter(playlist -> playlist.getUser().getEmail().equals(email) && playlist.getTitle().equals(titlePlaylist))
		.collect(Collectors.toList());
		
		if(playlistSearch.isEmpty()) 
		{
			exist = false;
		}
		
		
		return exist;
	}*/


}
