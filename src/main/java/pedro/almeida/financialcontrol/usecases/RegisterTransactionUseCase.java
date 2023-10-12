package pedro.almeida.financialcontrol.usecases;

import pedro.almeida.financialcontrol.domain.models.Transaction;
import pedro.almeida.financialcontrol.domain.repositories.Transactions;
import pedro.almeida.financialcontrol.domain.usecases.RegisterTransaction;

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
