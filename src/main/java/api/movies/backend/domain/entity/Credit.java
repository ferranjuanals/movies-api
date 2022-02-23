package api.movies.backend.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Data
@AllArgsConstructor
public class Credit {

    @NotBlank
    private String id;

    @NotBlank
    private String creditType;

    @NotBlank
    private String job;

    private String character;

    @JsonIgnore
    @NotNull
    private Movie movie;

    @JsonIgnore
    @NotNull
    private Person person;

}
