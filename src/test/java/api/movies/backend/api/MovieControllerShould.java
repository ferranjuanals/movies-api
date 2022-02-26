package api.movies.backend.api;

import api.movies.backend.application.dto.MovieDto;
import api.movies.backend.infrastructure.orm.entity.JpaMovie;
import api.movies.backend.infrastructure.orm.repository.JpaMovieRepository;
import api.movies.backend.util.RestPageImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerShould {

    private final static String MOVIE_BASE_URI = "/movies";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JpaMovieRepository jpaMovieRepository;

    @Test
    void returnNotFoundWhenTmdbIdIsInvalid() throws Exception {
        String invalidTmdbId = "5500";
        performPostRequest(invalidTmdbId)
                .andExpect(status().isNotFound());
    }

    @Test
    void persistMovie() throws Exception {
        String validTmdbId = "550";
        String persistedResponse = performPostRequest(validTmdbId)
                .andExpect(status().isCreated())
                .andReturn().getResponse()
                .getContentAsString();
        MovieDto persistedMovie = objectMapper.readValue(persistedResponse, MovieDto.class);
        assertAll(
                () -> assertThat(persistedMovie.getMovieId()).isEqualTo(550),
                () -> assertThat(persistedMovie.getTitle()).isEqualTo("Fight Club"),
                () -> assertThat(persistedMovie.getDirectors()).isNotEmpty(),
                () -> assertThat(persistedMovie.getCast()).isNotEmpty()
        );
    }

    private ResultActions performPostRequest(String tmdbId) throws Exception {
        return mvc.perform(post(MOVIE_BASE_URI + "/" + tmdbId));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidUri")
    void returnErrorWhenUriIsInvalid(String invalidUri) throws Exception {
        performGetRequest(invalidUri)
                .andExpect(status().isBadRequest());
    }

    private static Stream<String> provideInvalidUri() {
        return Stream.of(
                "",
                "?page=0",
                "?page=0&size=Six",
                "?page=0&size=6&year=Month"
        );
    }

    @ParameterizedTest
    @MethodSource("provideValidUri")
    void fetchPaginatedListOfMovies(String validUri) throws Exception {
        JpaMovie jpaMovie = JpaMovie.builder()
                .id(550)
                .title("Fight Club")
                .rating(8.4)
                .releaseDate(LocalDate.parse("1999-10-15"))
                .build();
        jpaMovieRepository.save(jpaMovie);
        String fetchedResponse = performGetRequest(validUri)
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();
        Page<MovieDto> fetchedMovies = objectMapper.readValue(fetchedResponse, new TypeReference<RestPageImpl<MovieDto>>(){});
        assertAll(
                () -> assertThat(fetchedMovies.getTotalElements()).isEqualTo(1),
                () -> assertThat(fetchedMovies.getSize()).isEqualTo(6)
        );
    }

    private static Stream<String> provideValidUri() {
        return Stream.of(
                "?page=0&size=6",
                "?page=0&size=6&title=Fight",
                "?page=0&size=6&year=1999",
                "?page=0&size=6&title=Fight&year=1999"

        );
    }

    private ResultActions performGetRequest(String uri) throws Exception {
        return mvc.perform(get(MOVIE_BASE_URI + uri));
    }

}
