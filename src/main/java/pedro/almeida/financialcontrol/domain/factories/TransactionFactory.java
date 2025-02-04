package pedro.almeida.financialcontrol.domain.factories;

import pedro.almeida.financialcontrol.domain.errors.TransactionException;
import pedro.almeida.financialcontrol.domain.models.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

public class TransactionFactory {
    private TransactionFactory() {
    }

    public static Transaction buildTransaction(UUID id, String title, String description, BigDecimal value, String type, Integer currentMonth, LocalDate date, LocalDateTime timestamp, TransactionCategory category) {
        if (type.equals(TransactionType.CREDIT.name())) {
            return new Credit(id, title, description, value, Month.of(currentMonth), date, timestamp, category);
        }
        if (type.equals(TransactionType.EXPENSE.name())) {
            return new Expense(id, title, description, value, Month.of(currentMonth), date, timestamp, category);
        }
        throw TransactionException.invalidTransactionType();
    }

    public static Transaction buildTransaction(String title, String description, BigDecimal value, String type, Integer currentMonth, LocalDate date, TransactionCategory category) {
        LocalDateTime timestamp = LocalDateTime.now();
        if (type.equals(TransactionType.CREDIT.name())) {
            return new Credit(UUID.randomUUID(), title, description, value, Month.of(currentMonth), date, timestamp, category);
        }
        if (type.equals(TransactionType.EXPENSE.name())) {
            return new Expense(UUID.randomUUID(), title, description, value, Month.of(currentMonth), date, timestamp, category);
        }
        throw TransactionException.invalidTransactionType();
    }
}
