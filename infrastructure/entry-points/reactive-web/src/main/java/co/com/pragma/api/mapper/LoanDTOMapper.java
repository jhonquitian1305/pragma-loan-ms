package co.com.pragma.api.mapper;

import co.com.pragma.api.dto.CreateLoanDTO;
import co.com.pragma.model.loan.Loan;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanDTOMapper {
    Loan toModel(CreateLoanDTO createLoanDTO);
}
