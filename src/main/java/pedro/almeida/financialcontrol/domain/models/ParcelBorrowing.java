package pedro.almeida.financialcontrol.domain.models;

import pedro.almeida.financialcontrol.domain.errors.ParcelBorrowingException;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ParcelBorrowing {

    private BigDecimal value;
    private LocalDate date;


    public ParcelBorrowing(BigDecimal value, LocalDate date) {
        this.validate(value);
        this.value = value;
        this.date = date;
    }

    public ParcelBorrowing(BigDecimal value) {
        this.validate(value);
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    private void validate(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw ParcelBorrowingException.invalidParcelBorrowingValue();
        }
    }

    @Override
    public String toString() {
        return "ParcelBorrowing{" +
                "date=" + date +
                ", value=" + value +
                '}';
    }

}
