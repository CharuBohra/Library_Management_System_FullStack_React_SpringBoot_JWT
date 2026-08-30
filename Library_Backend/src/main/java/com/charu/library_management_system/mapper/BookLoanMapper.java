package com.charu.library_management_system.mapper;

import com.charu.library_management_system.dto.BookLoanDTO;
import com.charu.library_management_system.models.BookLoan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookLoanMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "userId",source = "user.id")
    @Mapping(target = "userName",source = "user.fullName")
    @Mapping(target = "userEmail",source = "user.email")
    @Mapping(target = "bookId",source = "book.id")
    @Mapping(target = "bookTitle",source = "book.title")
    @Mapping(target = "bookIsbn",source = "book.isbn")
    @Mapping(target = "bookAuthor",source = "book.author")
    @Mapping(target = "bookCoverImageUrl",source = "book.coverImageUrl")
    BookLoanDTO toDTO(BookLoan bookLoan);

    @Mapping(target = "id",source = "id")
    @Mapping(target = "book",ignore = true)
    @Mapping(target = "user",ignore = true)
    BookLoan toEntity(BookLoanDTO bookLoanDTO);
}
