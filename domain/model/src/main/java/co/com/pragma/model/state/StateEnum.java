package co.com.pragma.model.state;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StateEnum {
    PENDIENTE_REVISION(1L, "Pendiente de revisión");

    private Long id;
    private String name;
}
