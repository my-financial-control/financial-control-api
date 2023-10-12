package pedro.almeida.financialcontrol.application.usecases;

import pedro.almeida.financialcontrol.domain.models.Borrowing;
import pedro.almeida.financialcontrol.domain.repositories.Borrowings;
import pedro.almeida.financialcontrol.domain.usecases.FindAllBorrowings;

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
