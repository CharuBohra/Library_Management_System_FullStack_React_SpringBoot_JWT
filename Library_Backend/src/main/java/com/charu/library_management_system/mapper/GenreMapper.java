package com.charu.library_management_system.mapper;

import com.charu.library_management_system.dto.GenreDTO;
import com.charu.library_management_system.models.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GenreMapper {

    @Mapping(target = "id" , source = "id")
    @Mapping(target="parentGenreId" , source = "parentGenre.id")
    @Mapping(target="parentGenreName", source = "parentGenre.name")
    GenreDTO toDTO(Genre genre);

    @Mapping(target ="id", source = "id")
    @Mapping(target = "parentGenre" , ignore = true)
    @Mapping(target = "subGenres" , ignore = true)
    Genre toEntity(GenreDTO genreDTO);

    @Mapping(target ="id", ignore = true)
    @Mapping(target = "parentGenre" , ignore = true)
    @Mapping(target = "subGenres" , ignore = true)
    @Mapping(target = "createdAt" , ignore = true)
    @Mapping(target = "updatedAt" , ignore = true)
    void updateEntityFromDTO(GenreDTO genreDTO , @MappingTarget Genre genre);
}
