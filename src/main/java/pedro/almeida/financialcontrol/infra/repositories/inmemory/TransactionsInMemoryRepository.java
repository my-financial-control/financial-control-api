package pedro.almeida.financialcontrol.infra.repositories.inmemory;

import pedro.almeida.financialcontrol.domain.models.Transaction;
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
    public List<Transaction> findAll(Month month, int year) {
        return this.findAll().stream().filter(transaction -> transaction.getCurrentMonth().equals(month) && transaction.getDate().getYear() == year).toList();
    }

    @Override
    public List<Transaction> findAll(TransactionType type) {
        return this.findAll().stream().filter(transaction -> transaction.getType().equals(type)).toList();
    }

    @Override
    public List<Transaction> findAll(Month month, int year, TransactionType type) {
        return this.findAll(month, year).stream().filter(transaction -> transaction.getType().equals(type)).toList();
    }

    @Override
    public BigDecimal sumOfCredits() {
        List<Transaction> allCredits = this.findAll(TransactionType.CREDIT);
        return allCredits.stream().map(Transaction::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal sumOfCredits(Month month, int year) {
        List<Transaction> allCreditsByMonth = this.findAll(month, year, TransactionType.CREDIT);
        return allCreditsByMonth.stream().map(Transaction::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal sumOfExpenses() {
        List<Transaction> allExpenses = this.findAll(TransactionType.EXPENSE);
        return allExpenses.stream().map(Transaction::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal sumOfExpenses(Month month, int year) {
        List<Transaction> allExpensesByMonth = this.findAll(month, year, TransactionType.EXPENSE);
        return allExpensesByMonth.stream().map(Transaction::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static Map<UUID, Transaction> seed() {
        Map<UUID, Transaction> transactions = new HashMap<>();
        Transaction transaction1 = new Transaction("Compra de roupas", "Gastos com roupas de outono", new BigDecimal("150.50"), TransactionType.EXPENSE, Month.SEPTEMBER, LocalDate.of(2023, 9, 15));
        Transaction transaction2 = new Transaction("Salário", "Pagamento mensal", new BigDecimal("2500.00"), TransactionType.CREDIT, Month.SEPTEMBER, LocalDate.of(2023, 9, 30));
        Transaction transaction3 = new Transaction("Aluguel", "Pagamento do aluguel", new BigDecimal("1000.00"), TransactionType.EXPENSE, Month.OCTOBER, LocalDate.of(2023, 10, 5));
        Transaction transaction4 = new Transaction("Venda de produtos", "Receita da venda de produtos", new BigDecimal("800.00"), TransactionType.CREDIT, Month.OCTOBER, LocalDate.of(2023, 10, 18));
        Transaction transaction5 = new Transaction("Restaurante", "Jantar fora com amigos", new BigDecimal("75.80"), TransactionType.EXPENSE, Month.NOVEMBER, LocalDate.of(2023, 11, 12));
        Transaction transaction6 = new Transaction("Compra de roupas", "Gastos com roupas de outono", new BigDecimal("150.50"), TransactionType.EXPENSE, Month.SEPTEMBER, LocalDate.of(2023, 9, 15));
        Transaction transaction7 = new Transaction("Salário", "Pagamento mensal", new BigDecimal("2500.00"), TransactionType.CREDIT, Month.SEPTEMBER, LocalDate.of(2023, 9, 30));
        Transaction transaction8 = new Transaction("Aluguel", "Pagamento do aluguel", new BigDecimal("1000.00"), TransactionType.EXPENSE, Month.OCTOBER, LocalDate.of(2023, 10, 5));
        Transaction transaction9 = new Transaction("Venda de produtos", "Receita da venda de produtos", new BigDecimal("800.00"), TransactionType.CREDIT, Month.OCTOBER, LocalDate.of(2023, 10, 18));
        Transaction transaction10 = new Transaction("Restaurante", "Jantar fora com amigos", new BigDecimal("75.80"), TransactionType.EXPENSE, Month.NOVEMBER, LocalDate.of(2023, 11, 12));
        Transaction transaction11 = new Transaction("Compra de roupas", "Gastos com roupas de outono", new BigDecimal("150.50"), TransactionType.EXPENSE, Month.SEPTEMBER, LocalDate.of(2023, 9, 15));
        Transaction transaction12 = new Transaction("Salário", "Pagamento mensal", new BigDecimal("2500.00"), TransactionType.CREDIT, Month.SEPTEMBER, LocalDate.of(2023, 9, 30));
        Transaction transaction13 = new Transaction("Aluguel", "Pagamento do aluguel", new BigDecimal("1000.00"), TransactionType.EXPENSE, Month.OCTOBER, LocalDate.of(2023, 10, 5));
        Transaction transaction14 = new Transaction("Venda de produtos", "Receita da venda de produtos", new BigDecimal("800.00"), TransactionType.CREDIT, Month.OCTOBER, LocalDate.of(2023, 10, 18));
        Transaction transaction15 = new Transaction("Restaurante", "Jantar fora com amigos", new BigDecimal("75.80"), TransactionType.EXPENSE, Month.NOVEMBER, LocalDate.of(2023, 11, 12));
        Transaction transaction16 = new Transaction("Compra de roupas", "Gastos com roupas de outono", new BigDecimal("150.50"), TransactionType.EXPENSE, Month.SEPTEMBER, LocalDate.of(2023, 9, 15));
        Transaction transaction17 = new Transaction("Salário", "Pagamento mensal", new BigDecimal("2500.00"), TransactionType.CREDIT, Month.SEPTEMBER, LocalDate.of(2023, 9, 30));
        Transaction transaction18 = new Transaction("Aluguel", "Pagamento do aluguel", new BigDecimal("1000.00"), TransactionType.EXPENSE, Month.OCTOBER, LocalDate.of(2023, 10, 5));
        Transaction transaction19 = new Transaction("Venda de produtos", "Receita da venda de produtos", new BigDecimal("800.00"), TransactionType.CREDIT, Month.OCTOBER, LocalDate.of(2023, 10, 18));
        Transaction transaction20 = new Transaction("Restaurante", "Jantar fora com amigos", new BigDecimal("75.80"), TransactionType.EXPENSE, Month.NOVEMBER, LocalDate.of(2023, 11, 12));
        Transaction transaction21 = new Transaction("Compra de roupas", "Gastos com roupas de outono", new BigDecimal("150.50"), TransactionType.EXPENSE, Month.SEPTEMBER, LocalDate.of(2023, 9, 15));
        Transaction transaction22 = new Transaction("Salário", "Pagamento mensal", new BigDecimal("2500.00"), TransactionType.CREDIT, Month.SEPTEMBER, LocalDate.of(2023, 9, 30));
        Transaction transaction23 = new Transaction("Aluguel", "Pagamento do aluguel", new BigDecimal("1000.00"), TransactionType.EXPENSE, Month.OCTOBER, LocalDate.of(2023, 10, 5));
        Transaction transaction24 = new Transaction("Venda de produtos", "Receita da venda de produtos", new BigDecimal("800.00"), TransactionType.CREDIT, Month.OCTOBER, LocalDate.of(2023, 10, 18));
        Transaction transaction25 = new Transaction("Restaurante", "Jantar fora com amigos", new BigDecimal("75.80"), TransactionType.EXPENSE, Month.NOVEMBER, LocalDate.of(2023, 11, 12));
        Transaction transaction26 = new Transaction("Compra de roupas", "Gastos com roupas de outono", new BigDecimal("150.50"), TransactionType.EXPENSE, Month.SEPTEMBER, LocalDate.of(2023, 9, 15));
        Transaction transaction27 = new Transaction("Salário", "Pagamento mensal", new BigDecimal("2500.00"), TransactionType.CREDIT, Month.SEPTEMBER, LocalDate.of(2023, 9, 30));
        Transaction transaction28 = new Transaction("Aluguel", "Pagamento do aluguel", new BigDecimal("1000.00"), TransactionType.EXPENSE, Month.OCTOBER, LocalDate.of(2023, 10, 5));
        Transaction transaction29 = new Transaction("Venda de produtos", "Receita da venda de produtos", new BigDecimal("800.00"), TransactionType.CREDIT, Month.OCTOBER, LocalDate.of(2023, 10, 18));
        Transaction transaction30 = new Transaction("Restaurante", "Jantar fora com amigos", new BigDecimal("75.80"), TransactionType.EXPENSE, Month.NOVEMBER, LocalDate.of(2023, 11, 12));
        Transaction transaction31 = new Transaction("Compra de roupas", "Gastos com roupas de outono", new BigDecimal("150.50"), TransactionType.EXPENSE, Month.SEPTEMBER, LocalDate.of(2023, 9, 15));
        Transaction transaction32 = new Transaction("Salário", "Pagamento mensal", new BigDecimal("2500.00"), TransactionType.CREDIT, Month.SEPTEMBER, LocalDate.of(2023, 9, 30));
        Transaction transaction33 = new Transaction("Aluguel", "Pagamento do aluguel", new BigDecimal("1000.00"), TransactionType.EXPENSE, Month.OCTOBER, LocalDate.of(2023, 10, 5));
        Transaction transaction34 = new Transaction("Venda de produtos", "Receita da venda de produtos", new BigDecimal("800.00"), TransactionType.CREDIT, Month.OCTOBER, LocalDate.of(2023, 10, 18));
        Transaction transaction35 = new Transaction("Restaurante", "Jantar fora com amigos", new BigDecimal("75.80"), TransactionType.EXPENSE, Month.NOVEMBER, LocalDate.of(2023, 11, 12));
        Transaction transaction36 = new Transaction("Compra de roupas", "Gastos com roupas de outono", new BigDecimal("150.50"), TransactionType.EXPENSE, Month.SEPTEMBER, LocalDate.of(2023, 9, 15));
        Transaction transaction37 = new Transaction("Salário", "Pagamento mensal", new BigDecimal("2500.00"), TransactionType.CREDIT, Month.SEPTEMBER, LocalDate.of(2023, 9, 30));
        Transaction transaction38 = new Transaction("Aluguel", "Pagamento do aluguel", new BigDecimal("1000.00"), TransactionType.EXPENSE, Month.OCTOBER, LocalDate.of(2023, 10, 5));
        Transaction transaction39 = new Transaction("Venda de produtos", "Receita da venda de produtos", new BigDecimal("800.00"), TransactionType.CREDIT, Month.OCTOBER, LocalDate.of(2023, 10, 18));
        Transaction transaction40 = new Transaction("Restaurante", "Jantar fora com amigos", new BigDecimal("75.80"), TransactionType.EXPENSE, Month.NOVEMBER, LocalDate.of(2023, 11, 12));

        transactions.put(transaction1.getId(), transaction1);
        transactions.put(transaction2.getId(), transaction2);
        transactions.put(transaction3.getId(), transaction3);
        transactions.put(transaction4.getId(), transaction4);
        transactions.put(transaction5.getId(), transaction5);
        transactions.put(transaction6.getId(), transaction6);
        transactions.put(transaction7.getId(), transaction7);
        transactions.put(transaction8.getId(), transaction8);
        transactions.put(transaction9.getId(), transaction9);
        transactions.put(transaction10.getId(), transaction10);
        transactions.put(transaction11.getId(), transaction11);
        transactions.put(transaction12.getId(), transaction12);
        transactions.put(transaction13.getId(), transaction13);
        transactions.put(transaction14.getId(), transaction14);
        transactions.put(transaction15.getId(), transaction15);
        transactions.put(transaction16.getId(), transaction16);
        transactions.put(transaction17.getId(), transaction17);
        transactions.put(transaction18.getId(), transaction18);
        transactions.put(transaction19.getId(), transaction19);
        transactions.put(transaction20.getId(), transaction20);
        transactions.put(transaction21.getId(), transaction21);
        transactions.put(transaction22.getId(), transaction22);
        transactions.put(transaction23.getId(), transaction23);
        transactions.put(transaction24.getId(), transaction24);
        transactions.put(transaction25.getId(), transaction25);
        transactions.put(transaction26.getId(), transaction26);
        transactions.put(transaction27.getId(), transaction27);
        transactions.put(transaction28.getId(), transaction28);
        transactions.put(transaction29.getId(), transaction29);
        transactions.put(transaction30.getId(), transaction30);
        transactions.put(transaction31.getId(), transaction31);
        transactions.put(transaction32.getId(), transaction32);
        transactions.put(transaction33.getId(), transaction33);
        transactions.put(transaction34.getId(), transaction34);
        transactions.put(transaction35.getId(), transaction35);
        transactions.put(transaction36.getId(), transaction36);
        transactions.put(transaction37.getId(), transaction37);
        transactions.put(transaction38.getId(), transaction38);
        transactions.put(transaction39.getId(), transaction39);
        transactions.put(transaction40.getId(), transaction40);
        return transactions;
    }

}
