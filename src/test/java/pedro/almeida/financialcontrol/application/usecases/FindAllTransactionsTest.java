package pedro.almeida.financialcontrol.application.usecases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pedro.almeida.financialcontrol.domain.models.Transaction;
import pedro.almeida.financialcontrol.domain.repositories.Transactions;
import pedro.almeida.financialcontrol.infra.repositories.inmemory.TransactionsInMemoryRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAllTransactionsTest {

    @Mock
    private Transactions transactions;
    private List<Transaction> transactionsMock;
    @InjectMocks
    private FindAllTransactions findAllTransactions;

    @BeforeEach
    void setUp() {
        TransactionsInMemoryRepository repository = new TransactionsInMemoryRepository();
        this.transactionsMock = repository.findAll();
        when(this.transactions.findAll()).thenReturn(transactionsMock);
    }

    @Test
    void executeShouldReturnAListOfTransaction() {
        List<Transaction> transactionsReturned = findAllTransactions.execute();

        assertEquals(transactionsMock, transactionsReturned);
        verify(transactions).findAll();
    }

}