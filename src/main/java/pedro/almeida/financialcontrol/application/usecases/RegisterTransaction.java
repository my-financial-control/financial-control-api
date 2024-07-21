package pedro.almeida.financialcontrol.application.usecases;

import pedro.almeida.financialcontrol.domain.models.Transaction;
import pedro.almeida.financialcontrol.domain.repositories.Transactions;

public class RegisterTransaction {

    private final Transactions transactions;

    public RegisterTransaction(Transactions transactions) {
        this.transactions = transactions;
    }

    public Transaction execute(Transaction transaction) {
        return this.transactions.save(transaction);
    }

}
