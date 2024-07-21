package pedro.almeida.financialcontrol.application.usecases;

import pedro.almeida.financialcontrol.domain.models.Borrowing;
import pedro.almeida.financialcontrol.domain.models.ParcelBorrowing;
import pedro.almeida.financialcontrol.domain.repositories.Borrowings;

import java.util.UUID;

public class PayParcelBorrowing {

    private final Borrowings borrowings;

    public PayParcelBorrowing(Borrowings borrowings) {
        this.borrowings = borrowings;
    }

    public void execute(UUID id, ParcelBorrowing parcel) {
        Borrowing borrowing = this.borrowings.findById(id);
        borrowing.payParcel(parcel);
        this.borrowings.save(borrowing);
    }

}
