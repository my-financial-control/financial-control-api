package pedro.almeida.financialcontrol.domain.usecases;

import pedro.almeida.financialcontrol.domain.models.Borrowing;

import java.util.List;

public interface FindAllBorrowings {

    List<Borrowing> execute();

}
