package api.movies.backend.infrastructure.api;

import api.movies.backend.application.dto.TmdbCreditsDto;
import api.movies.backend.application.dto.TmdbMovieDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ApiTmdb {

    private WebClient webClient;

    @Autowired
    public ApiTmdb(WebClient webClient) {
        this.webClient = webClient;
    }

    public TmdbMovieDto getMovie(String movieId) {
        return webClient.get()
                .uri("movie/{movieId}", movieId)
                .retrieve()
                .bodyToMono(TmdbMovieDto.class)
                .block();
    }

    public TmdbCreditsDto getCredits(String movieId) {
        return webClient.get()
                .uri("movie/{movieId}/credits", movieId)
                .retrieve()
                .bodyToMono(TmdbCreditsDto.class)
                .block();
    }

}
