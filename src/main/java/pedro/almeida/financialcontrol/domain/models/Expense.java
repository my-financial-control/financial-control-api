package pedro.almeida.financialcontrol.domain.models;

import pedro.almeida.financialcontrol.domain.errors.TransactionException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

public class Expense extends Transaction {
    public Expense(UUID id, String title, String description, BigDecimal value, Month currentMonth, LocalDate date, LocalDateTime timestamp, String category) {
        super(id, title, description, value, TransactionType.EXPENSE, currentMonth, date, timestamp, category);
        validCategory(category);
    }

    public void validCategory(String category) {
        if (category == null || category.isEmpty()) {
            throw TransactionException.categoryIsRequired();
        }
    }
}
