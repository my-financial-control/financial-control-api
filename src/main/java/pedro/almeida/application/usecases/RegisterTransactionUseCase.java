package pedro.almeida.application.usecases;

import pedro.almeida.domain.models.Transaction;
import pedro.almeida.domain.repositories.Transactions;
import pedro.almeida.domain.usecases.RegisterTransaction;

public class RegisterTransactionUseCase implements RegisterTransaction {

    private final Transactions transactions;

    public RegisterTransactionUseCase(Transactions transactions) {
        this.transactions = transactions;
    }

    @Override
    public Transaction execute(Transaction transaction) {
        return this.transactions.save(transaction);
    }

}
