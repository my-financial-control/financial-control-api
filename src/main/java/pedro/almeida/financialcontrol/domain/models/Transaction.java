package pedro.almeida.financialcontrol.domain.models;

import pedro.almeida.financialcontrol.domain.errors.TransactionException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.UUID;

public class Transaction {

    private final UUID id = UUID.randomUUID();
    private final String title;
    private String description = "";
    private final BigDecimal value;
    private final TransactionType type;
    private final Month currentMonth;
    private final LocalDate date;
    private final LocalTime time = LocalTime.now();

    public Transaction(String title, String description, BigDecimal value, TransactionType type, Month currentMonth, LocalDate date) {
        this.title = title;
        this.description = description;
        validate(value);
        this.value = value;
        this.type = type;
        this.currentMonth = currentMonth;
        this.date = date;
    }

    public Transaction(String title, BigDecimal value, TransactionType type, Month currentMonth, LocalDate date) {
        this.title = title;
        validate(value);
        this.value = value;
        this.type = type;
        this.currentMonth = currentMonth;
        this.date = date;
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

    public LocalTime getTime() {
        return time;
    }

}
