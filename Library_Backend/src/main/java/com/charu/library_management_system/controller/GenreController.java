package com.charu.library_management_system.controller;

import com.charu.library_management_system.dto.GenreDTO;
import com.charu.library_management_system.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/genres")
public class GenreController {

    private final GenreService genreService;
    @PostMapping("/create")
    public ResponseEntity<GenreDTO> addGenre(@RequestBody GenreDTO genreDTO)
    {
        GenreDTO genre= genreService.createGenre(genreDTO);
        return new ResponseEntity<>(genre, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllGenres()
    {
        List<GenreDTO> genres = genreService.getAllGenres();
        return ResponseEntity.ok(genres);
    }
}
