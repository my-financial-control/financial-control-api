package pedro.almeida.application.usecases;

import pedro.almeida.domain.models.Transaction;
import pedro.almeida.domain.repositories.Transactions;
import pedro.almeida.domain.usecases.FindAllTransactions;

import java.time.Month;
import java.util.List;

public class FindAllTransactionsUseCase implements FindAllTransactions {

    private final Transactions transactions;

    public FindAllTransactionsUseCase(Transactions transactions) {
        this.transactions = transactions;
    }

    @Override
    public List<Transaction> execute() {
        return this.transactions.findAll();
    }

    @Override
    public List<Transaction> execute(Month month, int year) {
        return transactions.findAll(month, year);
    }

}
