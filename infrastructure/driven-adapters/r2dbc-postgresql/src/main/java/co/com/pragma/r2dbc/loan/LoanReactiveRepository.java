package co.com.pragma.r2dbc.loan;

import co.com.pragma.r2dbc.entities.LoanEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface LoanReactiveRepository extends ReactiveCrudRepository<LoanEntity, Long>, ReactiveQueryByExampleExecutor<LoanEntity> {

    @Query("""
        SELECT COUNT(*) FROM loans l
        INNER JOIN states s ON l.id_state = s.id
        WHERE (:state is null or s.id = :state)
    """)
    Mono<Long> countAll(Long state);
}