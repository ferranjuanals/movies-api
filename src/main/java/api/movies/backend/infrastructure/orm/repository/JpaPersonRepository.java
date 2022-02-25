package api.movies.backend.infrastructure.orm.repository;

import api.movies.backend.infrastructure.orm.entity.JpaCredit;
import api.movies.backend.infrastructure.orm.entity.JpaPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaPersonRepository extends JpaRepository<JpaPerson, Integer> {

    List<JpaPerson> findAllByCreditsIn(List<JpaCredit> jpaCredits);

}
