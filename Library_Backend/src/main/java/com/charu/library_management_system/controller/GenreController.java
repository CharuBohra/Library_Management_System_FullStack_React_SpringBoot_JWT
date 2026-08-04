package com.charu.library_management_system.controller;

import com.charu.library_management_system.dto.GenreDTO;
import com.charu.library_management_system.dto.responseDTO.ApiResponse;
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

    @GetMapping("/{genreId}")
    public ResponseEntity<?> getGenreById(@PathVariable Long genreId)
    {
        GenreDTO genre = genreService.getGenreById(genreId);
        return ResponseEntity.ok(genre);
    }

    @PutMapping("/{genreId}")
    public ResponseEntity<?> updateGenre(@PathVariable Long genreId , @RequestBody GenreDTO genreDTO)
    {
        GenreDTO updatedGenre = genreService.updateGenre(genreId,genreDTO);
        return ResponseEntity.ok(updatedGenre);
    }

    @DeleteMapping("/{genreId}/soft-delete")
    public ResponseEntity<?> deleteGenre(@PathVariable Long genreId)
    {
        genreService.deleteGenre(genreId);
        ApiResponse apiResponse = new ApiResponse("Genre Deleted - soft delete",true);
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{genreId}/hard")
    public ResponseEntity<?> hardDeleteGenre(@PathVariable Long genreId)
    {
        genreService.hardDeleteGenre(genreId);
        ApiResponse apiResponse = new ApiResponse("Genre Deleted - hard delete",true);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/top-level-genres")
    public ResponseEntity<?> getTopLevelGenres()
    {
        List<GenreDTO> topLevelGenres = genreService.getTopLevelGenres();
        return ResponseEntity.ok(topLevelGenres);
    }

    @GetMapping("/count")
    public ResponseEntity<?> getTotalActiveGenres()
    {
        Long countActiveGenres = genreService.getTotalActiveGenres();
        return ResponseEntity.ok(countActiveGenres);
    }

    @GetMapping("/{id}/book-count")
    public ResponseEntity<?> getBookCountByGenre(@PathVariable Long id)
    {
        Long bookCountByGenre = genreService.getBookCountByGenre(id);
        return ResponseEntity.ok(bookCountByGenre);
    }
}
