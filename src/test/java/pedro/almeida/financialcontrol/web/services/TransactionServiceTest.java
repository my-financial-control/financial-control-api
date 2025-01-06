package pedro.almeida.financialcontrol.web.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pedro.almeida.financialcontrol.application.usecases.FindAllTransactions;
import pedro.almeida.financialcontrol.application.usecases.RegisterTransaction;
import pedro.almeida.financialcontrol.domain.factories.TransactionFactory;
import pedro.almeida.financialcontrol.domain.models.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Transaction transaction = TransactionFactory.buildTransaction("Title", "", new BigDecimal("100.0"), "EXPENSE", 1, LocalDate.now(), "Category");
        when(registerTransaction.execute(any())).thenReturn(transaction);

        Transaction createdTransaction = transactionService.register(transaction);

        verify(registerTransaction).execute(transaction);
        assert createdTransaction != null;
    }

    @Test
    void findAllShouldReturnAListOfTransactionsAndCallUseCaseWithCorrectParamsWhenMonthAndYearWereInformed() {
        int month = 1;
        int year = 2023;
        List<Transaction> expectedTransactions = new ArrayList<>();
        when(findAllTransactions.execute(any(), anyInt())).thenReturn(expectedTransactions);

        List<Transaction> transactions = transactionService.findAll(month, year);

        verify(findAllTransactions).execute(Month.of(month), year);
        assertEquals(expectedTransactions, transactions);
    }

    @Test
    void findAllShouldReturnAListOfTransactionsAndCallUseCaseWithCorrectParamsWhenMonthAndYearWerentInformed() {
        List<Transaction> expectedTransactions = new ArrayList<>();
        when(findAllTransactions.execute()).thenReturn(expectedTransactions);

        List<Transaction> transactions = transactionService.findAll(null, null);

        verify(findAllTransactions).execute();
        assertEquals(expectedTransactions, transactions);
    }

    @Test
    void findAllShouldReturnAListOfTransactionsAndCallUseCaseWithMonthInformedAndCurrentYearWhenOnlyMonthWasInformed() {
        int month = 1;
        List<Transaction> expectedTransactions = new ArrayList<>();
        when(findAllTransactions.execute(any(), anyInt())).thenReturn(expectedTransactions);

        List<Transaction> transactions = transactionService.findAll(month, null);

        verify(findAllTransactions).execute(Month.of(month), LocalDate.now().getYear());
        assertEquals(expectedTransactions, transactions);
    }

}
