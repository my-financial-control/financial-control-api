package pedro.almeida.financialcontrol.domain.factories;

import pedro.almeida.financialcontrol.domain.errors.TransactionException;
import pedro.almeida.financialcontrol.domain.models.Credit;
import pedro.almeida.financialcontrol.domain.models.Expense;
import pedro.almeida.financialcontrol.domain.models.Transaction;
import pedro.almeida.financialcontrol.domain.models.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

public class TransactionFactory {
    private TransactionFactory() {
    }

    public static Transaction buildTransaction(UUID id, String title, String description, BigDecimal value, String type, Integer currentMonth, LocalDate date, LocalDateTime timestamp, String category) {
        if (type.equals(TransactionType.CREDIT.name())) {
            return new Credit(id, title, description, value, Month.of(currentMonth), date, timestamp);
        }
        if (type.equals(TransactionType.EXPENSE.name())) {
            return new Expense(id, title, description, value, Month.of(currentMonth), date, timestamp, category);
        }
        throw TransactionException.invalidTransactionType();
    }

    public static Transaction buildTransaction(String title, String description, BigDecimal value, String type, Integer currentMonth, LocalDate date, String category) {
        LocalDateTime timestamp = LocalDateTime.now();
        if (type.equals(TransactionType.CREDIT.name())) {
            return new Credit(UUID.randomUUID(), title, description, value, Month.of(currentMonth), date, timestamp);
        }
        if (type.equals(TransactionType.EXPENSE.name())) {
            return new Expense(UUID.randomUUID(), title, description, value, Month.of(currentMonth), date, timestamp, category);
        }
        throw TransactionException.invalidTransactionType();
    }
}
