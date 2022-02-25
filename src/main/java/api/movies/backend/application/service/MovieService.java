package api.movies.backend.application.service;

import api.movies.backend.application.dto.*;
import api.movies.backend.domain.entity.Credit;
import api.movies.backend.domain.entity.Movie;
import api.movies.backend.domain.repository.IMovieRepository;
import api.movies.backend.infrastructure.api.ApiTmdb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

@Service
public class MovieService {

    private final ApiTmdb apiTmdb;
    private final IMovieRepository movieRepository;
    private final CreditService creditService;

    @Autowired
    public MovieService(ApiTmdb apiTmdb, IMovieRepository movieRepository, CreditService creditService) {
       this.apiTmdb = apiTmdb;
       this.movieRepository = movieRepository;
       this.creditService = creditService;
    }

    public MovieDto saveMovie(String tmdbId) {
        TmdbMovieDto tmdbMovieDto = apiTmdb.getMovie(tmdbId);
        TmdbCreditsDto tmdbCreditsDto = apiTmdb.getCredits(tmdbId);
        Movie movie = mapMovieFromTmdbDto(tmdbMovieDto, tmdbCreditsDto);
        movie = movieRepository.save(movie);
        return mapMovieDtoFromMovie(movie);
    }

    private Movie mapMovieFromTmdbDto(TmdbMovieDto tmdbMovieDto, TmdbCreditsDto tmdbCreditsDto) {
        return Movie.builder()
                .id(tmdbMovieDto.getId())
                .title(tmdbMovieDto.getTitle())
                .description(tmdbMovieDto.getOverview())
                .rating(tmdbMovieDto.getVoteAverage().doubleValue())
                .releaseDate(LocalDate.parse(tmdbMovieDto.getReleaseDate()))
                .credits(creditService.createCreditsFromTmdbDto(tmdbCreditsDto))
                .build();
    }

    public Page<MovieDto> getMovies(Integer page, Integer size, Integer year, String string) {
        if(year != null && string != null) return getAllMoviesByYearAndTitle(createPageable(page, size), year, string);
        if (year != null) return getAllMoviesByYear(createPageable(page, size), year);
        if (string != null) return getAllMoviesByTitle(createPageable(page, size), string);
        return getAllMovies(createPageable(page, size));
    }

    private Pageable createPageable(Integer page, Integer size) {
        return PageRequest.of(page, size);
    }

    private Page<MovieDto> getAllMoviesByYearAndTitle(Pageable pageable, Integer year, String string) {
        return movieRepository.findAllByYearAndTitle(daysOfYear(year), string, pageable)
                .map(this::mapMovieDtoFromMovie);
    }

    private Page<MovieDto> getAllMoviesByYear(Pageable pageable, Integer year) {
        return  movieRepository.findAllByYear(daysOfYear(year), pageable)
                .map(this::mapMovieDtoFromMovie);
    }

    private Page<MovieDto> getAllMoviesByTitle(Pageable pageable, String string) {
        return movieRepository.findAllByTitle(string, pageable)
                .map(this::mapMovieDtoFromMovie);
    }

    private Page<MovieDto> getAllMovies(Pageable pageable) {
        return movieRepository.findAll(pageable)
                .map(this::mapMovieDtoFromMovie);
    }

    private List<LocalDate> daysOfYear(Integer year) {
        LocalDate firstDay = LocalDate.ofYearDay(year, 1);
        LocalDate lastDay = firstDay.with(lastDayOfYear());
        return Arrays.asList(firstDay, lastDay);
    }

    private MovieDto mapMovieDtoFromMovie(Movie movie) {
        return MovieDto.builder()
                .movieId(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .rating(movie.getRating())
                .releaseDate(movie.getReleaseDate())
                .directors(filterCredits(movie.getCredits(), "director"))
                .writers(filterCredits(movie.getCredits(), "writer"))
                .cast(filterCredits(movie.getCredits(), "actor"))
                .build();
    }

    private List<CreditDto> filterCredits(List<Credit> credits, String role) {
        return credits.stream()
                .filter(credit -> credit.getJob().equals(role))
                .map(this::mapCreditDtoFromCredit)
                .collect(Collectors.toList());
    }

    private CreditDto mapCreditDtoFromCredit(Credit credit) {
        return CreditDto.builder()
                .creditId(credit.getId())
                .job(credit.getJob())
                .character(credit.getCharacter())
                .person(PersonDto.builder()
                        .personId(credit.getPerson().getId())
                        .name(credit.getPerson().getName())
                        .build())
                .build();
    }

}
