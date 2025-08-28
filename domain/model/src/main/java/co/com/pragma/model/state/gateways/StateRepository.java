package co.com.pragma.model.state.gateways;

import reactor.core.publisher.Mono;

public interface StateRepository {
    Mono<Boolean> existsById(Long id);
}
