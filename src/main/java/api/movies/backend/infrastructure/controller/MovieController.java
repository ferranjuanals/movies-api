package api.movies.backend.infrastructure.controller;

import api.movies.backend.application.dto.MovieDto;
import api.movies.backend.application.exception.InvalidParameterException;
import api.movies.backend.application.exception.MovieNotFoundInTmdbException;
import api.movies.backend.application.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientException;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    MovieDto saveMovie(@PathVariable("id") String tmdbId) {
        try {
            return movieService.saveMovie(tmdbId);
        } catch (WebClientException e) {
            throw new MovieNotFoundInTmdbException("The given ID doesn't correspond to any TMDb Movie");
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Page<MovieDto> getMovies(@RequestParam Integer page,
                             @RequestParam Integer size,
                             @RequestParam(required = false) Integer year,
                             @RequestParam(required = false) String title) {
        try {
            return movieService.getMovies(page, size, year, title);
        } catch (Exception e) {
            throw new InvalidParameterException(e.getMessage());
        }
    }

}
