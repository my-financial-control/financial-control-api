package pedro.almeida.domain.repositories;

import pedro.almeida.domain.models.Transaction;
import pedro.almeida.domain.models.TransactionType;

import java.math.BigDecimal;
import java.time.Month;
import java.util.List;

public interface Transactions {

    Transaction save(Transaction transaction);
    List<Transaction> findAll();
    List<Transaction> findAll(TransactionType type);
    List<Transaction> findAll(Month month, int year);
    List<Transaction> findAll(Month month, int year, TransactionType type);
    BigDecimal sumOfCredits();
    BigDecimal sumOfCredits(Month month, int year);
    BigDecimal sumOfExpenses();
    BigDecimal sumOfExpenses(Month month, int year);

}
