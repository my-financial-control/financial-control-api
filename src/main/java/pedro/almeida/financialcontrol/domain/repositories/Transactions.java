package pedro.almeida.financialcontrol.domain.repositories;

import pedro.almeida.financialcontrol.domain.models.Transaction;

import java.math.BigDecimal;
import java.time.Month;
import java.util.List;

public interface Transactions {

    Transaction save(Transaction transaction);

    List<Transaction> findAll(String type, Integer month, Integer year);

    BigDecimal sumOfCredits();

    BigDecimal sumOfCredits(Month month, Integer year);

    BigDecimal sumOfExpenses();

    BigDecimal sumOfExpenses(Month month, Integer year);

}
