package com.charu.library_management_system.mapper;

import com.charu.library_management_system.dto.BookDTO;
import com.charu.library_management_system.dto.requestDTO.UpdateBookRequestDTO;
import com.charu.library_management_system.models.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "id" , source = "id")
    @Mapping(target = "genreId" , source = "genre.id")
    @Mapping(target = "genreName" , source = "genre.name")
    @Mapping(target = "genreCode" , source = "genre.code")
    BookDTO toDTO(Book book);

    @Mapping(target = "id" , source = "id")
    @Mapping(target = "genre" , ignore = true)
    Book toEntity(BookDTO bookDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isbn" , ignore = true)
    @Mapping(target = "genre" , ignore = true)
    @Mapping(target = "createdAt" , ignore = true)
    @Mapping(target = "updatedAt" , ignore = true)
    void updateEntityFromDTO(UpdateBookRequestDTO updateBookDTO, @MappingTarget Book book);
}
