package pedro.almeida.domain.usecases;

import pedro.almeida.domain.models.Borrowing;

public interface RegisterBorrowing {

    Borrowing execute(Borrowing borrowing);

}
