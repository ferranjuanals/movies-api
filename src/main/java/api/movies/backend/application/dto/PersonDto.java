package api.movies.backend.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonDto {

    private Integer personId;

    private String name;

    private String knownFor;

    private List<CreditDto> credits;

}
