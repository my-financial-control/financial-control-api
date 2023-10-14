package pedro.almeida.financialcontrol.web.services;

import org.springframework.stereotype.Service;
import pedro.almeida.financialcontrol.domain.models.Transaction;
import pedro.almeida.financialcontrol.domain.usecases.FindAllTransactions;
import pedro.almeida.financialcontrol.domain.usecases.RegisterTransaction;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
public class TransactionService {

    private final FindAllTransactions findAllTransactions;
    private final RegisterTransaction registerTransaction;

    public TransactionService(FindAllTransactions findAllTransactions, RegisterTransaction registerTransaction) {
        this.findAllTransactions = findAllTransactions;
        this.registerTransaction = registerTransaction;
    }

    public Transaction register(Transaction transaction) {
        return this.registerTransaction.execute(transaction);
    }

    public List<Transaction> findAll(Integer month, Integer year) {
        if (month == null) {
            return this.findAllTransactions.execute();
        }
        if (year == null) {
            year = LocalDate.now().getYear();
        }
        return this.findAllTransactions.execute(Month.of(month), year);
    }

}
