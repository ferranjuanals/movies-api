package api.movies.backend.application.repository;

import api.movies.backend.domain.entity.Credit;
import api.movies.backend.domain.entity.Movie;
import api.movies.backend.domain.entity.Person;
import api.movies.backend.domain.repository.IPersonRepository;
import api.movies.backend.infrastructure.orm.entity.JpaCredit;
import api.movies.backend.infrastructure.orm.entity.JpaMovie;
import api.movies.backend.infrastructure.orm.entity.JpaPerson;
import api.movies.backend.infrastructure.orm.repository.JpaPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PersonRepository implements IPersonRepository {

    private final JpaPersonRepository jpaPersonRepository;

    @Autowired
    public PersonRepository(JpaPersonRepository jpaPersonRepository) {
        this.jpaPersonRepository = jpaPersonRepository;
    }

    @Override
    public Page<Person> findAll(Pageable pageable) {
        return jpaPersonRepository.findAll(pageable)
                .map(this::mapPersonFromJpaPerson);
    }

    @Override
    public List<Person> findAllByCreditsIn(List<JpaCredit> jpaCredits) {
        return jpaPersonRepository.findAllByCreditsIn(jpaCredits).stream()
                .map(this::mapPersonFromJpaPerson)
                .collect(Collectors.toList());
    }

    private Person mapPersonFromJpaPerson(JpaPerson jpaPerson) {
        return Person.builder()
                .id(jpaPerson.getId())
                .name(jpaPerson.getName())
                .knownFor(jpaPerson.getKnownFor())
                .credits(jpaPerson.getCredits().stream()
                        .map(this::mapCreditFromJpaCredit)
                        .collect(Collectors.toList()))
                .build();
    }

    private Credit mapCreditFromJpaCredit(JpaCredit jpaCredit) {
        return Credit.builder()
                .id(jpaCredit.getId())
                .job(jpaCredit.getJob())
                .character(jpaCredit.getCharacter())
                .movie(mapMovieFromJpaMovie(jpaCredit.getMovie()))
                .build();
    }

    private Movie mapMovieFromJpaMovie(JpaMovie jpaMovie) {
        return Movie.builder()
                .id(jpaMovie.getId())
                .title(jpaMovie.getTitle())
                .build();
    }

}
