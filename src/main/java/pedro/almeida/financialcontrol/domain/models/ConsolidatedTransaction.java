package pedro.almeida.financialcontrol.domain.models;

import pedro.almeida.financialcontrol.domain.errors.ConsolidatedTransactionException;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class ConsolidatedTransaction {
    private final String title;
    private final BigDecimal total;
    private final List<Transaction> transactions;

    public ConsolidatedTransaction(List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            throw ConsolidatedTransactionException.noTransactionsToConsolidate();
        }
        this.title = transactions.get(0).getTitle();
        this.total = transactions.stream().map(Transaction::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
        this.transactions = transactions;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }
}
