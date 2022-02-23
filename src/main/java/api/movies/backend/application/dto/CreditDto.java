package api.movies.backend.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditDto {

    private String creditId;

    private String job;

    private String character;

    private MovieDto movie;

    private PersonDto person;

}
