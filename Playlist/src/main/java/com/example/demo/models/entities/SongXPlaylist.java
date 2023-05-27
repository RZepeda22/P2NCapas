package com.example.demo.models.entities;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class SongXPlaylist {
	@Id
	@Column(name = "code")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID code;
	
	@Column(name = "song_code")
	private UUID songCode;
	
	@Column(name = "playlist_code")
	private UUID playlistCode;
	
	@Column(name = "dateAdded")
	private Date dateAdded;
	
	public SongXPlaylist(UUID songCode, UUID playlistCode, Date dateAdded) {
		super();
		this.songCode = songCode;
		this.playlistCode = playlistCode;
		this.dateAdded = dateAdded;
	}
}
