package pedro.almeida.application.usecases;

import pedro.almeida.domain.models.Borrowing;
import pedro.almeida.domain.repositories.Borrowings;
import pedro.almeida.domain.usecases.RegisterBorrowing;

public class RegisterBorrowingUseCase implements RegisterBorrowing {

    private final Borrowings borrowingsRepository;

    public RegisterBorrowingUseCase(Borrowings borrowingsRepository) {
        this.borrowingsRepository = borrowingsRepository;
    }

    @Override
    public Borrowing execute(Borrowing borrowing) {
        return this.borrowingsRepository.save(borrowing);
    }

}
