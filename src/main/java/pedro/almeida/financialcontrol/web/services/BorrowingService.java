package pedro.almeida.financialcontrol.web.services;

import org.springframework.stereotype.Service;
import pedro.almeida.financialcontrol.application.usecases.*;
import pedro.almeida.financialcontrol.domain.models.*;

import java.util.List;
import java.util.UUID;

@Service
public class BorrowingService {

    private final RegisterBorrowing registerBorrowing;
    private final FindAllBorrowings findAllBorrowings;
    private final PayParcelBorrowing payParcelBorrowing;

    public BorrowingService(RegisterBorrowing registerBorrowing, FindAllBorrowings findAllBorrowings, PayParcelBorrowing payParcelBorrowing) {
        this.registerBorrowing = registerBorrowing;
        this.findAllBorrowings = findAllBorrowings;
        this.payParcelBorrowing = payParcelBorrowing;
    }

    public Borrowing register(Borrowing borrowing) {
        return registerBorrowing.execute(borrowing);
    }

    public List<Borrowing> findAll() {
        return findAllBorrowings.execute();
    }

    public void payParcel(UUID id, ParcelBorrowing parcel) {
        payParcelBorrowing.execute(id, parcel);
    }

}
