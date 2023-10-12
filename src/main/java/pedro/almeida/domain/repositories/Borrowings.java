package pedro.almeida.domain.repositories;

import pedro.almeida.domain.models.Borrowing;

import java.math.BigDecimal;
import java.time.Month;
import java.util.List;
import java.util.UUID;

public interface Borrowings {

    Borrowing save(Borrowing borrowing);
    List<Borrowing> findAll();
    List<Borrowing> findAll(Month month, int year);
    Borrowing findById(UUID uuid);
    BigDecimal sumOfRemainingPayment();
    BigDecimal sumOfRemainingPayment(Month month, int year);

}
