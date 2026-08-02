package com.charu.library_management_system.controller;

import com.charu.library_management_system.dto.GenreDTO;
import com.charu.library_management_system.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/genres")
public class GenreController {

    private final GenreService genreService;
    @PostMapping
    public ResponseEntity<GenreDTO> addGenre(@RequestBody GenreDTO genreDTO)
    {
        GenreDTO genre= genreService.createGenre(genreDTO);
        return ResponseEntity.ok(genre);
    }
}
