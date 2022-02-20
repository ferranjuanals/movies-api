package api.movies.backend.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${tmdb.base.url}")
    private String tmdbBaseUrl;

    @Value("${tmdb.api.key}")
    private String accessToken;

    @Bean
    public WebClient movieWebClient(){
        return WebClient.builder()
                .baseUrl(tmdbBaseUrl)
                .defaultHeaders(httpHeaders -> httpHeaders.setBearerAuth(accessToken))
                .build();
    }
}
