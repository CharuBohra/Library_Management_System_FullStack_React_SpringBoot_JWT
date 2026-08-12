package com.charu.library_management_system.service.implementation;

import com.charu.library_management_system.dto.GenreDTO;
import com.charu.library_management_system.exception.GenreNotFoundException;
import com.charu.library_management_system.exception.ParentGenreNotFoundException;
import com.charu.library_management_system.mapper.GenreMapper;
import com.charu.library_management_system.models.Genre;
import com.charu.library_management_system.repository.GenreRepository;
import com.charu.library_management_system.service.GenreService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
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

    @Override
    public GenreDTO getGenreById(Long genreId) {
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(()->new GenreNotFoundException("Genre not found for id "+genreId));

        return genreMapper.toDTO(genre);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public GenreDTO updateGenre(Long genreId, GenreDTO genreDTO) {
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(()->new GenreNotFoundException("Genre not found for id "+genreId));


        genreMapper.updateEntityFromDTO(genreDTO,genre);

        if(genreDTO.getParentGenreId()!=null)
        {
            Genre parent = genreRepository.findById(genreDTO.getParentGenreId())
                    .orElseThrow(()->new ParentGenreNotFoundException("Parent Genre not found for id "+genreDTO.getParentGenreId()));
            genre.setParentGenre(parent);
        }

        Genre updatedGenre = genreRepository.save(genre);

        return genreMapper.toDTO(updatedGenre);

    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteGenre(Long genreId) {
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(()->new GenreNotFoundException("Genre not found for id "+genreId));
        genre.setActive(false);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void hardDeleteGenre(Long genreId) {
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(()->new GenreNotFoundException("Genre not found for id "+genreId));
        genreRepository.delete(genre);
    }

    @Override
    public List<GenreDTO> getAllActiveGenresWithSubGenre() {
        List<GenreDTO> activeGenres = genreRepository.findByParentGenreIsNullAndActiveTrueOrderByDisplayOrderAsc()
                .stream()
                .map(genreMapper::toDTO)
                .toList();

        return activeGenres;
    }

    @Override
    public List<GenreDTO> getTopLevelGenres() {
        List<GenreDTO> topGenres = genreRepository.findByParentGenreIsNullAndActiveTrueOrderByDisplayOrderAsc()
                .stream()
                .map(genreMapper::toDTO)
                .toList();

        return topGenres;
    }

//    @Override
//    public Page<GenreDTO> searchGenre(String searchTerm, Pageable pageable) {
//        return null;
//    }

    @Override
    public Long getTotalActiveGenres() {
        return genreRepository.countByActiveTrue();
    }

    @Override
    public Long getBookCountByGenre(Long genreId) {
        return genreRepository.countBookByGenre(genreId);
    }
}
