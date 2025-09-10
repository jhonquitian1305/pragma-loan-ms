package co.com.pragma.model.loan.gateways;

import co.com.pragma.model.dto.UserDTO;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<Long> getByDni(String dni, String token);

    Mono<UserDTO> getDataByDni(String dni, String token);
}
