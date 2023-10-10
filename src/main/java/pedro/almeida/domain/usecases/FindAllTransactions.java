package pedro.almeida.domain.usecases;

import pedro.almeida.domain.models.Transaction;

import java.time.Month;
import java.util.List;

public interface FindAllTransactions {

    List<Transaction> execute();
    List<Transaction> execute(Month month, int year);

}
