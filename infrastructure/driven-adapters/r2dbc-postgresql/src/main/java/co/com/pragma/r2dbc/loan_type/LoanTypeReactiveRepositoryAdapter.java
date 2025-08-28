package co.com.pragma.r2dbc.loan_type;

import co.com.pragma.model.loan_type.LoanType;
import co.com.pragma.model.loan_type.gateways.LoanTypeRepository;
import co.com.pragma.r2dbc.entities.LoanTypeEntity;
import co.com.pragma.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class LoanTypeReactiveRepositoryAdapter extends ReactiveAdapterOperations<
    LoanType,
    LoanTypeEntity,
    Long,
    LoanTypeReactiveRepository
> implements LoanTypeRepository {
    public LoanTypeReactiveRepositoryAdapter(LoanTypeReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, LoanType.class));
    }

    @Override
    public Mono<Boolean> existsById(Long id) {
        return this.repository.existsById(id);
    }
}
