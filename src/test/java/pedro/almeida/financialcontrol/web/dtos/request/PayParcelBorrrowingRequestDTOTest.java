package pedro.almeida.financialcontrol.web.dtos.request;

import org.junit.jupiter.api.Test;
import pedro.almeida.financialcontrol.domain.models.ParcelBorrowing;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PayParcelBorrrowingRequestDTOTest {

    @Test
    void toParcelBorrowingShouldReturnAParcelBorrowing() {
        PayParcelBorrrowingRequestDTO payParcelBorrrowingRequestDTO = new PayParcelBorrrowingRequestDTO(new BigDecimal("100.5"), LocalDate.now());

        ParcelBorrowing parcelBorrowing = payParcelBorrrowingRequestDTO.toParcelBorrowing();

        assertEquals(payParcelBorrrowingRequestDTO.value(), parcelBorrowing.getValue());
        assertEquals(payParcelBorrrowingRequestDTO.date(), parcelBorrowing.getDate());
    }

}