package com.charu.library_management_system.repository;

import com.charu.library_management_system.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre,Long> {

    List<Genre> findByActiveTrueOrderByDisplayOrderAsc();

    List<Genre> findByParentGenreIdAndActiveTrueOrderByDisplayOrderAsc(Long genreId);

    List<Genre> findByParentGenreIsNullAndActiveTrueOrderByDisplayOrderAsc();

    Long countByActiveTrue();

//    @Query("select count(b) from book b where b.genre.id = :genreId")
//    Long countBookByGenre(@Param("genreId") Long genreId);

}
