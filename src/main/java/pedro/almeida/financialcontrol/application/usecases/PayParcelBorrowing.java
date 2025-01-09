package pedro.almeida.financialcontrol.application.usecases;

import org.springframework.stereotype.Component;
import pedro.almeida.financialcontrol.application.dtos.request.PayParcelBorrowingRequestDTO;
import pedro.almeida.financialcontrol.domain.models.Borrowing;
import pedro.almeida.financialcontrol.domain.repositories.Borrowings;

import java.util.UUID;

@Component
public class PayParcelBorrowing {

    private final Borrowings borrowings;

    public PayParcelBorrowing(Borrowings borrowings) {
        this.borrowings = borrowings;
    }

    public void execute(UUID borrowingId, PayParcelBorrowingRequestDTO parcelDTO) {
        Borrowing borrowing = borrowings.findById(borrowingId);
        borrowing.payParcel(parcelDTO.toParcelBorrowing());
        borrowings.save(borrowing);
    }

}
