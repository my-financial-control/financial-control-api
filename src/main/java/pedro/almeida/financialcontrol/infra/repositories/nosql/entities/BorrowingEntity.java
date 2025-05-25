package pedro.almeida.financialcontrol.infra.repositories.nosql.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pedro.almeida.financialcontrol.domain.models.Borrower;
import pedro.almeida.financialcontrol.domain.models.Borrowing;
import pedro.almeida.financialcontrol.domain.models.ParcelBorrowing;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Document(collection = "borrowings")
public class BorrowingEntity {

    @Id
    private String id;
    private BorrowerEntity borrower;
    private BigDecimal value;
    private String description;
    private Boolean paid;
    private LocalDate date;
    private List<ParcelBorrowingEntity> parcels;
    private LocalDateTime timestamp;
    private boolean hasReceipt;

    public BorrowingEntity() {}

    public BorrowingEntity(String id, BorrowerEntity borrower, BigDecimal value, String description, Boolean paid, LocalDate date, List<ParcelBorrowingEntity> parcels, LocalDateTime timestamp, boolean hasReceipt) {
        this.id = id;
        this.borrower = borrower;
        this.value = value;
        this.description = description;
        this.paid = paid;
        this.date = date;
        this.parcels = parcels;
        this.timestamp = timestamp;
        this.hasReceipt = hasReceipt;
    }

    public BorrowingEntity(Borrowing borrowing) {
        this.id = borrowing.getId().toString();
        this.borrower = new BorrowerEntity(borrowing.getBorrower().getName());
        this.value = borrowing.getValue();
        this.description = borrowing.getDescription();
        this.paid = borrowing.getPaid();
        this.date = borrowing.getDate();
        this.parcels = borrowing.getParcels().stream().map(p -> new ParcelBorrowingEntity(p.getValue(), p.getDate())).toList();
        this.timestamp = borrowing.getTimestamp();
        this.hasReceipt = borrowing.hasReceipt();
    }

    public Borrowing toModel() {
        return new Borrowing(
                UUID.fromString(id),
                new Borrower(borrower.getName()),
                value,
                description,
                paid,
                date,
                parcels.stream().map(p -> new ParcelBorrowing(p.getValue(), p.getDate())).toList(),
                timestamp,
                hasReceipt
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BorrowerEntity getBorrower() {
        return borrower;
    }

    public void setBorrower(BorrowerEntity borrower) {
        this.borrower = borrower;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<ParcelBorrowingEntity> getParcels() {
        return parcels;
    }

    public void setParcels(List<ParcelBorrowingEntity> parcels) {
        this.parcels = parcels;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isHasReceipt() {
        return hasReceipt;
    }

    public void setHasReceipt(boolean hasReceipt) {
        this.hasReceipt = hasReceipt;
    }
}
