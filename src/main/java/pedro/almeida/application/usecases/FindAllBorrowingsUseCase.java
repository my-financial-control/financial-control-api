package pedro.almeida.application.usecases;

import pedro.almeida.domain.models.Borrowing;
import pedro.almeida.domain.repositories.Borrowings;
import pedro.almeida.domain.usecases.FindAllBorrowings;

import java.util.List;

public class FindAllBorrowingsUseCase implements FindAllBorrowings {

    private final Borrowings borrowings;


    public FindAllBorrowingsUseCase(Borrowings borrowings) {
        this.borrowings = borrowings;
    }


    @Override
    public List<Borrowing> execute() {
        return this.borrowings.findAll();
    }

}
