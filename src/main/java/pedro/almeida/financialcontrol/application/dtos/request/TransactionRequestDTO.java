package pedro.almeida.financialcontrol.application.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionRequestDTO(@NotBlank String title, String description, @Positive @NotNull BigDecimal value,
                                    @NotBlank String type, @Positive @NotNull Integer currentMonth,
                                    @NotNull LocalDate date, String categoryId) {
}
