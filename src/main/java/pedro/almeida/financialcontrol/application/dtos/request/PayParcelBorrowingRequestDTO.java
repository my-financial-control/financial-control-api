package pedro.almeida.financialcontrol.application.dtos.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import pedro.almeida.financialcontrol.domain.models.ParcelBorrowing;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PayParcelBorrowingRequestDTO(@Positive @NotNull BigDecimal value, @NotNull LocalDate date) {

    public ParcelBorrowing toParcelBorrowing() {
        if (date != null) {
            return new ParcelBorrowing(value, date);
        } else {
            return new ParcelBorrowing(value);
        }
    }

}
