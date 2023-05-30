package com.example.demo.services.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
				String formattedDuration = String.format("%02d:%02d", song.getDuration() / 60, song.getDuration() % 60);
				parseData.add(new SongDataParseDTO(song.getTitle(), formattedDuration));
				});
		
		return parseData; 
	}

	@Override
	public List<Song> findAllByCode(String code) {
		// TODO Auto-generated method stub
		UUID id = UUID.fromString(code);
		List<Song> songSearch = songRepository.findAll();
		songSearch = songSearch.stream()
						.filter(song -> song.getCode().equals(id))
						.collect(Collectors.toList());
		
		return songSearch;
	}

	@Override
	public List<Song> findAllByTitle(String title) {
		
		List<Song> songSearch = songRepository.findAll();
		songSearch = songSearch.stream()
						.filter(song -> song.getTitle().equals(title))
						.collect(Collectors.toList());
		
		return songSearch;
	}

	@Override
	public List<Song> findAllByDuration(int duration) {
		List<Song> songSearch = songRepository.findAll();
		songSearch = songSearch.stream()
						.filter(song -> song.getDuration() == duration)
						.collect(Collectors.toList());
		
		return songSearch;
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
				String formattedDuration = String.format("%02d:%02d", song.getDuration() / 60, song.getDuration() % 60);
				parseData.add(new SongDataParseDTO(song.getTitle(), formattedDuration));
				});
		
		return parseData; 
		
	}
	
	
	
}
