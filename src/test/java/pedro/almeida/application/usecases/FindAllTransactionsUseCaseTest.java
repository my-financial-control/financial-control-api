package pedro.almeida.application.usecases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pedro.almeida.domain.models.Transaction;
import pedro.almeida.domain.models.TransactionType;
import pedro.almeida.domain.repositories.Transactions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindAllTransactionsUseCaseTest {

    @Mock
    private Transactions transactions;
    private List<Transaction> transactionsMock;
    @InjectMocks
    private FindAllTransactionsUseCase findAllTransactionsUseCase;

    @BeforeEach
    void setUp() {
        this.transactionsMock = Arrays.asList(
            new Transaction("Title 1", new BigDecimal("100.0"), TransactionType.EXPENSE, Month.JANUARY, LocalDate.now()),
            new Transaction("Title 2", new BigDecimal("200.0"), TransactionType.CREDIT, Month.JANUARY, LocalDate.now()),
            new Transaction("Title 3", new BigDecimal("50.0"), TransactionType.EXPENSE, Month.JANUARY, LocalDate.now())
        );
        when(this.transactions.findAll()).thenReturn(this.transactionsMock);
    }

    @Test
    @DisplayName("Deve retornar uma lista de transações")
    void executeShouldReturnAListOfTransaction() {
        List<Transaction> transactionsReturned = this.findAllTransactionsUseCase.execute();

        assertEquals(this.transactionsMock, transactionsReturned);
        verify(this.transactions).findAll();
    }

}