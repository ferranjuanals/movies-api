package api.movies.backend.infrastructure.orm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "credits")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JpaCredit implements Serializable {

    @Id
    private String id;

    private String creditType;

    private String job;

    private String character;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private JpaMovie movie;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "person_id")
    private JpaPerson person;

}
