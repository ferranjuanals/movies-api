package api.movies.backend.infrastructure.orm.repository;

import api.movies.backend.infrastructure.orm.entity.JpaMovie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface JpaMovieRepository extends JpaRepository<JpaMovie, Integer> {

    Page<JpaMovie> findAllByReleaseDateBetween(LocalDate firstDay, LocalDate lastDay, Pageable pageable);

    Page<JpaMovie> findAllByTitleContains(String string, Pageable pageable);

    Page<JpaMovie> findAllByReleaseDateBetweenAndTitleContains(LocalDate firstDay, LocalDate lastDay, String string, Pageable pageable);

}
