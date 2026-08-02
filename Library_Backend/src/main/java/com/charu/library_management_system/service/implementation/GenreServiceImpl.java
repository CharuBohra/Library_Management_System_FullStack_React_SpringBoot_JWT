package com.charu.library_management_system.service.implementation;

import com.charu.library_management_system.dto.GenreDTO;
import com.charu.library_management_system.exception.ParentGenreNotFoundException;
import com.charu.library_management_system.mapper.GenreMapper;
import com.charu.library_management_system.models.Genre;
import com.charu.library_management_system.repository.GenreRepository;
import com.charu.library_management_system.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

        System.out.println(genre.getId());

        Genre savedGenre = genreRepository.save(genre);

        System.out.println(savedGenre.getId());

        GenreDTO dto = genreMapper.toDTO(savedGenre);

        System.out.println(dto.getId());

        return dto;
    }

    @Override
    public List<GenreDTO> getAllGenres() {
        List<GenreDTO> genres = genreRepository.findAll()
                .stream()
                .map(genreMapper::toDTO)
                .toList();

        return genres;
    }
}
