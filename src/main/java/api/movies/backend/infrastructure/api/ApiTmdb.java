package api.movies.backend.infrastructure.api;

import api.movies.backend.application.dto.MovieDto;
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

    public MovieDto getMovie(String movieId) {
        return webClient.get()
                .uri("movie/{movieId}", movieId)
                .retrieve()
                .bodyToMono(MovieDto.class)
                .block();
    }

    public CreditsDto getCredits(String movieId) {
        return webClient.get()
                .uri("movie/{movieId}/credits", movieId)
                .retrieve()
                .bodyToMono(CreditsDto.class)
                .block();
    }

    public PeopleDto getPeople(String peopleId) {
        return webClient.get()
                .uri("person/{person_id}", peopleId)
                .retrieve()
                .bodyToMono(PeopleDto.class)
                .block();
    }
}
