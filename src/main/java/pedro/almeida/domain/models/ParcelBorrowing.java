package pedro.almeida.domain.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ParcelBorrowing {

    private LocalDate date;
    private BigDecimal value;


    public ParcelBorrowing(LocalDate date, BigDecimal value) {
        this.date = date;
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
