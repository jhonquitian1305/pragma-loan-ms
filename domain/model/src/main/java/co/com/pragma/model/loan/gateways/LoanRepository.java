package co.com.pragma.model.loan.gateways;

import co.com.pragma.model.dto.InfoLoanUserDTO;
import co.com.pragma.model.dto.PageResponseDTO;
import co.com.pragma.model.loan.Loan;
import reactor.core.publisher.Mono;

public interface LoanRepository {
    Mono<Loan> createOne(Loan loan);

    Mono<PageResponseDTO<InfoLoanUserDTO>> findAllFiltered(Long state, int page, int size);
}
