package co.com.pragma.usecase.loan;

import co.com.pragma.model.dto.InfoLoanUserDTO;
import co.com.pragma.model.dto.PageResponseDTO;
import co.com.pragma.model.dto.UserDTO;
import co.com.pragma.model.loan.Loan;
import co.com.pragma.model.loan.gateways.LoanRepository;
import co.com.pragma.model.loan.gateways.UserRepository;
import co.com.pragma.model.loan_type.gateways.LoanTypeRepository;
import co.com.pragma.model.state.StateEnum;
import co.com.pragma.model.state.gateways.StateRepository;
import co.com.pragma.usecase.loan.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RequiredArgsConstructor
public class LoanUseCase implements ILoanUseCase {

    private final LoanRepository loanRepository;
    private final LoanTypeRepository loanTypeRepository;
    private final StateRepository stateRepository;
    private final UserRepository userRepository;

    public Mono<Loan> createOne(Loan loan, String token){
        Long idState = StateEnum.PENDIENTE_REVISION.getId();

        return this.userRepository.getByDni(loan.getDniUser(), token)
                .switchIfEmpty(Mono.error(new NotFoundException("User with dni %s not found".formatted(loan.getDniUser()))))
                .flatMap(id ->
                    loanTypeRepository.existsById(loan.getIdLoanType())
                    .flatMap(foundLoanType -> {
                        if(Boolean.FALSE.equals(foundLoanType)){
                            return Mono.error(new NotFoundException("Loan type with id %s doesn't exist".formatted(loan.getIdLoanType())));
                        }

                        return this.stateRepository.existsById(idState);
                    })
                    .flatMap(foundState -> {
                        if(Boolean.FALSE.equals(foundState)){
                            return Mono.error(new NotFoundException("Loan type with id %s doesn't exist".formatted(idState)));
                        }

                        loan.setIdState(idState);
                        return this.loanRepository.createOne(loan);
                    })
                );
    }

    @Override
    public Mono<PageResponseDTO<InfoLoanUserDTO>> findAllLoans(Long state, int page, int size, String token) {

        if(state != null){
            Optional<Long> idState = StateEnum.getById(state);
            if(idState.isEmpty()){
                return Mono.error(new NotFoundException("State not found"));
            }
        }

        return this.loanRepository.findAllFiltered(state, page, size)
                .flatMap(pageResponse ->
                    Flux.fromIterable(pageResponse.content())
                            .flatMap(infoLoanUserDTO -> this.userRepository.getDataByDni(infoLoanUserDTO.getDniUser(), token)
                                    .switchIfEmpty(Mono.error(new NotFoundException("User not found")))
                                    .map(userDTO -> this.addUserToContent(infoLoanUserDTO, userDTO))
                            )
                            .collectList()
                            .map(infoLoanUser -> new PageResponseDTO<>(
                                    pageResponse.totalElements(),
                                    pageResponse.totalPages(),
                                    pageResponse.page(),
                                    pageResponse.size(),
                                    infoLoanUser
                            ))
                );
    }

    private InfoLoanUserDTO addUserToContent(InfoLoanUserDTO infoLoanUserDTO, UserDTO userDTO) {

        Double amount = infoLoanUserDTO.getAmount();
        Integer term = infoLoanUserDTO.getTerm();
        Double interestRate = infoLoanUserDTO.getInterestRate() / 100;
        Double payAmountMonth = (amount + (amount * interestRate)) / term;

        infoLoanUserDTO.setUser(userDTO);
        infoLoanUserDTO.setPayAmountMonth(payAmountMonth);
        return infoLoanUserDTO;
    }
}
