package api.movies.backend.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TmdbCreditDto {

    private String creditId;

    private Integer id;

    private String name;

    private String knownForDepartment;

    private String job;

    private String character;

    private Integer order;

}
