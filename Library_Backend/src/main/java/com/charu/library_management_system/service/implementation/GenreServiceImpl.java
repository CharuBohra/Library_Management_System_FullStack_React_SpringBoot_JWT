package com.charu.library_management_system.service.implementation;

import com.charu.library_management_system.dto.GenreDTO;
import com.charu.library_management_system.exception.ParentGenreNotFoundException;
import com.charu.library_management_system.mapper.GenreMapper;
import com.charu.library_management_system.models.Genre;
import com.charu.library_management_system.repository.GenreRepository;
import com.charu.library_management_system.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Override
    public GenreDTO createGenre(GenreDTO genreDTO) {

        Genre genre = genreMapper.toEntity(genreDTO);
        if(genreDTO.getParentGenreId()!=null)
        {
            Genre parent = genreRepository.findById(genreDTO.getParentGenreId())
                    .orElseThrow(()->new ParentGenreNotFoundException("Parent Genre not found for id "+genreDTO.getParentGenreId()));
            genre.setParentGenre(parent);
        }

        Genre savedGenre = genreRepository.save(genre);

        return genreMapper.toDTO(savedGenre);
    }
}
