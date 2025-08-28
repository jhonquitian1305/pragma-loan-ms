package co.com.pragma.r2dbc.entities;

import jakarta.persistence.Id;
import lombok.*;
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
    private Integer idLoanType;
    private Integer idState;
}
