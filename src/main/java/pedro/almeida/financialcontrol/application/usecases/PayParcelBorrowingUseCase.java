package pedro.almeida.financialcontrol.application.usecases;

import pedro.almeida.financialcontrol.domain.models.Borrowing;
import pedro.almeida.financialcontrol.domain.models.ParcelBorrowing;
import pedro.almeida.financialcontrol.domain.repositories.Borrowings;
import pedro.almeida.financialcontrol.domain.usecases.PayParcelBorrowing;

import java.util.UUID;

public class PayParcelBorrowingUseCase implements PayParcelBorrowing {

    private final Borrowings borrowings;

    public PayParcelBorrowingUseCase(Borrowings borrowings) {
        this.borrowings = borrowings;
    }

    @Override
    public void execute(UUID id, ParcelBorrowing parcel) {
        Borrowing borrowing = this.borrowings.findById(id);
        borrowing.payParcel(parcel);
        this.borrowings.save(borrowing);
    }

}
