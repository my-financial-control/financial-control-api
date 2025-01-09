package pedro.almeida.financialcontrol.application.usecases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pedro.almeida.financialcontrol.application.dtos.request.PayParcelBorrowingRequestDTO;
import pedro.almeida.financialcontrol.domain.models.Borrowing;
import pedro.almeida.financialcontrol.domain.models.ParcelBorrowing;
import pedro.almeida.financialcontrol.domain.repositories.Borrowings;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PayParcelBorrowingTest {

    @Mock
    private Borrowings borrowings;
    @InjectMocks
    private PayParcelBorrowing payParcelBorrowing;

    @Test
    void executeShouldFindTheBorrowingCallMethodPayParcelAndSaveTheObjectInRepository() {
        Borrowing borrowing = mock(Borrowing.class);
        when(borrowings.findById(any())).thenReturn(borrowing);
        doNothing().when(borrowing).payParcel(any());
        PayParcelBorrowingRequestDTO parcelBorrowing = new PayParcelBorrowingRequestDTO(new BigDecimal("20.5"), LocalDate.now());

        payParcelBorrowing.execute(borrowing.getId(), parcelBorrowing);

        verify(borrowings).findById(borrowing.getId());
        verify(borrowing).payParcel(new ParcelBorrowing(parcelBorrowing.value(), parcelBorrowing.date()));
    }

}