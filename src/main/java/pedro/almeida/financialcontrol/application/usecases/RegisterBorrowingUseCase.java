package pedro.almeida.financialcontrol.application.usecases;

import pedro.almeida.financialcontrol.domain.models.Borrowing;
import pedro.almeida.financialcontrol.domain.repositories.Borrowings;
import pedro.almeida.financialcontrol.domain.usecases.RegisterBorrowing;

public class RegisterBorrowingUseCase implements RegisterBorrowing {

    private final Borrowings borrowings;

    public RegisterBorrowingUseCase(Borrowings borrowings) {
        this.borrowings = borrowings;
    }

    @Override
    public Borrowing execute(Borrowing borrowing) {
        return this.borrowings.save(borrowing);
    }

}
