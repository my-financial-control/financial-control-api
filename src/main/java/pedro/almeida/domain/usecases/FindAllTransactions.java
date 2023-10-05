package pedro.almeida.domain.usecases;

import pedro.almeida.domain.models.Transaction;

import java.util.List;

public interface FindAllTransactions {

    List<Transaction> execute();

}
