package pedro.almeida.financialcontrol.infra.repositories.nosql.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "transactions")
public class TransactionEntity {

    @Id
    private String id;
    private String title;
    private String description;
    private BigDecimal value;
    private String type;
    private String currentMonth;
    private LocalDate date;
    private LocalDateTime timestamp;
    private String category;

    public TransactionEntity(String id, String title, String description, BigDecimal value, String type, String currentMonth, LocalDate date, LocalDateTime timestamp, String category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.value = value;
        this.type = type;
        this.currentMonth = currentMonth;
        this.date = date;
        this.timestamp = timestamp;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(String currentMonth) {
        this.currentMonth = currentMonth;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
