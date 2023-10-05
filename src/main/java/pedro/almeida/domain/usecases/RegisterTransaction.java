package pedro.almeida.domain.usecases;

import pedro.almeida.domain.models.Transaction;

public interface RegisterTransaction {

    Transaction execute(Transaction transaction);

}
