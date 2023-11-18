package pedro.almeida.financialcontrol.web.dtos.request;

import pedro.almeida.financialcontrol.domain.models.ParcelBorrowing;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PayParcelBorrrowingRequestDTO(BigDecimal value, LocalDate date) {

    public ParcelBorrowing toParcelBorrowing() {
        if (this.date != null) {
            return new ParcelBorrowing(this.value, this.date);
        } else {
            return new ParcelBorrowing(this.value);
        }
    }

}
