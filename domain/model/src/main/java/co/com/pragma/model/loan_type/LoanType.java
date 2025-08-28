package co.com.pragma.model.loan_type;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class LoanType {
    private Integer id;
    private String name;
    private Double minAmount;
    private Double maxAmount;
    private Double interestRate;
    private boolean automaticValidation;
}
