package pedro.almeida.financialcontrol.application.usecases;

import org.springframework.stereotype.Component;
import pedro.almeida.financialcontrol.domain.models.Borrowing;
import pedro.almeida.financialcontrol.domain.repositories.Borrowings;

@Component
public class RegisterBorrowing {

    private final Borrowings borrowings;

    public RegisterBorrowing(Borrowings borrowings) {
        this.borrowings = borrowings;
    }

    public Borrowing execute(Borrowing borrowing) {
        return borrowings.save(borrowing);
    }

}
