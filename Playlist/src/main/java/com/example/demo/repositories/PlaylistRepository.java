package com.example.demo.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.example.demo.models.entities.Playlist;
import com.example.demo.models.entities.Song;

public interface PlaylistRepository extends ListCrudRepository<Playlist, UUID> {
	List<Playlist> findByTitleContainingIgnoreCase(String title);
}
