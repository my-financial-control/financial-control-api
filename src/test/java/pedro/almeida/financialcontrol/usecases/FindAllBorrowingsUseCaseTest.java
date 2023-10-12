package pedro.almeida.financialcontrol.usecases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pedro.almeida.financialcontrol.domain.models.Borrower;
import pedro.almeida.financialcontrol.domain.models.Borrowing;
import pedro.almeida.financialcontrol.domain.repositories.Borrowings;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindAllBorrowingsUseCaseTest {

    @Mock
    private Borrowings borrowings;
    private List<Borrowing> borrowingsMock;
    @InjectMocks
    private FindAllBorrowingsUseCase findAllBorrowingsUseCase;

    @BeforeEach
    public void setUp() {
        this.borrowingsMock = Arrays.asList(
            new Borrowing(new Borrower("Borrower"), new BigDecimal("100.0")),
            new Borrowing(new Borrower("Borrower"), new BigDecimal("200.0")),
            new Borrowing(new Borrower("Borrower"), new BigDecimal("50.0"))
        );
        when(this.borrowings.findAll()).thenReturn(this.borrowingsMock);
    }

    @Test
    @DisplayName("Deve chamar os métodos corretos do repositório e retornar uma lista de Borrowings")
    void executeShouldReturnAListOfBorrowing() {
        List<Borrowing> borrowingsReturned = this.findAllBorrowingsUseCase.execute();

        assertEquals(this.borrowingsMock, borrowingsReturned);
        verify(this.borrowings).findAll();
    }

}