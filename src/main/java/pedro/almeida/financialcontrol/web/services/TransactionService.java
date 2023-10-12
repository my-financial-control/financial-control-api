package pedro.almeida.financialcontrol.web.services;

import org.springframework.stereotype.Service;
import pedro.almeida.financialcontrol.domain.models.Transaction;
import pedro.almeida.financialcontrol.domain.usecases.FindAllTransactions;
import pedro.almeida.financialcontrol.repositories.inmemory.TransactionsInMemoryRepository;
import pedro.almeida.financialcontrol.usecases.FindAllTransactionsUseCase;

import java.util.List;

@Service
public class TransactionService {

    private final FindAllTransactions findAllTransactions = new FindAllTransactionsUseCase(new TransactionsInMemoryRepository());

    public List<Transaction> findAll() {
        return this.findAllTransactions.execute();
    }

}
