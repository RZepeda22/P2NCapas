package com.example.demo.models.entities;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Entity
@ToString(exclude = {"relationship"})
@Table(name = "song")
public class Song {
	@Id
	@Column(name = "code")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID code;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "duration")
	private int duration;
	
	@OneToMany(mappedBy = "song", fetch = FetchType.LAZY)
	@JsonIgnore
	List<SongXPlaylist> relationship;
	
	public Song(String title, int duration) {
		super();
		this.title = title;
		this.duration = duration;
	}
	
}
