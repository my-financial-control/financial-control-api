package pedro.almeida.financialcontrol.domain.usecases;

import pedro.almeida.financialcontrol.domain.models.Borrowing;

public interface RegisterBorrowing {

    Borrowing execute(Borrowing borrowing);

}
