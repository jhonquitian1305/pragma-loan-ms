package co.com.pragma.api.exception;

import co.com.pragma.usecase.loan.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class GlobalExceptionFilter implements HandlerFilterFunction<ServerResponse, ServerResponse> {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionFilter.class);

    @Override
    public Mono<ServerResponse> filter(ServerRequest request, HandlerFunction<ServerResponse> next) {
        return next.handle(request)
                .onErrorResume(NotFoundException.class, ex -> {

                    logger.info("NotFoundException: {}", ex.getMessage(), ex);

                    return ServerResponse.status(400).bodyValue(
                            ErrorResponse.builder()
                                    .tittle("User not found")
                                    .message(ex.getMessage())
                                    .status(400)
                                    .build()
                    );
                })
                .onErrorResume(Throwable.class, ex -> {
                    logger.error("Unexpected error: {}", ex.getMessage(), ex);

                    return ServerResponse.status(500).bodyValue(
                            ErrorResponse.builder()
                                    .tittle("Internal Server Error")
                                    .message("Ocurrió un error inesperado, intente más tarde")
                                    .status(500)
                                    .build()
                    );
                });
    }
}
