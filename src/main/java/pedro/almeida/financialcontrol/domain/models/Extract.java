package pedro.almeida.financialcontrol.domain.models;

import pedro.almeida.financialcontrol.domain.repositories.Borrowings;
import pedro.almeida.financialcontrol.domain.repositories.Transactions;

import java.math.BigDecimal;
import java.time.Month;

public class Extract {
    private final Transactions transactions;
    private final Borrowings borrowings;

    public Extract(Transactions transactions, Borrowings borrowings) {
        this.transactions = transactions;
        this.borrowings = borrowings;
    }

    public BigDecimal checkBalance() {
        BigDecimal transactionsBalance = this.transactions.sumOfCredits().subtract(this.transactions.sumOfExpenses());
        BigDecimal borrowingsRemainingPayment = this.borrowings.sumOfRemainingPayment();
        return transactionsBalance.subtract(borrowingsRemainingPayment);
    }

    public BigDecimal checkBalance(Month month, int year) {
        BigDecimal transactionsBalance = this.transactions.sumOfCredits(month, year).subtract(this.transactions.sumOfExpenses(month, year));
        BigDecimal borrowingsRemainingPayment = this.borrowings.sumOfRemainingPayment(month, year);
        return transactionsBalance.subtract(borrowingsRemainingPayment);
    }

    public BigDecimal checkBalancePlusRemainingPayment() {
        BigDecimal transactionsBalance = this.transactions.sumOfCredits().subtract(this.transactions.sumOfExpenses());
        BigDecimal borrowingsRemainingPayment = this.borrowings.sumOfRemainingPayment();
        return transactionsBalance.add(borrowingsRemainingPayment);
    }

    public BigDecimal checkBalancePlusRemainingPayment(Month month, int year) {
        BigDecimal transactionsBalance = this.transactions.sumOfCredits(month, year).subtract(this.transactions.sumOfExpenses(month, year));
        BigDecimal borrowingsRemainingPayment = this.borrowings.sumOfRemainingPayment(month, year);
        return transactionsBalance.add(borrowingsRemainingPayment);
    }
}
