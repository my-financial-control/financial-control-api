package pedro.almeida.financialcontrol.domain.models;

import pedro.almeida.financialcontrol.domain.errors.ParcelBorrowingException;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ParcelBorrowing {

    private final BigDecimal value;
    private LocalDate date = LocalDate.now();


    public ParcelBorrowing(BigDecimal value, LocalDate date) {
        validate(value);
        this.value = value;
        this.date = date;
    }

    public ParcelBorrowing(BigDecimal value) {
        validate(value);
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    public LocalDate getDate() {
        return date;
    }

    private void validate(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw ParcelBorrowingException.invalidParcelBorrowingValue();
        }
    }

}
