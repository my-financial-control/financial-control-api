package pedro.almeida.application.usecases;

import pedro.almeida.domain.models.Transaction;
import pedro.almeida.domain.repositories.Transactions;
import pedro.almeida.domain.usecases.FindTransactionsByMonth;

import java.time.Month;
import java.util.List;

public class FindTransactionByMonthUseCase implements FindTransactionsByMonth {

    private final Transactions transactions;

    public FindTransactionByMonthUseCase(Transactions transactions) {
        this.transactions = transactions;
    }

    @Override
    public List<Transaction> execute(Month month) {
        return transactions.findAll(month);
    }

}
