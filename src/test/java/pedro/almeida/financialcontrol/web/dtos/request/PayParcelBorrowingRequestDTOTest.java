package pedro.almeida.financialcontrol.web.dtos.request;

import org.junit.jupiter.api.Test;
import pedro.almeida.financialcontrol.domain.models.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PayParcelBorrowingRequestDTOTest {

    @Test
    void toParcelBorrowingShouldReturnAParcelBorrowing() {
        PayParcelBorrowingRequestDTO payParcelBorrowingRequestDTO = new PayParcelBorrowingRequestDTO(new BigDecimal("100.5"), LocalDate.now());

        ParcelBorrowing parcelBorrowing = payParcelBorrowingRequestDTO.toParcelBorrowing();

        assertEquals(payParcelBorrowingRequestDTO.value(), parcelBorrowing.getValue());
        assertEquals(payParcelBorrowingRequestDTO.date(), parcelBorrowing.getDate());
    }

}