package pedro.almeida.financialcontrol.application.usecases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pedro.almeida.financialcontrol.domain.models.Transaction;
import pedro.almeida.financialcontrol.domain.models.TransactionType;
import pedro.almeida.financialcontrol.domain.repositories.Transactions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterTransactionUseCaseTest {

    @Mock
    private Transactions transactions;
    @InjectMocks
    private RegisterTransactionUseCase registerTransactionUseCase;


    @Test
    void executeShouldCallTheTransactionsMethodToSaveTheTransactionAndReturnTheSavedTransaction() {
        Transaction transaction = new Transaction("Title", "", new BigDecimal("100.0"), TransactionType.EXPENSE, Month.JANUARY, LocalDate.now());
        when(transactions.save(any())).thenReturn(transaction);

        Transaction savedTransaction = registerTransactionUseCase.execute(transaction);

        verify(transactions).save(transaction);
        assertEquals(transaction, savedTransaction);
    }

}