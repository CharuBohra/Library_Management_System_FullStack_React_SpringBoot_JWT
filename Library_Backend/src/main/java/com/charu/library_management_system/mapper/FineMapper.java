package com.charu.library_management_system.mapper;

import com.charu.library_management_system.dto.FineDTO;
import com.charu.library_management_system.models.Fine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FineMapper {

    @Mapping(source = "id",target = "id")
    @Mapping(source = "user.id",target = "userId")
    @Mapping(source = "user.fullName",target = "userName")
    @Mapping(source = "user.email",target = "userEmail")

    @Mapping(source = "bookLoan.book.title",target = "bookTitle")
    @Mapping(source = "bookLoan.book.isbn",target = "bookIsbn")
    @Mapping(source = "bookLoan.id",target = "bookLoanId")

    @Mapping(source = "waivedBy.id",target = "waivedByUserId")
    @Mapping(source = "waivedBy.fullName",target = "waivedByUserName")
    @Mapping(source = "waivedBy.email",target = "waivedByUserEmail")

    @Mapping(source = "processedBy.id",target = "processedByUserId")
    @Mapping(source = "processedBy.fullName",target = "processedByUserName")
    @Mapping(source = "processedBy.email",target = "processedByUserEmail")

    @Mapping(target = "amountPaid" , ignore = true)
    @Mapping(target = "amountOutstanding", ignore = true)
    FineDTO toDTO(Fine fine);

    @Mapping(source = "id",target = "id")
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "bookLoan",ignore = true)
    @Mapping(target = "waivedBy",ignore = true)
    @Mapping(target = "processedBy",ignore = true)
    Fine toEntity(FineDTO fineDTO);
}
