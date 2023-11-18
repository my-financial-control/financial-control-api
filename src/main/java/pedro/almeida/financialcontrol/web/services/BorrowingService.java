package pedro.almeida.financialcontrol.web.services;

import org.springframework.stereotype.Service;
import pedro.almeida.financialcontrol.domain.models.Borrowing;
import pedro.almeida.financialcontrol.domain.usecases.RegisterBorrowing;

@Service
public class BorrowingService {

    private final RegisterBorrowing registerBorrowing;

    public BorrowingService(RegisterBorrowing registerBorrowing) {
        this.registerBorrowing = registerBorrowing;
    }

    public Borrowing register(Borrowing borrowing) {
        return this.registerBorrowing.execute(borrowing);
    }

}
