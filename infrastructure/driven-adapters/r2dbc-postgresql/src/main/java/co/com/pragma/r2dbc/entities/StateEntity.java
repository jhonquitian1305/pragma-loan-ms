package co.com.pragma.r2dbc.entities;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table("states")
public class StateEntity {
    @Id
    private Integer id;
    private String name;
    private String description;
}
