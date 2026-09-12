package com.charu.library_management_system.mapper;

import com.charu.library_management_system.dto.ReservationDTO;
import com.charu.library_management_system.models.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    @Mapping(target = "id",source = "id")
    @Mapping(target = "userId",source = "user.id")
    @Mapping(target = "userName",source = "user.fullName")
    @Mapping(target = "userEmail",source = "user.email")
    @Mapping(target = "bookId",source = "book.id")
    @Mapping(target = "bookTitle",source = "book.title")
    @Mapping(target = "bookIsbn",source = "book.isbn")
    @Mapping(target = "bookAuthor",source = "book.author")
    @Mapping(target = "isExpired", expression = "java(reservation.hasExpired())")
    @Mapping(target = "canBeCancelled", expression = "java(reservation.canBeCancelled())")
    ReservationDTO toDTO(Reservation reservation);

    @Mapping(source = "id", target = "id")
    @Mapping(target="user", ignore = true)
    @Mapping(target = "book" , ignore = true)
    @Mapping(target = "createdAt" , ignore = true)
    @Mapping(target = "updatedAt" , ignore = true)
    Reservation toEntity(ReservationDTO reservationDTO);
}
