package api.movies.backend.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class Movie {

    @NotNull
    private Integer id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private Double rating;

    @NotNull
    private LocalDate releaseDate;

    @NotEmpty
    private List<Credit> credits;

}
