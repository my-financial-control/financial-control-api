package pedro.almeida.financialcontrol.application.usecases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pedro.almeida.financialcontrol.domain.models.Borrower;
import pedro.almeida.financialcontrol.domain.models.Borrowing;
import pedro.almeida.financialcontrol.domain.models.ParcelBorrowing;
import pedro.almeida.financialcontrol.domain.repositories.Borrowings;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PayParcelBorrowingTest {

    @Mock
    private Borrowings borrowings;
    @InjectMocks
    private PayParcelBorrowing payParcelBorrowing;

    @Test
    void executeShouldFindTheBorrowingCallMethodPayParcelAndSaveTheObjectInRepository() {
        Borrowing borrowing = new Borrowing(new Borrower("Borrower"), new BigDecimal("50.80"), LocalDate.now());
        when(borrowings.findById(any())).thenReturn(borrowing);
        UUID uuid = UUID.randomUUID();
        ParcelBorrowing parcelBorrowing = new ParcelBorrowing(new BigDecimal("20.5"));

        this.payParcelBorrowing.execute(uuid, parcelBorrowing);

        verify(borrowings).findById(uuid);
        assert borrowing.getParcels().contains(parcelBorrowing);
        verify(borrowings).save(borrowing);
    }

}