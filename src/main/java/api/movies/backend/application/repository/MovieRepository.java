package api.movies.backend.application.repository;

import api.movies.backend.domain.entity.Credit;
import api.movies.backend.domain.entity.Movie;
import api.movies.backend.domain.entity.Person;
import api.movies.backend.domain.repository.IMovieRepository;
import api.movies.backend.infrastructure.orm.entity.JpaCredit;
import api.movies.backend.infrastructure.orm.entity.JpaMovie;
import api.movies.backend.infrastructure.orm.entity.JpaPerson;
import api.movies.backend.infrastructure.orm.repository.JpaMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MovieRepository implements IMovieRepository {

    JpaMovieRepository jpaMovieRepository;

    @Autowired
    public MovieRepository(JpaMovieRepository jpaMovieRepository) {
        this.jpaMovieRepository = jpaMovieRepository;
    }

    @Override
    public Movie save(Movie movie) {
        JpaMovie jpaMovie = mapJpaMovieFromMovie(movie);
        return mapMovieFromJpaMovie(jpaMovieRepository.save(jpaMovie));
    }

    @Override
    public Page<Movie> findAll(Pageable pageable) {
        return jpaMovieRepository.findAll(pageable)
                .map(this::mapMovieFromJpaMovie);
    }

    @Override
    public Page<Movie> findAllByYear(List<LocalDate> daysOfYear, Pageable pageable) {
        return jpaMovieRepository.findAllByReleaseDateBetween(daysOfYear.get(0), daysOfYear.get(1), pageable)
                .map(this::mapMovieFromJpaMovie);
    }

    @Override
    public Page<Movie> findAllByTitle(String string, Pageable pageable) {
        return jpaMovieRepository.findAllByTitleContains(string, pageable)
                .map(this::mapMovieFromJpaMovie);
    }

    @Override
    public Page<Movie> findAllByYearAndTitle(List<LocalDate> daysOfYear, String string, Pageable pageable) {
        return jpaMovieRepository.findAllByReleaseDateBetweenAndTitleContains(daysOfYear.get(0), daysOfYear.get(1), string, pageable)
                .map(this::mapMovieFromJpaMovie);
    }

    private JpaMovie mapJpaMovieFromMovie(Movie movie) {
        JpaMovie jpaMovie = JpaMovie.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .rating(movie.getRating())
                .releaseDate(movie.getReleaseDate())
                .build();
        List<JpaCredit> jpaCredits = getJpaCreditsFromMovie(movie.getCredits(), jpaMovie);
        jpaMovie.setCredits(jpaCredits);
        return jpaMovie;
    }

    private List<JpaCredit> getJpaCreditsFromMovie(List<Credit> credits, JpaMovie jpaMovie) {
        return credits.stream()
                .map(credit -> mapJpaCreditFromCredit(credit, jpaMovie))
                .collect(Collectors.toList());
    }

    private JpaCredit mapJpaCreditFromCredit(Credit credit, JpaMovie jpaMovie) {
        return JpaCredit.builder()
                .id(credit.getId())
                .creditType(credit.getCreditType())
                .job(credit.getJob())
                .character(credit.getCharacter())
                .movie(jpaMovie)
                .person(mapJpaPersonFromPerson(credit.getPerson()))
                .build();
    }

    private JpaPerson mapJpaPersonFromPerson(Person person) {
        return JpaPerson.builder()
                .id(person.getId())
                .name(person.getName())
                .knownFor(person.getKnownFor())
                .build();
    }

    private Movie mapMovieFromJpaMovie(JpaMovie jpaMovie) {
        return Movie.builder()
                .id(jpaMovie.getId())
                .title(jpaMovie.getTitle())
                .description(jpaMovie.getDescription())
                .rating(jpaMovie.getRating())
                .releaseDate(jpaMovie.getReleaseDate())
                .credits(jpaMovie.getCredits().stream()
                        .map(this::mapCreditFromJpaCredit)
                        .collect(Collectors.toList()))
                .build();
    }

    private Credit mapCreditFromJpaCredit(JpaCredit jpaCredit) {
        return Credit.builder()
                .id(jpaCredit.getId())
                .job(jpaCredit.getJob())
                .character(jpaCredit.getCharacter())
                .person(mapPersonFromJpaPerson(jpaCredit.getPerson()))
                .build();
    }

    private Person mapPersonFromJpaPerson(JpaPerson jpaPerson) {
        return Person.builder()
                .id(jpaPerson.getId())
                .name(jpaPerson.getName())
                .build();
    }

}
