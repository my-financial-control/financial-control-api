package pedro.almeida.financialcontrol.web.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import pedro.almeida.financialcontrol.domain.models.Transaction;
import pedro.almeida.financialcontrol.domain.models.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

public record TransactionRequestDTO(@NotBlank String title, String description, @Positive @NotNull BigDecimal value,
                                    @NotBlank String type, @Positive @NotNull Integer currentMonth, @NotNull LocalDate date) {

    public Transaction toTransaction() {
        return new Transaction(title, description, value, TransactionType.valueOf(type), Month.of(currentMonth), date);
    }

}
