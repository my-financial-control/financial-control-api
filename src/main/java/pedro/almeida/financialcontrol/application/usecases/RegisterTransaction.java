package pedro.almeida.financialcontrol.application.usecases;

import org.springframework.stereotype.Component;
import pedro.almeida.financialcontrol.domain.models.Transaction;
import pedro.almeida.financialcontrol.domain.repositories.Transactions;

@Component
public class RegisterTransaction {

    private final Transactions transactions;

    public RegisterTransaction(Transactions transactions) {
        this.transactions = transactions;
    }

    public Transaction execute(Transaction transaction) {
        return transactions.save(transaction);
    }

}
