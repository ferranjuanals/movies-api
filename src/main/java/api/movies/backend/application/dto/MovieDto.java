package api.movies.backend.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieDto {

    private Integer movieId;

    private String title;

    private String description;

    private Double rating;

    private LocalDate releaseDate;

    private List<CreditDto> directors;

    private List<CreditDto> writers;

    private List<CreditDto> cast;

}
