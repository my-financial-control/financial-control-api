package pedro.almeida.financialcontrol.application.usecases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pedro.almeida.financialcontrol.application.dtos.request.BorrowingRequestDTO;
import pedro.almeida.financialcontrol.application.dtos.response.BorrowingResponseDTO;
import pedro.almeida.financialcontrol.domain.models.Borrower;
import pedro.almeida.financialcontrol.domain.models.Borrowing;
import pedro.almeida.financialcontrol.domain.repositories.Borrowings;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterBorrowingTest {

    @Mock
    private Borrowings borrowings;
    @InjectMocks
    private RegisterBorrowing registerBorrowing;

    @Test
    void executeShouldSaveTheBorrowingaAndReturnTheSavedObject() {
        BorrowingRequestDTO borrowingRequest = new BorrowingRequestDTO("Borrower", new BigDecimal("100.80"), LocalDate.now());
        Borrowing borrowing = new Borrowing(new Borrower(borrowingRequest.borrower()), borrowingRequest.value(), borrowingRequest.date());
        when(borrowings.save(any())).thenReturn(borrowing);

        BorrowingResponseDTO savedBorrowing = registerBorrowing.execute(borrowingRequest);

        assertEquals(borrowing.getId(), savedBorrowing.id());
    }

}