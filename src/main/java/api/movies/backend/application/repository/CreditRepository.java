package api.movies.backend.application.repository;

import api.movies.backend.domain.repository.ICreditRepository;
import api.movies.backend.infrastructure.orm.entity.JpaCredit;
import api.movies.backend.infrastructure.orm.repository.JpaCreditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CreditRepository implements ICreditRepository {

    private final JpaCreditRepository jpaCreditRepository;

    @Autowired
    public CreditRepository(JpaCreditRepository jpaCreditRepository) {
        this.jpaCreditRepository = jpaCreditRepository;
    }

    @Override
    public List<JpaCredit> findAllByJob(String job) {
        return jpaCreditRepository.findAllByJob(job);
    }

}
