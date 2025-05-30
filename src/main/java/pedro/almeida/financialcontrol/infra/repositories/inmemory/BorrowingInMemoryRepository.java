package pedro.almeida.financialcontrol.infra.repositories.inmemory;

import pedro.almeida.financialcontrol.domain.models.Borrower;
import pedro.almeida.financialcontrol.domain.models.Borrowing;
import pedro.almeida.financialcontrol.domain.models.ParcelBorrowing;
import pedro.almeida.financialcontrol.domain.repositories.Borrowings;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BorrowingInMemoryRepository implements Borrowings {

    private final Map<UUID, Borrowing> borrowings = seed();

    @Override
    public Borrowing save(Borrowing borrowing) {
        borrowings.put(borrowing.getId(), borrowing);
        return borrowing;
    }

    @Override
    public List<Borrowing> findAll() {
        return borrowings.values().stream().toList();
    }

    @Override
    public List<Borrowing> findAll(Month month, int year) {
        return borrowings.values().stream().filter(borrowing -> borrowing.getDate().getMonth().equals(month) && borrowing.getDate().getYear() == year).toList();
    }

    @Override
    public Borrowing findById(UUID id) {
        return borrowings.get(id);
    }

    @Override
    public BigDecimal sumOfRemainingPayment() {
        List<Borrowing> allBorrowings = findAll();
        return allBorrowings.stream().map(Borrowing::remainingPaymentAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal sumOfRemainingPayment(Month month, int year) {
        List<Borrowing> allBorrowings = findAll(month, year);
        return allBorrowings.stream().map(Borrowing::remainingPaymentAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static Map<UUID, Borrowing> seed() {
        Map<UUID, Borrowing> borrowings = new HashMap<>();
        Borrower borrower1 = new Borrower("José de Souza");
        Borrowing borrowing1 = new Borrowing(borrower1, new BigDecimal("50.0"), "Empréstimo para José", LocalDate.now());
        borrowings.put(borrowing1.getId(), borrowing1);

        Borrower borrower2 = new Borrower("Maria Silva");
        Borrowing borrowing2 = new Borrowing(borrower2, new BigDecimal("75.0"), "Empréstimo para Maria", LocalDate.now());
        borrowing2.payParcel(new ParcelBorrowing(new BigDecimal("25.0")));
        borrowing2.payParcel(new ParcelBorrowing(new BigDecimal("10.0")));
        borrowing2.payParcel(new ParcelBorrowing(new BigDecimal("15.0")));
        borrowings.put(borrowing2.getId(), borrowing2);

        Borrower borrower3 = new Borrower("João Pereira");
        Borrowing borrowing3 = new Borrowing(borrower3, new BigDecimal("100.0"), "Empréstimo para João", LocalDate.now());
        borrowing3.payParcel(new ParcelBorrowing(new BigDecimal("100.0")));
        borrowings.put(borrowing3.getId(), borrowing3);

        Borrower borrower4 = new Borrower("Ana Santos");
        Borrowing borrowing4 = new Borrowing(borrower4, new BigDecimal("30.0"), "Empréstimo para Ana", LocalDate.now());
        borrowings.put(borrowing4.getId(), borrowing4);

        Borrower borrower5 = new Borrower("Carlos Mendes");
        Borrowing borrowing5 = new Borrowing(borrower5, new BigDecimal("60.0"), "Empréstimo para Carlos", LocalDate.now());
        borrowings.put(borrowing5.getId(), borrowing5);

        return borrowings;
    }

}
