package pedro.almeida.financialcontrol.infra.repositories.nosql.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "borrowings")
public class BorrowingEntity {

    @Id
    private String id;
    private BorrowerEntity borrower;
    private BigDecimal value;
    private Boolean paid;
    private LocalDate date;
    private List<ParcelBorrowingEntity> parcels;
    private LocalDateTime timestamp;

    public BorrowingEntity(String id, BorrowerEntity borrower, BigDecimal value, Boolean paid, LocalDate date, List<ParcelBorrowingEntity> parcels, LocalDateTime timestamp) {
        this.id = id;
        this.borrower = borrower;
        this.value = value;
        this.paid = paid;
        this.date = date;
        this.parcels = parcels;
        this.timestamp = timestamp;
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
}
