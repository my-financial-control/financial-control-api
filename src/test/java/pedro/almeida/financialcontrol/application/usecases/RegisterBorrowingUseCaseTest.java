package pedro.almeida.financialcontrol.application.usecases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pedro.almeida.financialcontrol.domain.models.Borrower;
import pedro.almeida.financialcontrol.domain.models.Borrowing;
import pedro.almeida.financialcontrol.domain.repositories.Borrowings;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterBorrowingUseCaseTest {

    @Mock
    private Borrowings borrowings;
    @InjectMocks
    private RegisterBorrowingUseCase registerBorrowingUseCase;

    @Test
    void executeShouldSaveTheBorrowingaAndReturnTheSavedObject() {
        Borrowing borrowing = new Borrowing(new Borrower("Borrower"), new BigDecimal("100.80"));
        when(borrowings.save(any())).thenReturn(borrowing);

        Borrowing savedBorrowing = this.registerBorrowingUseCase.execute(borrowing);

        verify(borrowings).save(borrowing);
        assertEquals(borrowing, savedBorrowing);
    }

}