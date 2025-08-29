package co.com.pragma.usecase.loan;

import co.com.pragma.model.loan.Loan;
import co.com.pragma.model.loan.gateways.LoanRepository;
import co.com.pragma.model.loan.gateways.UserRepository;
import co.com.pragma.model.loan_type.gateways.LoanTypeRepository;
import co.com.pragma.model.state.StateEnum;
import co.com.pragma.model.state.gateways.StateRepository;
import co.com.pragma.usecase.loan.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class LoanUseCase {

    private final LoanRepository loanRepository;
    private final LoanTypeRepository loanTypeRepository;
    private final StateRepository stateRepository;
    private final UserRepository userRepository;

    Mono<Loan> createOne(Loan loan){
        Long idState = StateEnum.PENDIENTE_REVISION.getId();

        return this.userRepository.getByDni(loan.getDniUser())
                .switchIfEmpty(Mono.error(new Throwable()))
                .flatMap(id ->
                    loanTypeRepository.existsById(loan.getIdLoanType())
                    .flatMap(foundLoanType -> {
                        if(Boolean.TRUE.equals(foundLoanType)){
                            return Mono.error(new NotFoundException("Loan type with id %s doesn't exist".formatted(loan.getIdLoanType())));
                        }

                        return this.stateRepository.existsById(idState);
                    })
                    .flatMap(foundState -> {
                        if(foundState){
                            return Mono.error(new NotFoundException("Loan type with id %s doesn't exist".formatted(idState)));
                        }

                        return this.loanRepository.createOne(loan);
                    })
                );
    }
}
