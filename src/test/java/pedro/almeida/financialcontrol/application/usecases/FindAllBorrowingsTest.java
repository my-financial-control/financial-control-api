package pedro.almeida.financialcontrol.application.usecases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pedro.almeida.financialcontrol.application.dtos.response.BorrowingResponseDTO;
import pedro.almeida.financialcontrol.domain.models.*;
import pedro.almeida.financialcontrol.domain.repositories.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindAllBorrowingsTest {

    @Mock
    private Borrowings borrowings;
    private List<Borrowing> borrowingsMock;
    @InjectMocks
    private FindAllBorrowings findAllBorrowings;

    @BeforeEach
    public void setUp() {
        this.borrowingsMock = Arrays.asList(
            new Borrowing(new Borrower("Borrower"), new BigDecimal("100.0")),
            new Borrowing(new Borrower("Borrower"), new BigDecimal("200.0")),
            new Borrowing(new Borrower("Borrower"), new BigDecimal("50.0"))
        );
        when(borrowings.findAll()).thenReturn(borrowingsMock);
    }

    @Test
    void executeShouldReturnAListOfBorrowing() {
        List<BorrowingResponseDTO> expected = borrowingsMock.stream().map(BorrowingResponseDTO::new).toList();

        List<BorrowingResponseDTO> borrowingsReturned = findAllBorrowings.execute();

        assertEquals(expected, borrowingsReturned);
        verify(borrowings).findAll();
    }

}