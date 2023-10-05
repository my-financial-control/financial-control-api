package pedro.almeida.domain.repositories;

import pedro.almeida.domain.models.Borrowing;

import java.util.List;
import java.util.UUID;

public interface Borrowings {

    Borrowing save(Borrowing borrowing);
    List<Borrowing> findAll();
    Borrowing findById(UUID uuid);

}
