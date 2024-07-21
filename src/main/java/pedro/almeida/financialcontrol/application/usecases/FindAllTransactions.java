package pedro.almeida.financialcontrol.application.usecases;

import pedro.almeida.financialcontrol.domain.models.Transaction;
import pedro.almeida.financialcontrol.domain.repositories.Transactions;

import java.time.Month;
import java.util.List;

public class FindAllTransactions {

    private final Transactions transactions;

    public FindAllTransactions(Transactions transactions) {
        this.transactions = transactions;
    }

    public List<Transaction> execute() {
        return this.transactions.findAll();
    }

    public List<Transaction> execute(Month month, int year) {
        return transactions.findAll(month, year);
    }

}
