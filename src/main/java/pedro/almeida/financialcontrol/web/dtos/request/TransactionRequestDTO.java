package pedro.almeida.financialcontrol.web.dtos.request;

import pedro.almeida.financialcontrol.domain.models.Transaction;
import pedro.almeida.financialcontrol.domain.models.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

public record TransactionRequestDTO(String title, String description, BigDecimal value, String type, Integer currentMonth, LocalDate date) {

    public Transaction toTransaction() {
        return new Transaction(title, description, value, TransactionType.valueOf(type), Month.of(currentMonth), date);
    }

}
