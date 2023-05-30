package com.example.demo.models.entities;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@Entity
@Table(name = "songxplaylist")
public class SongXPlaylist {
	@Id
	@Column(name = "code")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID code;
	
	@ManyToOne
	@JoinColumn(name = "song_code")
	Song song;
	
	@ManyToOne
	@JoinColumn(name = "playlist_code")
	Playlist playlist;
	
	@Column(name = "dateAdded")
	private Date dateAdded;

	public SongXPlaylist(Song song, Playlist playlist, Date dateAdded) {
		super();
		this.song = song;
		this.playlist = playlist;
		this.dateAdded = dateAdded;
	}
	
	
}

