package pedro.almeida.financialcontrol.domain.models;

import pedro.almeida.financialcontrol.domain.errors.TransactionException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.UUID;

public class Transaction {

    private UUID id = UUID.randomUUID();
    private String title;
    private String description = "";
    private BigDecimal value;
    private final TransactionType type;
    private Month currentMonth;
    private LocalDate date;
    private LocalTime time = LocalTime.now();

    public Transaction(String title, String description, BigDecimal value, TransactionType type, Month currentMonth, LocalDate date) {
        this.title = title;
        this.description = description;
        this.validate(value);
        this.value = value;
        this.type = type;
        this.currentMonth = currentMonth;
        this.date = date;
    }

    public Transaction(String title, BigDecimal value, TransactionType type, Month currentMonth, LocalDate date) {
        this.title = title;
        this.validate(value);
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

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", value=" + value +
                ", type=" + type +
                ", currentMonth=" + currentMonth +
                ", date=" + date +
                ", time=" + time +
                '}';
    }

}
