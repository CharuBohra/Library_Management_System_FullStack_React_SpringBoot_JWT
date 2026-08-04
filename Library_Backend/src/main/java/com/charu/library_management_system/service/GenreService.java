package com.charu.library_management_system.service;

import com.charu.library_management_system.dto.GenreDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GenreService {

    GenreDTO createGenre(GenreDTO genreDTO);

    List<GenreDTO> getAllGenres();

    GenreDTO getGenreById(Long genreId);

    GenreDTO updateGenre(Long genreId, GenreDTO genreDTO);

    void deleteGenre(Long genreId);

    void hardDeleteGenre(Long genreId);

    List<GenreDTO> getAllActiveGenresWithSubGenre();

    List<GenreDTO> getTopLevelGenres();

    //Page<GenreDTO> searchGenre(String searchTerm , Pageable pageable);

    Long getTotalActiveGenres();

    Long getBookCountByGenre(Long genreId);
}
