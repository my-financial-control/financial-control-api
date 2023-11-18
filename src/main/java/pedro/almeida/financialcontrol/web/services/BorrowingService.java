package pedro.almeida.financialcontrol.web.services;

import org.springframework.stereotype.Service;
import pedro.almeida.financialcontrol.domain.models.Borrowing;
import pedro.almeida.financialcontrol.domain.usecases.FindAllBorrowings;
import pedro.almeida.financialcontrol.domain.usecases.RegisterBorrowing;

import java.util.List;

@Service
public class BorrowingService {

    private final RegisterBorrowing registerBorrowing;
    private final FindAllBorrowings findAllBorrowings;

    public BorrowingService(RegisterBorrowing registerBorrowing, FindAllBorrowings findAllBorrowings) {
        this.registerBorrowing = registerBorrowing;
        this.findAllBorrowings = findAllBorrowings;
    }

    public Borrowing register(Borrowing borrowing) {
        return this.registerBorrowing.execute(borrowing);
    }

    public List<Borrowing> findAll() {
        return this.findAllBorrowings.execute();
    }

}
