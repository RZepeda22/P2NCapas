package com.example.demo.services.implementations;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.models.dtos.SaveSongXPlaylistDTO;
import com.example.demo.models.entities.Playlist;
import com.example.demo.models.entities.Song;
import com.example.demo.models.entities.SongXPlaylist;
import com.example.demo.repositories.SongXPlaylistRepository;
import com.example.demo.services.SongXPlaylistService;

import jakarta.transaction.Transactional;

public class SongXPlaylistServiceImpl implements SongXPlaylistService {
	private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
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
	@Transactional(rollbackOn = Exception.class)
	public void deleteOneByCode(String code) {
		UUID id = UUID.fromString(code);
		songXPlaylistRepository.deleteById(id);
		
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void deleteOneBySong(String songCode) {
		/*UUID id = UUID.fromString(songCode);
		List<SongXPlaylist> songXPlaylistSearch = songXPlaylistRepository.findAll();
		SongXPlaylist tempSongXPlaylist = songXPlaylistSearch.stream()
											.filter(songXPlaylist -> songXPlaylist.getSongCode().equals(id))
											.findAny()
											.orElse(null);
		songXPlaylistRepository.deleteById(tempSongXPlaylist.getCode());*/
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void deleteOneByPlaylist(String playlistCode) {
		/*UUID id = UUID.fromString(playlistCode);
		List<SongXPlaylist> songXPlaylistSearch = songXPlaylistRepository.findAll();
		SongXPlaylist tempSongXPlaylist = songXPlaylistSearch.stream()
											.filter(songXPlaylist -> songXPlaylist.getPlaylistCode().equals(id))
											.findAny()
											.orElse(null);
		songXPlaylistRepository.deleteById(tempSongXPlaylist.getCode());*/
		
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void deleteOneByDateAdded(String dateAdded){
		Date date;
		try {
			date = dateFormat.parse(dateAdded);
			List<SongXPlaylist> songXPlaylistSearch = songXPlaylistRepository.findAll();
			SongXPlaylist tempSongXPlaylist = songXPlaylistSearch.stream()
												.filter(songXPlaylist -> songXPlaylist.getDateAdded().equals(date))
												.findAny()
												.orElse(null);
			songXPlaylistRepository.deleteById(tempSongXPlaylist.getCode());
		} catch (ParseException e) {
			e.printStackTrace();
			songXPlaylistRepository.deleteById(null);
		}
		
		
	}

	@Override
	public List<SongXPlaylist> findAll() {
		return songXPlaylistRepository.findAll();
	}

	@Override
	public List<SongXPlaylist> findAllByCode(String code) {
		UUID id = UUID.fromString(code);
		List<SongXPlaylist> songXPlaylistSearch = songXPlaylistRepository.findAll();
		songXPlaylistSearch = songXPlaylistSearch.stream()
								.filter(songXPlaylist -> songXPlaylist.getCode().equals(id))
								.collect(Collectors.toList());
		
		return songXPlaylistSearch;
	}

	@Override
	public List<SongXPlaylist> findAllBySong(String songCode) {
		/*UUID id = UUID.fromString(songCode);
		List<SongXPlaylist> songXPlaylistSearch = songXPlaylistRepository.findAll();
		songXPlaylistSearch = songXPlaylistSearch.stream()
								.filter(songXPlaylist -> songXPlaylist.getSongCode().equals(id))
								.collect(Collectors.toList());
		
		return songXPlaylistSearch;*/
		return null;
	}

	@Override
	public List<SongXPlaylist> findAllByPlaylist(String playlistCode) {
		/*UUID id = UUID.fromString(playlistCode);
		List<SongXPlaylist> songXPlaylistSearch = songXPlaylistRepository.findAll();
		songXPlaylistSearch = songXPlaylistSearch.stream()
								.filter(songXPlaylist -> songXPlaylist.getPlaylistCode().equals(id))
								.collect(Collectors.toList());
		
		return songXPlaylistSearch;*/
		return null;
	}

	@Override
	public List<SongXPlaylist> findAllByDateAdded(String dateAdded) {
		Date date;
		List<SongXPlaylist> songXPlaylistSearch = songXPlaylistRepository.findAll();
		try {
			date = dateFormat.parse(dateAdded);
			
			songXPlaylistSearch = songXPlaylistSearch.stream()
												.filter(songXPlaylist -> songXPlaylist.getDateAdded().equals(date))
												.collect(Collectors.toList());
			return songXPlaylistSearch;
		} catch (ParseException e) {
			e.printStackTrace();
			songXPlaylistSearch = null;
			return songXPlaylistSearch;
		}
	}

	@Override
	public SongXPlaylist findOneByCode(String code) {
		UUID id = UUID.fromString(code);
		List<SongXPlaylist> songXPlaylistSearch = songXPlaylistRepository.findAll();
		SongXPlaylist tempSongXPlaylistSearch = songXPlaylistSearch.stream()
													.filter(songXPlaylist -> songXPlaylist.getCode().equals(id))
													.findAny()
													.orElse(null);
		
		return tempSongXPlaylistSearch;
	}

	@Override
	public SongXPlaylist findOneBySong(String songCode) {
		/*UUID id = UUID.fromString(songCode);
		List<SongXPlaylist> songXPlaylistSearch = songXPlaylistRepository.findAll();
		SongXPlaylist tempSongXPlaylistSearch = songXPlaylistSearch.stream()
													.filter(songXPlaylist -> songXPlaylist.getSongCode().equals(id))
													.findAny()
													.orElse(null);
		
		return tempSongXPlaylistSearch;*/
		return null;
	}

	@Override
	public SongXPlaylist findOneByPlaylist(String playlistCode) {
		/*UUID id = UUID.fromString(playlistCode);
		List<SongXPlaylist> songXPlaylistSearch = songXPlaylistRepository.findAll();
		SongXPlaylist tempSongXPlaylistSearch = songXPlaylistSearch.stream()
													.filter(songXPlaylist -> songXPlaylist.getPlaylistCode().equals(id))
													.findAny()
													.orElse(null);
		
		return tempSongXPlaylistSearch;*/
		return null;
	}

	@Override
	public SongXPlaylist findOneByDateAdded(String dateAdded) {
		Date date;
		List<SongXPlaylist> songXPlaylistSearch = songXPlaylistRepository.findAll();
		try {
			date = dateFormat.parse(dateAdded);
			
			SongXPlaylist tempSongXPlaylistSearch = songXPlaylistSearch.stream()
												.filter(songXPlaylist -> songXPlaylist.getDateAdded().equals(date))
												.findAny()
												.orElse(null);
			return tempSongXPlaylistSearch;
		} catch (ParseException e) {
			e.printStackTrace();
			SongXPlaylist tempSongXPlaylistSearch = null;
			return tempSongXPlaylistSearch;
		}
	}

	

	

}
