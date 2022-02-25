package api.movies.backend.infrastructure.orm.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity(name = "people")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JpaPerson {

    @Id
    private Integer id;

    private String name;

    private String knownFor;

    @OneToMany(mappedBy = "person")
    private List<JpaCredit> credits;
}
