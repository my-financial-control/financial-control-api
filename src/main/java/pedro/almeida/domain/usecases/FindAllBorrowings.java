package pedro.almeida.domain.usecases;

import pedro.almeida.domain.models.Borrowing;

import java.util.List;

public interface FindAllBorrowings {

    List<Borrowing> execute();

}
