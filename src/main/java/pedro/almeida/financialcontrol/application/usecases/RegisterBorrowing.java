package pedro.almeida.financialcontrol.application.usecases;

import org.springframework.stereotype.Component;
import pedro.almeida.financialcontrol.application.dtos.request.BorrowingRequestDTO;
import pedro.almeida.financialcontrol.application.dtos.response.BorrowingResponseDTO;
import pedro.almeida.financialcontrol.domain.models.Borrowing;
import pedro.almeida.financialcontrol.domain.repositories.Borrowings;

@Component
public class RegisterBorrowing {

    private final Borrowings borrowings;

    public RegisterBorrowing(Borrowings borrowings) {
        this.borrowings = borrowings;
    }

    public BorrowingResponseDTO execute(BorrowingRequestDTO borrowingDTO) {
        Borrowing borrowing = borrowings.save(borrowingDTO.toBorrowing());
        return new BorrowingResponseDTO(borrowing);
    }

}
