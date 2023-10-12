package pedro.almeida.financialcontrol.web.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pedro.almeida.financialcontrol.domain.models.Transaction;
import pedro.almeida.financialcontrol.domain.usecases.FindAllTransactions;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private FindAllTransactions findAllTransactions;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    @DisplayName("Deve retornar uma lista de transações e chamar o caso de uso com os parâmetros corretos")
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
    @DisplayName("Deve retornar uma lista de transações e chamar o caso de uso sem nenhum parâmetro")
    void findAllShouldReturnAListOfTransactionsAndCallUseCaseWithCorrectParamsWhenMonthAndYearWerentInformed() {
        List<Transaction> expectedTransactions = new ArrayList<>();
        when(findAllTransactions.execute()).thenReturn(expectedTransactions);

        List<Transaction> transactions = transactionService.findAll(null, null);

        verify(findAllTransactions).execute();
        assertEquals(expectedTransactions, transactions);
    }

    @Test
    @DisplayName("Deve retornar uma lista de transações e chamar o caso de uso com o mês informado e com o ano atual")
    void findAllShouldReturnAListOfTransactionsAndCallUseCaseWithMonthInformedAndCurrentYearWhenOnlyMonthWasInformed() {
        int month = 1;
        List<Transaction> expectedTransactions = new ArrayList<>();
        when(findAllTransactions.execute(any(), anyInt())).thenReturn(expectedTransactions);

        List<Transaction> transactions = transactionService.findAll(month, null);

        verify(findAllTransactions).execute(Month.of(month), LocalDate.now().getYear());
        assertEquals(expectedTransactions, transactions);
    }

}
