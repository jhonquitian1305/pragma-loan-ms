package co.com.pragma.model.loan;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Loan {
    private Long id;
    private double amount;
    private int term;
    private String dniUser;
    private Integer idLoanType;
    private Integer idState;
}
