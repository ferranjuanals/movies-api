package api.movies.backend.infrastructure.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ApiTmdb {

    private WebClient webClient;

    @Autowired
    public ApiTmdb(WebClient webClient) {
        this.webClient = webClient;
    }

    public String getMovie(String id) {
        return webClient.get()
                .uri("movie/{movieId}", id)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String getCredits(String id) {
        return webClient.get()
                .uri("movie/{movieId}/credits", id)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String getPeople(String id) {
        return webClient.get()
                .uri("person/{person_id}", id)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
