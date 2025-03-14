package pedro.almeida.financialcontrol.domain.models;

import pedro.almeida.financialcontrol.domain.errors.TransactionException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

public class Expense extends Transaction {
    public Expense(UUID id, String title, String description, BigDecimal value, Month currentMonth, Integer currentYear, LocalDate date, LocalDateTime timestamp, TransactionCategory category, Boolean hasReceipt) {
        super(id, title, description, value, TransactionType.EXPENSE, currentMonth, currentYear, date, timestamp, category, hasReceipt);
        validCategory(category);
    }

    public void validCategory(TransactionCategory category) {
        if (category == null || category.getName().isEmpty()) {
            throw TransactionException.categoryIsRequired();
        }
    }
}
