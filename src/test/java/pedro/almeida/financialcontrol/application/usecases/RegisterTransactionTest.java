package pedro.almeida.financialcontrol.application.usecases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pedro.almeida.financialcontrol.domain.factories.TransactionFactory;
import pedro.almeida.financialcontrol.domain.models.Transaction;
import pedro.almeida.financialcontrol.domain.repositories.Transactions;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterTransactionTest {

    @Mock
    private Transactions transactions;
    @InjectMocks
    private RegisterTransaction registerTransaction;


    @Test
    void executeShouldCallTheTransactionsMethodToSaveTheTransactionAndReturnTheSavedTransaction() {
        Transaction transaction = TransactionFactory.buildTransaction(
                "Title",
                "Description",
                new BigDecimal("150.50"),
                "EXPENSE",
                9,
                LocalDate.of(2023, 9, 15),
                "Category"
        );
        when(transactions.save(any())).thenReturn(transaction);

        Transaction savedTransaction = registerTransaction.execute(transaction);

        verify(transactions).save(transaction);
        assertEquals(transaction, savedTransaction);
    }

}