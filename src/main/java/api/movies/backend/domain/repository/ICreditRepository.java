package api.movies.backend.domain.repository;

import api.movies.backend.infrastructure.orm.entity.JpaCredit;

import java.util.List;

public interface ICreditRepository {

    List<JpaCredit> findAllByJob(String job);

}
