package api.movies.backend.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class Person {

    @NotNull
    private Integer id;

    @NotBlank
    private String name;

    @NotBlank
    private String knownFor;

    @NotEmpty
    private List<Credit> credits;

}
