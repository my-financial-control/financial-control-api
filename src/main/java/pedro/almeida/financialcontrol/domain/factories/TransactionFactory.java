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

    public static Transaction buildTransaction(UUID id, String title, String description, BigDecimal value, String type, Integer currentMonth, Integer currentYear, LocalDate date, LocalDateTime timestamp, TransactionCategory category) {
        if (type.equals(TransactionType.CREDIT.name())) {
            return new Credit(id, title, description, value, Month.of(currentMonth), currentYear, date, timestamp, category, false);
        }
        if (type.equals(TransactionType.EXPENSE.name())) {
            return new Expense(id, title, description, value, Month.of(currentMonth), currentYear, date, timestamp, category, false);
        }
        throw TransactionException.invalidTransactionType();
    }

    public static Transaction buildTransaction(UUID id, String title, String description, BigDecimal value, String type, Integer currentMonth, Integer currentYear, LocalDate date, LocalDateTime timestamp, TransactionCategory category, Boolean hasReceipt) {
        if (type.equals(TransactionType.CREDIT.name())) {
            return new Credit(id, title, description, value, Month.of(currentMonth), currentYear, date, timestamp, category, hasReceipt);
        }
        if (type.equals(TransactionType.EXPENSE.name())) {
            return new Expense(id, title, description, value, Month.of(currentMonth), currentYear, date, timestamp, category, hasReceipt);
        }
        throw TransactionException.invalidTransactionType();
    }

    public static Transaction buildTransaction(String title, String description, BigDecimal value, String type, Integer currentMonth, Integer currentYear, LocalDate date, TransactionCategory category) {
        LocalDateTime timestamp = LocalDateTime.now();
        if (type.equals(TransactionType.CREDIT.name())) {
            return new Credit(UUID.randomUUID(), title, description, value, Month.of(currentMonth), currentYear, date, timestamp, category, false);
        }
        if (type.equals(TransactionType.EXPENSE.name())) {
            return new Expense(UUID.randomUUID(), title, description, value, Month.of(currentMonth), currentYear, date, timestamp, category, false);
        }
        throw TransactionException.invalidTransactionType();
    }

    public static Transaction buildTransaction(String title, String description, BigDecimal value, String type, Integer currentMonth, Integer currentYear, LocalDate date, TransactionCategory category, Boolean hasReceipt) {
        LocalDateTime timestamp = LocalDateTime.now();
        if (type.equals(TransactionType.CREDIT.name())) {
            return new Credit(UUID.randomUUID(), title, description, value, Month.of(currentMonth), currentYear, date, timestamp, category, hasReceipt);
        }
        if (type.equals(TransactionType.EXPENSE.name())) {
            return new Expense(UUID.randomUUID(), title, description, value, Month.of(currentMonth), currentYear, date, timestamp, category, hasReceipt);
        }
        throw TransactionException.invalidTransactionType();
    }
}
