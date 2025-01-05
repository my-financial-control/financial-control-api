package pedro.almeida.financialcontrol.infra.repositories.nosql.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ParcelBorrowingEntity {

    private BigDecimal value;
    private LocalDate date;

    public ParcelBorrowingEntity(BigDecimal value, LocalDate date) {
        this.value = value;
        this.date = date;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}

