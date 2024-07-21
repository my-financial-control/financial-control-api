package pedro.almeida.financialcontrol.web.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pedro.almeida.financialcontrol.domain.models.*;
import pedro.almeida.financialcontrol.application.usecases.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BorrowingServiceTest {

    @Mock
    private RegisterBorrowing registerBorrowing;
    @Mock
    private FindAllBorrowings findAllBorrowings;
    @Mock
    private PayParcelBorrowing payParcelBorrowing;

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

    @Test
    void findAllShouldCallTheFindAllBorrowingsUseCaseAndReturnAListOfBorrowings() {
        List<Borrowing> expectedBorrowings = new ArrayList<>();
        when(this.borrowingService.findAll()).thenReturn(expectedBorrowings);

        List<Borrowing> borrowings = this.borrowingService.findAll();

        verify(this.findAllBorrowings).execute();
        assertEquals(expectedBorrowings, borrowings);
    }

    @Test
    void payParcelShouldCallThePayParcelBorrowingUseCase() {
        ParcelBorrowing parcel = new ParcelBorrowing(new BigDecimal("50.88"), LocalDate.now());
        UUID uuid = UUID.randomUUID();

        this.borrowingService.payParcel(uuid, parcel);

        verify(this.payParcelBorrowing).execute(uuid, parcel);
    }

}