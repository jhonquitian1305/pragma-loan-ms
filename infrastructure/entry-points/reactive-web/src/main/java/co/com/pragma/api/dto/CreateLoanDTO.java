package co.com.pragma.api.dto;

public record CreateLoanDTO(
    double amount,
    int term,
    String dniUser,
    Long idLoanType
) {
}
