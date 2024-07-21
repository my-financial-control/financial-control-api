package pedro.almeida.financialcontrol.web.dtos.request;

import org.junit.jupiter.api.Test;
import pedro.almeida.financialcontrol.domain.models.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BorrowingRequestDTOTest {

    @Test
    void toBorrowingShouldReturnABorrowing() {
        BorrowingRequestDTO dto = new BorrowingRequestDTO("Borrower", new BigDecimal("50.8"), LocalDate.now());

        Borrowing borrowing = dto.toBorrowing();

        assertEquals(dto.borrower(), borrowing.getBorrower().getName());
        assertEquals(dto.value(), borrowing.getValue());
        assertEquals(dto.date(), borrowing.getDate());
    }

}