package co.com.pragma.model.state;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class State {
    private Integer id;
    private String name;
    private String description;
}
