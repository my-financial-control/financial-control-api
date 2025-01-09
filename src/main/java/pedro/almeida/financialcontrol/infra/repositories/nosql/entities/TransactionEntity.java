package pedro.almeida.financialcontrol.infra.repositories.nosql.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import pedro.almeida.financialcontrol.domain.factories.TransactionFactory;
import pedro.almeida.financialcontrol.domain.models.Transaction;
import pedro.almeida.financialcontrol.domain.models.TransactionCategory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.UUID;

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
    private String categoryId;
    @Field("category")
    private TransactionCategoryEntity category;

    public TransactionEntity() {
    }

    public TransactionEntity(String id, String title, String description, BigDecimal value, String type, String currentMonth, LocalDate date, LocalDateTime timestamp, String categoryId, TransactionCategoryEntity category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.value = value;
        this.type = type;
        this.currentMonth = currentMonth;
        this.date = date;
        this.timestamp = timestamp;
        this.categoryId = categoryId;
        this.category = category;
    }

    public TransactionEntity(Transaction transaction) {
        this.id = transaction.getId().toString();
        this.title = transaction.getTitle();
        this.description = transaction.getDescription();
        this.value = transaction.getValue();
        this.type = transaction.getType().name();
        this.currentMonth = transaction.getCurrentMonth().name();
        this.date = transaction.getDate();
        this.timestamp = transaction.getTimestamp();
        this.categoryId = transaction.getCategory().getId().toString();
        this.category = new TransactionCategoryEntity(transaction.getCategory());
    }

    public Transaction toModel(TransactionCategory category) {
        return TransactionFactory.buildTransaction(
                UUID.fromString(id),
                title,
                description,
                value,
                type,
                Month.valueOf(currentMonth).getValue(),
                date,
                timestamp,
                category
        );
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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public TransactionCategoryEntity getCategory() {
        return category;
    }

    public void setCategory(TransactionCategoryEntity category) {
        this.category = category;
    }
}
