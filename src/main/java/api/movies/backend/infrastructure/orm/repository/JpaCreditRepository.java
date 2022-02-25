package api.movies.backend.infrastructure.orm.repository;

import api.movies.backend.infrastructure.orm.entity.JpaCredit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaCreditRepository extends JpaRepository<JpaCredit, String> {

    List<JpaCredit> findAllByJob(String job);

    List<JpaCredit> findAllByPersonName(String name);

    List<JpaCredit> findAllByJobAndPersonName(String job, String personName);

}
