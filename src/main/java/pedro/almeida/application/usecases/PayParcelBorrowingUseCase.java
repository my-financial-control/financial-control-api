package pedro.almeida.application.usecases;

import pedro.almeida.domain.models.Borrowing;
import pedro.almeida.domain.models.ParcelBorrowing;
import pedro.almeida.domain.repositories.Borrowings;
import pedro.almeida.domain.usecases.PayParcelBorrowing;

import java.util.UUID;

public class PayParcelBorrowingUseCase implements PayParcelBorrowing {

    private final Borrowings borrowingsRepository;

    public PayParcelBorrowingUseCase(Borrowings borrowingsRepository) {
        this.borrowingsRepository = borrowingsRepository;
    }

    @Override
    public void execute(UUID uuid, ParcelBorrowing parcel) {
        Borrowing borrowing = this.borrowingsRepository.findById(uuid);
        borrowing.payParcel(parcel);
        this.borrowingsRepository.save(borrowing);
    }

}