package co.com.pragma.model.loan_type.gateways;

import reactor.core.publisher.Mono;

public interface LoanTypeRepository {
    Mono<Boolean> existsById(Integer id);
}
