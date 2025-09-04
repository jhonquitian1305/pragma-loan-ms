package co.com.pragma.model.loan.gateways;

import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<Long> getByDni(String dni, String token);
}
