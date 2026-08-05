package com.charu.library_management_system.mapper;

import com.charu.library_management_system.dto.BookDTO;
import com.charu.library_management_system.models.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "id" , source = "id")
    @Mapping(target = "genreId" , source = "genre.id")
    @Mapping(target = "genreName" , source = "genre.name")
    @Mapping(target = "genreCode" , source = "genre.code")
    BookDTO toDTO(Book book);
}
