package pedro.almeida.financialcontrol.domain.models;

import pedro.almeida.financialcontrol.domain.errors.BorrowingException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Borrowing {

    private final UUID id = UUID.randomUUID();
    private final Borrower borrower;
    private final BigDecimal value;
    private Boolean paid = false;
    private LocalDate date = LocalDate.now();
    private final List<ParcelBorrowing> parcels = new LinkedList<>();

    public Borrowing(Borrower borrower, BigDecimal value, LocalDate date) {
        this.borrower = borrower;
        validate(value);
        this.value = value;
        this.date = date;
    }

    public Borrowing(Borrower borrower, BigDecimal value) {
        this.borrower = borrower;
        validate(value);
        this.value = value;
    }

    public void payParcel(ParcelBorrowing parcel) {
        if (isParcelExceedsBorrowingValue(parcel)) {
            throw BorrowingException.parcelExceedsBorrowingValue();
        }
        parcels.add(parcel);
        if (isBorrowingFullPaid()) paid = true;
    }

    public Boolean isParcelExceedsBorrowingValue(ParcelBorrowing parcel) {
        return sumParcels().add(parcel.getValue()).compareTo(value) > 0;
    }

    public Boolean isBorrowingFullPaid() {
        return sumParcels().compareTo(value) >= 0;
    }

    public BigDecimal sumParcels() {
        return getParcels().stream().map(ParcelBorrowing::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal remainingPaymentAmount() {
        return value.subtract(sumParcels());
    }

    private void validate(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw BorrowingException.invalidBorrowingValue();
        }
    }

    public UUID getId() {
        return id;
    }

    public Borrower getBorrower() {
        return borrower;
    }

    public BigDecimal getValue() {
        return value;
    }

    public List<ParcelBorrowing> getParcels() {
        return Collections.unmodifiableList(parcels);
    }

    public Boolean getPaid() {
        return paid;
    }

    public LocalDate getDate() {
        return date;
    }

}
