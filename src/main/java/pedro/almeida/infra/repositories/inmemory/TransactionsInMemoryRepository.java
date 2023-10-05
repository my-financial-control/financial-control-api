package pedro.almeida.infra.repositories.inmemory;

import pedro.almeida.domain.models.Transaction;
import pedro.almeida.domain.models.TransactionType;
import pedro.almeida.domain.repositories.Transactions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TransactionsInMemoryRepository implements Transactions {

    private Map<UUID, Transaction> transactions = seed();

    @Override
    public Transaction save(Transaction transaction) {
        this.transactions.put(transaction.getId(), transaction);
        return transaction;
    }

    @Override
    public List<Transaction> findAll() {
        return this.transactions.values().stream().toList();
    }

    @Override
    public List<Transaction> findAll(Month month) {
        return this.findAll().stream().filter(transaction -> transaction.getCurrentMonth().equals(month)).toList();
    }

    private static Map<UUID, Transaction> seed() {
        Map<UUID, Transaction> transactions = new HashMap<>();
        Transaction transaction1 = new Transaction(
                "Compra de roupas",
                "Gastos com roupas de outono",
                new BigDecimal("150.50"),
                TransactionType.EXPENSE,
                Month.SEPTEMBER,
                LocalDate.of(2023, 9, 15)
        );
        Transaction transaction2 = new Transaction(
                "Salário",
                "Pagamento mensal",
                new BigDecimal("2500.00"),
                TransactionType.CREDIT,
                Month.SEPTEMBER,
                LocalDate.of(2023, 9, 30)
        );
        Transaction transaction3 = new Transaction(
                "Aluguel",
                "Pagamento do aluguel",
                new BigDecimal("1000.00"),
                TransactionType.EXPENSE,
                Month.OCTOBER,
                LocalDate.of(2023, 10, 5)
        );
        Transaction transaction4 = new Transaction(
                "Venda de produtos",
                "Receita da venda de produtos",
                new BigDecimal("800.00"),
                TransactionType.CREDIT,
                Month.OCTOBER,
                LocalDate.of(2023, 10, 18)
        );
        Transaction transaction5 = new Transaction(
                "Restaurante",
                "Jantar fora com amigos",
                new BigDecimal("75.80"),
                TransactionType.EXPENSE,
                Month.NOVEMBER,
                LocalDate.of(2023, 11, 12)
        );
        transactions.put(transaction1.getId(), transaction1);
        transactions.put(transaction2.getId(), transaction2);
        transactions.put(transaction3.getId(), transaction3);
        transactions.put(transaction4.getId(), transaction4);
        transactions.put(transaction5.getId(), transaction5);
        return transactions;
    }

}
