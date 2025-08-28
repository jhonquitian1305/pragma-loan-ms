package co.com.pragma.r2dbc.loan;

import co.com.pragma.r2dbc.entities.LoanEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface LoanReactiveRepository extends ReactiveCrudRepository<LoanEntity, Long>, ReactiveQueryByExampleExecutor<LoanEntity> {

}
