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
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private RegisterTransaction registerTransaction;
    @Mock
    private FindAllTransactions findAllTransactions;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void registerShouldCallTheRegisterTransactionUseCaseAndReturnATransaction() {
        Transaction transaction = new Transaction("Title", "", new BigDecimal("100.0"), TransactionType.EXPENSE, Month.JANUARY, LocalDate.now());
        when(this.registerTransaction.execute(any())).thenReturn(transaction);

        Transaction createdTransaction = this.transactionService.register(transaction);

        verify(this.registerTransaction).execute(transaction);
        assert createdTransaction != null;
    }

    @Test
    void findAllShouldReturnAListOfTransactionsAndCallUseCaseWithCorrectParamsWhenMonthAndYearWereInformed() {
        int month = 1;
        int year = 2023;
        List<Transaction> expectedTransactions = new ArrayList<>();
        when(this.findAllTransactions.execute(any(), anyInt())).thenReturn(expectedTransactions);

        List<Transaction> transactions = this.transactionService.findAll(month, year);

        verify(this.findAllTransactions).execute(Month.of(month), year);
        assertEquals(expectedTransactions, transactions);
    }

    @Test
    void findAllShouldReturnAListOfTransactionsAndCallUseCaseWithCorrectParamsWhenMonthAndYearWerentInformed() {
        List<Transaction> expectedTransactions = new ArrayList<>();
        when(this.findAllTransactions.execute()).thenReturn(expectedTransactions);

        List<Transaction> transactions = this.transactionService.findAll(null, null);

        verify(this.findAllTransactions).execute();
        assertEquals(expectedTransactions, transactions);
    }

    @Test
    void findAllShouldReturnAListOfTransactionsAndCallUseCaseWithMonthInformedAndCurrentYearWhenOnlyMonthWasInformed() {
        int month = 1;
        List<Transaction> expectedTransactions = new ArrayList<>();
        when(this.findAllTransactions.execute(any(), anyInt())).thenReturn(expectedTransactions);

        List<Transaction> transactions = this.transactionService.findAll(month, null);

        verify(this.findAllTransactions).execute(Month.of(month), LocalDate.now().getYear());
        assertEquals(expectedTransactions, transactions);
    }

}
