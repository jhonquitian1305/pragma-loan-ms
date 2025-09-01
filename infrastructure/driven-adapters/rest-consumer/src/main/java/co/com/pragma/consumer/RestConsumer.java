package co.com.pragma.consumer;

import co.com.pragma.model.loan.gateways.UserRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RestConsumer implements UserRepository {
    private final WebClient client;

    private final Logger logger = LoggerFactory.getLogger(RestConsumer.class);

    @Override
    @CircuitBreaker(name = "getUser" , fallbackMethod = "getUserByDniFallback")
    public Mono<Long> getByDni(String dni) {
        logger.info("get user with dni {}", dni);
        return client
                .get()
                .uri("/users/{dni}", dni)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        clientResponse -> {
                            logger.warn("user with dni {} not found", dni);
                            return Mono.empty();
                        })
                .onStatus(HttpStatusCode::is5xxServerError,
                        clientResponse -> {
                            logger.error("External service failed user {}", dni);
                            return Mono.error(new RuntimeException("Contact to admin"));
                        })
                .bodyToMono(Long.class)
                .doOnError(e -> logger.error("Error calling external service user {}", dni, e));
    }

    private Mono<Long> getUserByDniFallback(String dni, Exception exception) {
        logger.error("fallback error dni={}, error={}", dni, exception.getMessage());
        return Mono.empty();
    }
}
