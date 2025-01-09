package pedro.almeida.financialcontrol.domain.models;

import pedro.almeida.financialcontrol.domain.errors.ParcelBorrowingException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class ParcelBorrowing {

    private final BigDecimal value;
    private final LocalDate date;


    public ParcelBorrowing(BigDecimal value, LocalDate date) {
        validate(value);
        this.value = value;
        this.date = date;
    }

    public ParcelBorrowing(BigDecimal value) {
        validate(value);
        this.value = value;
        this.date = LocalDate.now();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParcelBorrowing that)) return false;
        return value.equals(that.value) && date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, date);
    }
}
