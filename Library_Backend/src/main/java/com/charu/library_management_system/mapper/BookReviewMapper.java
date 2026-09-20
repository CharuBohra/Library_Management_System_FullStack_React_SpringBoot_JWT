package com.charu.library_management_system.mapper;

import com.charu.library_management_system.dto.BookReviewDTO;
import com.charu.library_management_system.models.BookReview;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookReviewMapper {

    @Mapping(target = "id",source = "id")
    @Mapping(target = "userId",source = "user.id")
    @Mapping(target = "userName",source = "user.fullName")
    @Mapping(target = "bookId",source = "book.id")
    @Mapping(target = "bookTitle",source = "book.title")
    BookReviewDTO toDTO(BookReview bookReview);

    @Mapping(target = "id",source = "id")
    @Mapping(target = "user",ignore = true)
    @Mapping(target = "book",ignore = true)
    BookReview toEntity(BookReviewDTO bookReviewDTO);
}
