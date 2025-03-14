package pedro.almeida.financialcontrol.domain.models;

import pedro.almeida.financialcontrol.domain.errors.TransactionException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

public abstract class Transaction {

    private final UUID id;
    private final String title;
    private final String description;
    private final BigDecimal value;
    private final TransactionType type;
    private final Month currentMonth;
    private final Integer currentYear;
    private final LocalDate date;
    private final LocalDateTime timestamp;
    private final TransactionCategory category;
    private final Boolean hasReceipt;

    public Transaction(UUID id, String title, String description, BigDecimal value, TransactionType type, Month currentMonth, Integer currentYear, LocalDate date, LocalDateTime timestamp, TransactionCategory category, Boolean hasReceipt) {
        this.id = id;
        this.title = title;
        this.description = description;
        validateValue(value);
        this.value = value;
        this.type = type;
        this.currentMonth = currentMonth;
        this.currentYear = currentYear;
        this.date = date;
        this.timestamp = timestamp;
        this.category = category;
        this.hasReceipt = hasReceipt;
    }

    private void validateValue(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw TransactionException.invalidTransactionValue();
        }
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TransactionType getType() {
        return this.type;
    }

    public TransactionCategory getCategory() {
        return category;
    }

    public Month getCurrentMonth() {
        return currentMonth;
    }

    public Integer getCurrentYear() {
        return currentYear;
    }

    public BigDecimal getValue() {
        return this.value;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Boolean getHasReceipt() {
        return hasReceipt;
    }
}
