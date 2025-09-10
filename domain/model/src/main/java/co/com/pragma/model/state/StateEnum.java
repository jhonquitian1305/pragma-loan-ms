package co.com.pragma.model.state;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
@Getter
public enum StateEnum {
    PENDIENTE_REVISION(1L, "Pendiente d e revisión"),
    RECHAZADAS(2L, "Rechazadas"),
    REVISION_MANUAL(3L, "Revisión manual");

    private Long id;
    private String name;

    public static Optional<Long> getById(Long id){
        return Arrays.stream(values())
                .map(StateEnum::getId)
                .filter(stateId -> stateId.equals(id))
                .findFirst();
    }
}
