package pedro.almeida.financialcontrol.application.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import pedro.almeida.financialcontrol.domain.models.Borrower;
import pedro.almeida.financialcontrol.domain.models.Borrowing;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BorrowingRequestDTO(
    @NotBlank String borrower,
    @Positive @NotNull BigDecimal value,
    @NotBlank String description,
    @NotNull LocalDate date
) {

    public Borrowing toBorrowing() {
        if (date != null) {
            return new Borrowing(new Borrower(borrower), value, description, date, false);
        } else {
            return new Borrowing(new Borrower(borrower), value, description);
        }
    }

    public Borrowing toBorrowing(boolean hasReceipt) {
        if (date != null) {
            return new Borrowing(new Borrower(borrower), value, description, date, hasReceipt);
        } else {
            return new Borrowing(new Borrower(borrower), value, description);
        }
    }
}
