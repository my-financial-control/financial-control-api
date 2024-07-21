package pedro.almeida.financialcontrol.application.usecases;

import pedro.almeida.financialcontrol.domain.models.Borrowing;
import pedro.almeida.financialcontrol.domain.repositories.Borrowings;

import java.util.List;

public class FindAllBorrowings {

    private final Borrowings borrowings;


    public FindAllBorrowings(Borrowings borrowings) {
        this.borrowings = borrowings;
    }


    public List<Borrowing> execute() {
        return borrowings.findAll();
    }

}
