package pedro.almeida.financialcontrol.web.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pedro.almeida.financialcontrol.domain.models.Borrower;
import pedro.almeida.financialcontrol.domain.models.Borrowing;
import pedro.almeida.financialcontrol.domain.usecases.RegisterBorrowing;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BorrowingServiceTest {

    @Mock
    private RegisterBorrowing registerBorrowing;

    @InjectMocks
    private BorrowingService borrowingService;

    @Test
    void registerShouldCallTheRegisterBorrowingUseCaseAndThenReturnABorrowing() {
        Borrowing borrowing = new Borrowing(new Borrower("Borrower"), new BigDecimal("50.5"), LocalDate.now());
        when(this.registerBorrowing.execute(any())).thenReturn(borrowing);

        Borrowing createdBorrowing = this.borrowingService.register(borrowing);

        verify(this.registerBorrowing).execute(borrowing);
        assert createdBorrowing != null;
    }

}