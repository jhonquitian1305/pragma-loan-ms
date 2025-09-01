package co.com.pragma.usecase.loan;

import co.com.pragma.model.loan.Loan;
import co.com.pragma.model.loan.gateways.LoanRepository;
import co.com.pragma.model.loan.gateways.UserRepository;
import co.com.pragma.model.loan_type.gateways.LoanTypeRepository;
import co.com.pragma.model.state.StateEnum;
import co.com.pragma.model.state.gateways.StateRepository;
import co.com.pragma.usecase.loan.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class LoanUseCaseTest {
    @Mock
    private LoanRepository loanRepository;

    @Mock
    private LoanTypeRepository loanTypeRepository;

    @Mock
    private StateRepository stateRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LoanUseCase loanUseCase;

    private Loan loan;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        loan = new Loan();
        loan.setIdLoanType(1L);
        loan.setDniUser("123456");
    }

    @Test
    void shouldCreateLoanSuccessfully() {
        when(userRepository.getByDni("123456")).thenReturn(Mono.just(1L)); // el usuario existe
        when(loanTypeRepository.existsById(1L)).thenReturn(Mono.just(true)); // el loanType existe
        when(stateRepository.existsById(StateEnum.PENDIENTE_REVISION.getId())).thenReturn(Mono.just(true)); // state existe
        when(loanRepository.createOne(any(Loan.class))).thenReturn(Mono.just(loan));

        StepVerifier.create(loanUseCase.createOne(loan))
                .expectNextMatches(savedLoan ->
                        savedLoan.getIdState().equals(StateEnum.PENDIENTE_REVISION.getId())
                                && savedLoan.getDniUser().equals("123456")
                )
                .verifyComplete();
    }

    @Test
    void shouldFailWhenUserNotFound() {
        when(userRepository.getByDni("123456")).thenReturn(Mono.empty());

        StepVerifier.create(loanUseCase.createOne(loan))
                .expectErrorMatches(throwable ->
                        throwable instanceof NotFoundException &&
                                throwable.getMessage().equals("User with dni 123456 not found"))
                .verify();
    }

    @Test
    @DisplayName("should fail when Loan Type Not Exists")
    void shouldFailWhenLoanTypeNotExists() {
        when(userRepository.getByDni("123456")).thenReturn(Mono.just(1L));
        when(loanTypeRepository.existsById(1L)).thenReturn(Mono.just(false));

        StepVerifier.create(loanUseCase.createOne(loan))
                .expectErrorMatches(throwable ->
                        throwable instanceof NotFoundException &&
                                throwable.getMessage().equals("Loan type with id 1 doesn't exist"))
                .verify();
    }

    @Test
    void shouldFailWhenStateNotExists() {
        Long idState = StateEnum.PENDIENTE_REVISION.getId();

        when(userRepository.getByDni("123456")).thenReturn(Mono.just(1L));
        when(loanTypeRepository.existsById(1L)).thenReturn(Mono.just(true));
        when(stateRepository.existsById(idState)).thenReturn(Mono.just(false));

        StepVerifier.create(loanUseCase.createOne(loan))
                .expectErrorMatches(throwable ->
                        throwable instanceof NotFoundException &&
                                throwable.getMessage().equals("Loan type with id " + idState + " doesn't exist"))
                .verify();
    }
}
