package api.movies.backend.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
public class Movie {

    @NotBlank
    private Integer tmdbId;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotEmpty
    private Double rating;

    @NotEmpty
    private LocalDate releaseDate;

}
