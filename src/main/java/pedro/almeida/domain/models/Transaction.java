package pedro.almeida.domain.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.UUID;

public class Transaction {

    private UUID id = UUID.randomUUID();
    private String title;
    private String description;
    private BigDecimal value;
    private TransactionType type;
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

    public Month getCurrentMonth() {
        return currentMonth;
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
