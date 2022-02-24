package api.movies.backend.domain.repository;

import api.movies.backend.domain.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface IMovieRepository {

    Movie save(Movie movie);

    Page<Movie> findAll(Pageable pageable);

    Page<Movie> findAllByYear(List<LocalDate> daysOfYear, Pageable pageable);

    Page<Movie> findAllByTitle(String string, Pageable pageable);

    Page<Movie> findAllByYearAndTitle(List<LocalDate> daysOfYear, String string, Pageable pageable);

}
