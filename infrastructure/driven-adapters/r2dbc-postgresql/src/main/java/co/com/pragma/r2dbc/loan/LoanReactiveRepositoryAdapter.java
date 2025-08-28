package co.com.pragma.r2dbc.loan;

import co.com.pragma.model.loan.Loan;
import co.com.pragma.model.loan.gateways.LoanRepository;
import co.com.pragma.r2dbc.entities.LoanEntity;
import co.com.pragma.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class LoanReactiveRepositoryAdapter extends ReactiveAdapterOperations<
    Loan,
    LoanEntity,
    Long,
    LoanReactiveRepository
> implements LoanRepository {
    public LoanReactiveRepositoryAdapter(LoanReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Loan.class));
    }

    @Override
    public Mono<Loan> createOne(Loan loan) {
        return super.save(loan);
    }
}
