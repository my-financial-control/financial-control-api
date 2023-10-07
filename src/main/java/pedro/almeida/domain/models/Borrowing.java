package pedro.almeida.domain.models;

import pedro.almeida.domain.errors.ParcelExceedsBorrowingValueException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Borrowing {

    private UUID id = UUID.randomUUID();
    private Borrower borrower;
    private BigDecimal value;
    private Boolean paid = false;
    private LocalDate date = LocalDate.now();
    private final List<ParcelBorrowing> parcels = new LinkedList<>();

    public Borrowing(Borrower borrower, BigDecimal value, LocalDate date) {
        this.borrower = borrower;
        this.value = value;
        this.date = date;
    }

    public Borrowing(Borrower borrower, BigDecimal value) {
        this.borrower = borrower;
        this.value = value;
    }

    public void payParcel(ParcelBorrowing parcel) {
        if(this.isParcelExceedsBorrowingValue(parcel)) {
            throw new ParcelExceedsBorrowingValueException("O valor da parcela excede o valor restante a pagar do empréstimo");
        }
        this.parcels.add(parcel);
        if(isBorrowingFullPaid()) this.paid = true;
    }

    public Boolean isParcelExceedsBorrowingValue(ParcelBorrowing parcel) {
        return this.sumParcels().add(parcel.getValue()).compareTo(this.value) > 0;
    }

    public Boolean isBorrowingFullPaid() {
        return this.sumParcels().compareTo(this.value) >= 0;
    }

    public BigDecimal sumParcels() {
        return this.getParcels().stream().map(ParcelBorrowing::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal remainingPaymentAmount() {
        return this.value.subtract(this.sumParcels());
    }

    public UUID getId() {
        return id;
    }

    public List<ParcelBorrowing> getParcels() {
        return Collections.unmodifiableList(this.parcels);
    }

    public Boolean getPaid() {
        return paid;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Borrowing{" +
                "id=" + id +
                ", borrower=" + borrower +
                ", value=" + value +
                ", paid=" + paid +
                ", date=" + date +
                ", parcels=" + parcels +
                '}';
    }

}
