package api.movies.backend.domain.repository;

import api.movies.backend.domain.entity.Person;
import api.movies.backend.infrastructure.orm.entity.JpaCredit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPersonRepository {

    Page<Person> findAll(Pageable pageable);

    List<Person> findAllByCreditsIn(List<JpaCredit> jpaCredits);

}
