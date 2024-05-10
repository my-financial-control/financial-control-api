package pedro.almeida.financialcontrol.web.dtos.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import pedro.almeida.financialcontrol.domain.models.ParcelBorrowing;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PayParcelBorrowingRequestDTO(@Positive @NotNull BigDecimal value, @NotNull LocalDate date) {

    public ParcelBorrowing toParcelBorrowing() {
        if (this.date != null) {
            return new ParcelBorrowing(this.value, this.date);
        } else {
            return new ParcelBorrowing(this.value);
        }
    }

}
