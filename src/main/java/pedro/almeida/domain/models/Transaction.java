package pedro.almeida.domain.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.UUID;

public class Transaction {

    private UUID id = UUID.randomUUID();
    private String title;
    private String description;
    private BigDecimal value;
    private final TransactionType type;
    private Month currentMonth;
    private LocalDate date;
    private LocalTime time = LocalTime.now();

    public Transaction(String title, String description, BigDecimal value, TransactionType type, Month currentMonth, LocalDate date) {
        this.title = title;
        this.description = description;
        this.value = value;
        this.type = type;
        this.currentMonth = currentMonth;
        this.date = date;
    }

    public UUID getId() {
        return id;
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
        return title + "\t| " + description + "\t| " + value + "\t| " + type + "\t| " + currentMonth + "\t| " + date + " |";
    }

}
