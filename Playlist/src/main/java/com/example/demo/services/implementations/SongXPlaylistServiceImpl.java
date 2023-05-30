package com.example.demo.services.implementations;

import java.sql.Timestamp;
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
import org.springframework.stereotype.Service;

import com.example.demo.models.dtos.SaveSongXPlaylistDTO;
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
	public void save(SaveSongXPlaylistDTO info) throws Exception {
		SongXPlaylist songplaylist = new SongXPlaylist(
				info.getSong(),
				info.getPlaylist(),
				info.getDateAdded());
		songXPlaylistRepository.save(songplaylist);
	}
	

}
