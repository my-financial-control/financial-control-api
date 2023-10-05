package pedro.almeida.domain.repositories;

import pedro.almeida.domain.models.Transaction;

import java.time.Month;
import java.util.List;

public interface Transactions {

    Transaction save(Transaction transaction);
    List<Transaction> findAll();
    List<Transaction> findAll(Month month);

}
