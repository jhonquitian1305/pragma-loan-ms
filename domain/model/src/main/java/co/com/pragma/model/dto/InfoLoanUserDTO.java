package co.com.pragma.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class InfoLoanUserDTO {
    private Long id;
    private Double amount;
    private Integer term;
    private Long idLoanType;
    private Long idState;
    private String stateName;
    private String typeLoanName;
    private Double interestRate;
    private Double payAmountMonth;
    private String dniUser;
    private UserDTO user;
}
