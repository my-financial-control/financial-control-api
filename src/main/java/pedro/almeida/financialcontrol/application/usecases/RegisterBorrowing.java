package pedro.almeida.financialcontrol.application.usecases;

import pedro.almeida.financialcontrol.domain.models.Borrowing;
import pedro.almeida.financialcontrol.domain.repositories.Borrowings;

public class RegisterBorrowing {

    private final Borrowings borrowings;

    public RegisterBorrowing(Borrowings borrowings) {
        this.borrowings = borrowings;
    }

    public Borrowing execute(Borrowing borrowing) {
        return this.borrowings.save(borrowing);
    }

}
