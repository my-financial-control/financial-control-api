package pedro.almeida.financialcontrol.domain.usecases;

import pedro.almeida.financialcontrol.domain.models.Transaction;

public interface RegisterTransaction {

    Transaction execute(Transaction transaction);

}
