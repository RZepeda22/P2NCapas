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
@Table(name = "playlist")
public class Playlist {
	
	@Id
	@Column(name = "code")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID code;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "user_code")
	private UUID userCode;
	
	@JsonIgnore
	@OneToMany(mappedBy = "playlist", fetch = FetchType.LAZY)
	List<SongXPlaylist> relationship;
	
	public Playlist(String title, String description, UUID userCode) {
		super();
		this.title = title;
		this.description = description;
		this.userCode = userCode;
	}
}
