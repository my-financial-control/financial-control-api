package pedro.almeida.financialcontrol.web.services;

import org.springframework.stereotype.Service;
import pedro.almeida.financialcontrol.domain.models.Transaction;
import pedro.almeida.financialcontrol.domain.usecases.FindAllTransactions;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
public class TransactionService {

    private final FindAllTransactions findAllTransactions;

    public TransactionService(FindAllTransactions findAllTransactions) {
        this.findAllTransactions = findAllTransactions;
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
