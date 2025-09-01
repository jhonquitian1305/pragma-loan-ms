package co.com.pragma.r2dbc.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table("loan_types")
public class LoanTypeEntity {

    @Id
    private Long id;
    private String name;
    private Double minAmount;
    private Double maxAmount;
    private Double interestRate;
    private boolean automaticValidation;
}
