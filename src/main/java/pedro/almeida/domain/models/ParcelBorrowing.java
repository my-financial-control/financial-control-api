package pedro.almeida.domain.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ParcelBorrowing {

    private BigDecimal value;
    private LocalDate date;


    public ParcelBorrowing(BigDecimal value, LocalDate date) {
        this.value = value;
        this.date = date;
    }

    public ParcelBorrowing(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "ParcelBorrowing{" +
                "date=" + date +
                ", value=" + value +
                '}';
    }

}
