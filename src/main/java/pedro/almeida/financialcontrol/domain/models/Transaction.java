package pedro.almeida.financialcontrol.domain.models;

import pedro.almeida.financialcontrol.domain.errors.TransactionException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

public class Transaction {

    private final UUID id;
    private final String title;
    private final String description;
    private final BigDecimal value;
    private final TransactionType type;
    private final Month currentMonth;
    private final LocalDate date;
    private final LocalDateTime timestamp;

    public Transaction(String title, String description, BigDecimal value, TransactionType type, Month currentMonth, LocalDate date) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.description = description;
        validate(value);
        this.value = value;
        this.type = type;
        this.currentMonth = currentMonth;
        this.date = date;
        this.timestamp = LocalDateTime.now();
    }

    public Transaction(UUID id, String title, BigDecimal value, TransactionType type, Month currentMonth, LocalDate date, LocalDateTime time, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.value = value;
        this.type = type;
        this.currentMonth = currentMonth;
        this.date = date;
        this.timestamp = time;
    }

    public Transaction(String title, BigDecimal value, TransactionType type, Month currentMonth, LocalDate date) {
        this.id = UUID.randomUUID();
        this.title = title;
        validate(value);
        this.description = "";
        this.value = value;
        this.type = type;
        this.currentMonth = currentMonth;
        this.date = date;
        this.timestamp = LocalDateTime.now();
    }

    private void validate(BigDecimal value) {
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

    public Month getCurrentMonth() {
        return currentMonth;
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

}
