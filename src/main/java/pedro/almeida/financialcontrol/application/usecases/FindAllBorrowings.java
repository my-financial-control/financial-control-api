package pedro.almeida.financialcontrol.application.usecases;

import org.springframework.stereotype.Component;
import pedro.almeida.financialcontrol.application.dtos.response.BorrowingResponseDTO;
import pedro.almeida.financialcontrol.domain.models.Borrowing;
import pedro.almeida.financialcontrol.domain.repositories.Borrowings;

import java.util.List;

@Component
public class FindAllBorrowings {

    private final Borrowings borrowings;


    public FindAllBorrowings(Borrowings borrowings) {
        this.borrowings = borrowings;
    }

    public List<BorrowingResponseDTO> execute() {
        List<Borrowing> bgs = borrowings.findAll();
        return BorrowingResponseDTO.toBorrowingResponseDTO(bgs);
    }

}
