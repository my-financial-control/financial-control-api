package pedro.almeida.domain.usecases;

import pedro.almeida.domain.models.Transaction;

import java.time.Month;
import java.util.List;

public interface FindTransactionsByMonth {

    List<Transaction> execute(Month month, int year);

}
