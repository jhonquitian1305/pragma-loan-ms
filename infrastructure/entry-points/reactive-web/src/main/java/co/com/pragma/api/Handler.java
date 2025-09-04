package co.com.pragma.api;

import co.com.pragma.api.dto.CreateLoanDTO;
import co.com.pragma.api.mapper.LoanDTOMapper;
import co.com.pragma.usecase.loan.ILoanUseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class Handler {

    private final ILoanUseCase loanUseCase;
    private final LoanDTOMapper loanDTOMapper;

    private final Logger logger = LoggerFactory.getLogger(Handler.class);

    public Mono<ServerResponse> createOne(ServerRequest serverRequest) {
        String token = serverRequest.headers().firstHeader(HttpHeaders.AUTHORIZATION);
        return serverRequest.bodyToMono(CreateLoanDTO.class)
                .doOnNext(createLoanDTO -> logger.info("beginning register a loan"))
                .map(this.loanDTOMapper::toModel)
                .flatMap(loan -> this.loanUseCase.createOne(loan, token))
                .doOnNext(loan -> logger.info("loan created with id {}", loan.getId()))
                .flatMap(loan -> ServerResponse.created(URI.create("/api/v1/requests")).bodyValue(loan));
    }
}
