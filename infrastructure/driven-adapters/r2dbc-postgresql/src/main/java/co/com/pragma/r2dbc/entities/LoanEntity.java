package co.com.pragma.r2dbc.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table("loans")
public class LoanEntity {

    @Id
    private Long id;
    private double amount;
    private int term;
    private String dniUser;
    private Long idLoanType;
    private Long idState;
}
