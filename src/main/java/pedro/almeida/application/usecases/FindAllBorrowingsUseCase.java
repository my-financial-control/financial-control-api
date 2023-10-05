package pedro.almeida.application.usecases;

import pedro.almeida.domain.models.Borrowing;
import pedro.almeida.domain.repositories.Borrowings;
import pedro.almeida.domain.usecases.FindAllBorrowings;

import java.util.List;

public class FindAllBorrowingsUseCase implements FindAllBorrowings {

    private final Borrowings borrowingsRepository;


    public FindAllBorrowingsUseCase(Borrowings borrowingsRepository) {
        this.borrowingsRepository = borrowingsRepository;
    }


    @Override
    public List<Borrowing> execute() {
        return this.borrowingsRepository.findAll();
    }

}
