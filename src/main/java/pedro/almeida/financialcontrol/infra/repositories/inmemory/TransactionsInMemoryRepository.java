package pedro.almeida.financialcontrol.infra.repositories.inmemory;

import pedro.almeida.financialcontrol.domain.factories.TransactionFactory;
import pedro.almeida.financialcontrol.domain.models.Transaction;
import pedro.almeida.financialcontrol.domain.models.TransactionCategory;
import pedro.almeida.financialcontrol.domain.models.TransactionType;
import pedro.almeida.financialcontrol.domain.repositories.Transactions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TransactionsInMemoryRepository implements Transactions {

    private final Map<UUID, Transaction> transactions = seed();

    @Override
    public Transaction save(Transaction transaction) {
        transactions.put(transaction.getId(), transaction);
        return transaction;
    }

    @Override
    public List<Transaction> findAll(String type, Integer month, Integer year) {
        return List.of();
    }

    @Override
    public BigDecimal sumOfCredits() {
        List<Transaction> allCredits = findAll(TransactionType.CREDIT.name(), null, null);
        return allCredits.stream().map(Transaction::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal sumOfCredits(Month month, int year) {
        List<Transaction> allCreditsByMonth = findAll(TransactionType.CREDIT.name(), month.getValue(), year);
        return allCreditsByMonth.stream().map(Transaction::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal sumOfExpenses() {
        List<Transaction> allExpenses = findAll(TransactionType.EXPENSE.name(), null, null);
        return allExpenses.stream().map(Transaction::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal sumOfExpenses(Month month, int year) {
        List<Transaction> allExpensesByMonth = findAll(TransactionType.EXPENSE.name(), month.getValue(), year);
        return allExpensesByMonth.stream().map(Transaction::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static Map<UUID, Transaction> seed() {
        Map<UUID, Transaction> transactions = new HashMap<>();
        Transaction transaction1 = TransactionFactory.buildTransaction(
                "Compra de roupas",
                "Gastos com roupas de outono",
                new BigDecimal("150.50"),
                "EXPENSE",
                9,
                LocalDate.of(2023, 9, 15),
                new TransactionCategory(UUID.randomUUID(), "Categoria 1", "")
        );
        Transaction transaction2 = TransactionFactory.buildTransaction(
                "Salário",
                "Pagamento mensal",
                new BigDecimal("2500.00"),
                "CREDIT",
                9,
                LocalDate.of(2023, 9, 30),
                null
        );
        Transaction transaction3 = TransactionFactory.buildTransaction(
                "Aluguel",
                "Pagamento do aluguel",
                new BigDecimal("1000.00"),
                "EXPENSE",
                10,
                LocalDate.of(2023, 10, 5),
                new TransactionCategory(UUID.randomUUID(), "Categoria 2", "")
        );
        Transaction transaction4 = TransactionFactory.buildTransaction(
                "Venda de produtos",
                "Receita da venda de produtos",
                new BigDecimal("800.00"),
                "CREDIT",
                10,
                LocalDate.of(2023, 10, 18),
                null
        );
        Transaction transaction5 = TransactionFactory.buildTransaction(
                "Restaurante",
                "Jantar fora com amigos",
                new BigDecimal("75.80"),
                "EXPENSE",
                11,
                LocalDate.of(2023, 11, 12),
                new TransactionCategory(UUID.randomUUID(), "Categoria 3", "")
        );
        transactions.put(transaction1.getId(), transaction1);
        transactions.put(transaction2.getId(), transaction2);
        transactions.put(transaction3.getId(), transaction3);
        transactions.put(transaction4.getId(), transaction4);
        transactions.put(transaction5.getId(), transaction5);
        return transactions;
    }

}
