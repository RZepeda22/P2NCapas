package com.example.demo.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetPlaylistDataDTO {
	private String title;
	private String description;
}
