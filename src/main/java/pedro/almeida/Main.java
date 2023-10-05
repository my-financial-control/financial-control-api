package pedro.almeida;

import pedro.almeida.application.usecases.*;
import pedro.almeida.domain.models.*;
import pedro.almeida.domain.repositories.Borrowings;
import pedro.almeida.domain.repositories.Transactions;
import pedro.almeida.domain.usecases.*;
import pedro.almeida.infra.repositories.inmemory.BorrowingInMemoryRepository;
import pedro.almeida.infra.repositories.inmemory.TransactionsInMemoryRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        System.out.println("=========== Controle de Despesas ===========");
//        registerTransaction();
//        findAllTransactions();
//        findByMonth();
//        findAllBorrowings();
//        registerBorrowing();
        payBorrowing();
    }

    public static void payBorrowing() {
        Borrowings borrowings = new BorrowingInMemoryRepository();
        PayParcelBorrowing payParcelBorrowing = new PayParcelBorrowingUseCase(borrowings);
        System.out.println(borrowings.findAll());
        Scanner scanner = new Scanner(System.in);
        UUID uuid = UUID.fromString(scanner.nextLine());
        LocalDate paymentDay = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        ParcelBorrowing parcel = new ParcelBorrowing(paymentDay, new BigDecimal(scanner.nextLine()));
        payParcelBorrowing.execute(uuid, parcel);
        System.out.println(borrowings.findById(uuid));
    }

    public static void registerBorrowing() {
        Borrowings borrowings = new BorrowingInMemoryRepository();
        RegisterBorrowing registerBorrowing = new RegisterBorrowingUseCase(borrowings);
        Scanner scanner = new Scanner(System.in);
        Borrower borrower = new Borrower(scanner.nextLine());
        Borrowing borrowing = new Borrowing(borrower, scanner.nextBigDecimal());
        registerBorrowing.execute(borrowing);
        System.out.println(borrowings.findAll());
    }

    public static void findAllBorrowings() {
        Borrowings borrowings = new BorrowingInMemoryRepository();
        FindAllBorrowings findAllBorrowings = new FindAllBorrowingsUseCase(borrowings);
        System.out.println(findAllBorrowings.execute());
    }

    private static void findByMonth() {
        Transactions transactions = new TransactionsInMemoryRepository();
        FindTransactionsByMonth findTransactionsByMonth = new FindTransactionByMonthUseCase(transactions);
        System.out.println(findTransactionsByMonth.execute(Month.OCTOBER));
    }

    private static void findAllTransactions() {
        Transactions transactions = new TransactionsInMemoryRepository();
        FindAllTransactions findAllTransactions = new FindAllTransactionsUseCase(transactions);
        System.out.println(findAllTransactions.execute());
    }

    private static void registerTransaction() {
        Transactions transactions = new TransactionsInMemoryRepository();
        RegisterTransaction registerTransaction = new RegisterTransactionUseCase(transactions);
        Scanner scanner = new Scanner(System.in);
        String title = scanner.nextLine();
        String description = scanner.nextLine();
        BigDecimal value = scanner.nextBigDecimal();
        scanner.nextLine();
        TransactionType type = TransactionType.valueOf(scanner.nextLine());
        Month currentMonth = Month.valueOf(scanner.nextLine());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(scanner.nextLine(), formatter);
        Transaction transaction = new Transaction(title, description, value, type, currentMonth, date);
        registerTransaction.execute(transaction);
        System.out.println(transactions.findAll());
    }

}
