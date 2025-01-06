package pedro.almeida.financialcontrol.web.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import pedro.almeida.financialcontrol.domain.factories.TransactionFactory;
import pedro.almeida.financialcontrol.domain.models.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionRequestDTO(@NotBlank String title, String description, @Positive @NotNull BigDecimal value,
                                    @NotBlank String type, @Positive @NotNull Integer currentMonth,
                                    @NotNull LocalDate date, String category) {

    public Transaction toTransaction() {
        return TransactionFactory.buildTransaction(title, description, value, type, currentMonth, date, category);
    }
}
