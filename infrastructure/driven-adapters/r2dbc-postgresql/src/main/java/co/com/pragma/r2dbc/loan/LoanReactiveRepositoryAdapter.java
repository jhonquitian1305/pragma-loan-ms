package co.com.pragma.r2dbc.loan;

import co.com.pragma.model.dto.InfoLoanUserDTO;
import co.com.pragma.model.dto.PageResponseDTO;
import co.com.pragma.model.loan.Loan;
import co.com.pragma.model.loan.gateways.LoanRepository;
import co.com.pragma.r2dbc.entities.LoanEntity;
import co.com.pragma.r2dbc.helper.ReactiveAdapterOperations;
import co.com.pragma.r2dbc.utils.LoanQueryBuilder;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Repository
public class LoanReactiveRepositoryAdapter extends ReactiveAdapterOperations<
    Loan,
    LoanEntity,
    Long,
    LoanReactiveRepository
> implements LoanRepository {

    private final DatabaseClient client;

    private final LoanQueryBuilder queryBuilder;

    public LoanReactiveRepositoryAdapter(LoanReactiveRepository repository, ObjectMapper mapper, DatabaseClient client,
                                         LoanQueryBuilder queryBuilder) {
        super(repository, mapper, d -> mapper.map(d, Loan.class));
        this.client = client;
        this.queryBuilder = queryBuilder;
    }

    @Override
    public Mono<Loan> createOne(Loan loan) {
        return super.save(loan);
    }

    @Override
    public Mono<PageResponseDTO<InfoLoanUserDTO>> findAllFiltered(Long state, int page, int size) {

        Map<String, Object> params = new HashMap<>();

        if(state != null){
            params.put("state", state);
        }

        params.put("limit", size);
        params.put("offset", page * size);

        String sql = queryBuilder.findAllInfo(params);

        Mono<Long> totalQuery = repository.countAll(state);

        Flux<InfoLoanUserDTO> contentFlux = client.sql(sql)
                .bindValues(params)
                .map((row, meta) ->
                    InfoLoanUserDTO.builder()
                            .id(row.get("id", Long.class))
                            .amount(row.get("amount", Double.class))
                            .term(row.get("term", Integer.class))
                            .idState(row.get("id_state", Long.class))
                            .stateName(row.get("state_name", String.class))
                            .idLoanType(row.get("id_loan_type", Long.class))
                            .typeLoanName(row.get("name", String.class))
                            .interestRate(row.get("interest_rate", Double.class))
                            .dniUser(row.get("dni_user", String.class))
                            .build()
                )
                .all();

        return totalQuery.flatMap(total ->
                contentFlux.collectList().map(content -> {
                int totalPages = (int) Math.ceil((double) total / size);
                return new PageResponseDTO<>(
                        total,
                        totalPages,
                        page,
                        size,
                        content
                );
            })
        );

    }
}
