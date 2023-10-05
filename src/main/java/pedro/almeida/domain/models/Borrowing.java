package pedro.almeida.domain.models;

import pedro.almeida.domain.errors.ParcelExceedsBorrowingValueException;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    private Boolean isParcelExceedsBorrowingValue(ParcelBorrowing parcel) {
        return this.sumParcels().add(parcel.getValue()).compareTo(this.value) > 0;
    }

    private Boolean isBorrowingFullPaid() {
        return this.sumParcels().compareTo(this.value) >= 0;
    }

    private BigDecimal sumParcels() {
        return this.parcels.stream().map(ParcelBorrowing::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public UUID getId() {
        return id;
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
