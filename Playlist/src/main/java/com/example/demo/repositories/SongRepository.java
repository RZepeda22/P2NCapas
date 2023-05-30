package com.example.demo.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.example.demo.models.entities.Song;

public interface SongRepository extends ListCrudRepository<Song, UUID>{
	List<Song> findByTitleContainingIgnoreCase(String title);
}
