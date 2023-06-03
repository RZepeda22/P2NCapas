package com.example.demo.services.implementations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.dtos.CodeDataDTO;
import com.example.demo.models.entities.Playlist;
import com.example.demo.models.entities.Song;
import com.example.demo.models.entities.SongXPlaylist;
import com.example.demo.repositories.SongXPlaylistRepository;
import com.example.demo.services.SongXPlaylistService;
import jakarta.transaction.Transactional;

@Service
public class SongXPlaylistServiceImpl implements SongXPlaylistService {
	@Autowired
	private SongXPlaylistRepository songXPlaylistRepository;

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void save(LocalDateTime date, Playlist playlist, Song song) throws Exception {
		SongXPlaylist songXPlaylist = new SongXPlaylist(
				song,
				playlist,
				date
			);
		songXPlaylistRepository.save(songXPlaylist);
	}

	@Override
	public List<CodeDataDTO> findAllSongCodeByPlaylist(String code) {
		UUID id = UUID.fromString(code);
		List<SongXPlaylist> songXPlaylistSearch = songXPlaylistRepository.findAll();
		List<CodeDataDTO> tempCodes = new ArrayList<>();;
		songXPlaylistSearch.stream()
						   .forEach(songXPlaylist -> {
							   if(songXPlaylist.getPlaylist().getCode().equals(id)) {
							   tempCodes.add(new CodeDataDTO(songXPlaylist.getSong().getCode().toString()));
							   }
						   });
		return tempCodes;
	}

	@Override
	public boolean existSongInPlaylist(String playlist, String song) {
		UUID idPlaylist = UUID.fromString(playlist);
		UUID idSong = UUID.fromString(song);
		List<SongXPlaylist> songXPlaylistSearch = songXPlaylistRepository.findAll();
		boolean exist = true;
		
		songXPlaylistSearch = songXPlaylistSearch.stream()
			.filter(songXPlaylist -> songXPlaylist.getPlaylist().getCode().equals(idPlaylist))
			.collect(Collectors.toList());
		
		songXPlaylistSearch = songXPlaylistSearch.stream()
				.filter(songXPlaylist -> songXPlaylist.getSong().getCode().equals(idSong))
				.collect(Collectors.toList());
		
		if(songXPlaylistSearch.isEmpty()){
			exist = false;
		}
		return exist;
	}


	

	

}
