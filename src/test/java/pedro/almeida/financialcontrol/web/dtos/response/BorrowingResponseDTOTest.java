package pedro.almeida.financialcontrol.web.dtos.response;

import org.junit.jupiter.api.Test;
import pedro.almeida.financialcontrol.domain.models.Borrower;
import pedro.almeida.financialcontrol.domain.models.Borrowing;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BorrowingResponseDTOTest {

    @Test
    void toBorrowingResponseDTOShouldReturnAListOfBorrowingResponseDTO() {
        List<Borrowing> borrowings = Arrays.asList(
                new Borrowing(new Borrower("Borrower 1"), new BigDecimal("50.8")),
                new Borrowing(new Borrower("Borrower 2"), new BigDecimal("100.7")),
                new Borrowing(new Borrower("Borrower 3"), new BigDecimal("200")),
                new Borrowing(new Borrower("Borrower 4"), new BigDecimal("20.89")),
                new Borrowing(new Borrower("Borrower 5"), new BigDecimal("33.74"))
        );

        List<BorrowingResponseDTO> dtos = BorrowingResponseDTO.toBorrowingResponseDTO(borrowings);

        for (int i = 0; i < borrowings.size(); i++) {
            Borrowing borrowing = borrowings.get(i);
            BorrowingResponseDTO borrowingResponseDTO = dtos.get(i);

            assertEquals(borrowing.getId(), borrowingResponseDTO.id());
            assertEquals(borrowing.getBorrower().getName(), borrowingResponseDTO.borrower());
            assertEquals(borrowing.getValue(), borrowingResponseDTO.value());
            assertEquals(borrowing.getPaid(), borrowingResponseDTO.paid());
            assertEquals(borrowing.getDate(), borrowingResponseDTO.date());
            assertEquals(borrowing.getParcels(), borrowingResponseDTO.parcels());
        }

    }

}