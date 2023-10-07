package pedro.almeida.application.usecases;

import pedro.almeida.domain.models.Borrowing;
import pedro.almeida.domain.repositories.Borrowings;
import pedro.almeida.domain.usecases.RegisterBorrowing;

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
