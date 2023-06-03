package com.example.demo.services.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.dtos.CodeDataDTO;
import com.example.demo.models.dtos.SaveSongDTO;
import com.example.demo.models.dtos.SongDataParseDTO;
import com.example.demo.models.entities.Song;
import com.example.demo.repositories.SongRepository;
import com.example.demo.services.SongService;

import jakarta.transaction.Transactional;

@Service
public class SongServiceimpl implements SongService {
	@Autowired
	private SongRepository songRepository;
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void save(SaveSongDTO data) throws Exception {
		Song song = new Song(
				data.getTitle(),
				data.getDuration()
			);
	
		songRepository.save(song);
		
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void deleteOneByCode(String code) {
		UUID id = UUID.fromString(code);
		songRepository.deleteById(id);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void deleteOneByTitle(String title) {
		List<Song> songSearch = songRepository.findAll();
		Song tempSong = songSearch.stream()
						.filter(song -> song.getTitle().equals(title))
						.findAny()
						.orElse(null);
		songRepository.deleteById(tempSong.getCode());
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void deleteOneByDuration(int duration) {
		List<Song> songSearch = songRepository.findAll();
		Song tempSong = songSearch.stream()
						.filter(song -> song.getDuration() == duration)
						.findAny()
						.orElse(null);
		songRepository.deleteById(tempSong.getCode());
		
	}

	@Override
	public List<SongDataParseDTO> findAll() {
		
		List<Song> Songs = songRepository.findAll();
		List<SongDataParseDTO> parseData = new ArrayList<>();
		Songs.stream()
			.forEach(song -> {
				String formattedDuration = String.format("%02dm:%02ds", song.getDuration() / 60, song.getDuration() % 60);
				parseData.add(new SongDataParseDTO(song.getCode().toString() ,song.getTitle(), formattedDuration));
				});
		
		return parseData; 
	}

	@Override
	public Song findOneByCode(String code) {
		UUID id = UUID.fromString(code);
		List<Song> songSearch = songRepository.findAll();
		Song tempSong = songSearch.stream()
						.filter(song -> song.getCode().equals(id))
						.findAny()
						.orElse(null);
		
		return tempSong;
	}

	@Override
	public Song findOneByTitle(String title) {
		List<Song> songSearch = songRepository.findAll();
		Song tempSong = songSearch.stream()
						.filter(song -> song.getTitle().equals(title))
						.findAny()
						.orElse(null);
		
		return tempSong;
	}

	@Override
	public List<SongDataParseDTO> findAllByParcialTitle(String title) {
		List<Song> Songs = songRepository.findByTitleContainingIgnoreCase(title);
		List<SongDataParseDTO> parseData = new ArrayList<>();
		Songs.stream()
			.forEach(song -> {
				String formattedDuration = String.format("%02dm:%02ds", song.getDuration() / 60, song.getDuration() % 60);
				parseData.add(new SongDataParseDTO(song.getCode().toString() ,song.getTitle(), formattedDuration));
				});
		
		return parseData; 
		
	}
	

	@Override
	public List<SongDataParseDTO> findAllSongByListCode(List<CodeDataDTO> songsCodes) {
		List<Song> songSearch = songRepository.findAll();
		List<SongDataParseDTO> parseData = new ArrayList<>();
		
		songsCodes.stream()
				.forEach(code ->{
					
					UUID id = UUID.fromString(code.getCode());
					Song tempSong = songSearch.stream()
									.filter(song -> song.getCode().equals(id))
									.findAny()
									.orElse(null);
					String formattedDuration = String.format("%02dm:%02ds", tempSong.getDuration() / 60, tempSong.getDuration() % 60);
					parseData.add(new SongDataParseDTO(tempSong.getCode().toString() ,tempSong.getTitle(),formattedDuration));
					
				});

		return parseData;
	}

	@Override
	public Song getSongByIdentifier(String identifier) {
		Song tempSong =  null;
		List<Song> songSearch = songRepository.findAll();
		if(songSearch.stream().anyMatch(playlist-> playlist.getTitle().equals(identifier))) {
			tempSong = songSearch.stream()
					.filter(song -> song.getTitle().equals(identifier))
					.findAny()
					.orElse(null);
		}else {
			tempSong = songSearch.stream()
					.filter(song -> song.getCode().toString().equals(identifier))
					.findAny()
					.orElse(null);
		}
		return tempSong;
	}
	
	
	
}
