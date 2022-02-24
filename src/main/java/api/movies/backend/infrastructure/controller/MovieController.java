package api.movies.backend.infrastructure.controller;

import api.movies.backend.application.dto.MovieDto;
import api.movies.backend.application.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/{id}")
    MovieDto saveMovie(@PathVariable("id") String tmdbId) {
        return movieService.saveMovie(tmdbId);
    }

    @GetMapping
    Page<MovieDto> getMovies(@RequestParam Integer page,
                             @RequestParam Integer size,
                             @RequestParam(required = false) Integer year,
                             @RequestParam(required = false) String title) {
        return movieService.getMovies(page, size, year, title);
    }

}
