package pedro.almeida.financialcontrol.application.dtos.response;

import java.math.BigDecimal;

public record CalculateTotalsResponseDTO(
        BigDecimal credits,
        BigDecimal expenses
) {
}
