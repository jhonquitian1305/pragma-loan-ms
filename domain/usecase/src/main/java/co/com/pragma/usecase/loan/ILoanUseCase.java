package co.com.pragma.usecase.loan;

import co.com.pragma.model.dto.InfoLoanUserDTO;
import co.com.pragma.model.dto.PageResponseDTO;
import co.com.pragma.model.loan.Loan;
import reactor.core.publisher.Mono;

public interface ILoanUseCase {
    Mono<Loan> createOne(Loan loan, String token);
    Mono<PageResponseDTO<InfoLoanUserDTO>> findAllLoans(Long state, int page, int size, String token);
}
