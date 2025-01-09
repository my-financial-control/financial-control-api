package pedro.almeida.financialcontrol.application.usecases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pedro.almeida.financialcontrol.application.dtos.request.TransactionRequestDTO;
import pedro.almeida.financialcontrol.application.dtos.response.TransactionResponseDTO;
import pedro.almeida.financialcontrol.domain.factories.TransactionFactory;
import pedro.almeida.financialcontrol.domain.models.Transaction;
import pedro.almeida.financialcontrol.domain.models.TransactionCategory;
import pedro.almeida.financialcontrol.domain.repositories.TransactionCategories;
import pedro.almeida.financialcontrol.domain.repositories.Transactions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterTransactionTest {

    @Mock
    private Transactions transactions;
    @Mock
    private TransactionCategories transactionCategories;
    @InjectMocks
    private RegisterTransaction registerTransaction;


    @Test
    void executeShouldCallTheTransactionsMethodToSaveTheTransactionAndReturnTheSavedTransaction() {
        TransactionRequestDTO transactionRequest = new TransactionRequestDTO(
                "Title",
                "Description",
                new BigDecimal("150.50"),
                "EXPENSE",
                9,
                LocalDate.of(2023, 9, 15),
                UUID.randomUUID().toString()
        );
        TransactionCategory category = new TransactionCategory(UUID.fromString(transactionRequest.categoryId()), "Category", "");
        Transaction transaction = TransactionFactory.buildTransaction(
                transactionRequest.title(),
                transactionRequest.description(),
                transactionRequest.value(),
                transactionRequest.type(),
                transactionRequest.currentMonth(),
                transactionRequest.date(),
                category
        );
        when(transactionCategories.findById(any())).thenReturn(category);
        when(transactions.save(any())).thenReturn(transaction);

        TransactionResponseDTO savedTransaction = registerTransaction.execute(transactionRequest);

        assertEquals(transaction.getId(), savedTransaction.id());
    }

}