package co.com.pragma.r2dbc.state;

import co.com.pragma.model.state.State;
import co.com.pragma.model.state.gateways.StateRepository;
import co.com.pragma.r2dbc.entities.StateEntity;
import co.com.pragma.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class StateReactiveRepositoryAdapter extends ReactiveAdapterOperations<
    State,
    StateEntity,
    Long,
    StateReactiveRepository
> implements StateRepository {
    public StateReactiveRepositoryAdapter(StateReactiveRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, State.class/* change for domain model */));
    }

    @Override
    public Mono<Boolean> existsById(Long id) {
        return this.repository.existsById(id);
    }
}
