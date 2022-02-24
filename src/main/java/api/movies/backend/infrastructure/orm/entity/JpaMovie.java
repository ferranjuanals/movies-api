package api.movies.backend.infrastructure.orm.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "movies")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JpaMovie implements Serializable {

    @Id
    private Integer id;

    private String title;

    @Column(columnDefinition="TEXT")
    private String description;

    private Double rating;

    private LocalDate releaseDate;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<JpaCredit> credits;

}
