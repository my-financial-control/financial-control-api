package pedro.almeida.financialcontrol.domain.services;

import pedro.almeida.financialcontrol.domain.repositories.Borrowings;
import pedro.almeida.financialcontrol.domain.repositories.Transactions;

import java.math.BigDecimal;
import java.time.Month;

public class ExtractConsultation {
    private final Transactions transactions;
    private final Borrowings borrowings;

    public ExtractConsultation(Transactions transactions, Borrowings borrowings) {
        this.transactions = transactions;
        this.borrowings = borrowings;
    }

    public BigDecimal checkBalance() {
        BigDecimal transactionsBalance = transactions.sumOfCredits().subtract(transactions.sumOfExpenses());
        BigDecimal borrowingsRemainingPayment = borrowings.sumOfRemainingPayment();
        return transactionsBalance.subtract(borrowingsRemainingPayment);
    }

    public BigDecimal checkBalance(Month month, int year) {
        BigDecimal transactionsBalance = transactions.sumOfCredits(month, year).subtract(transactions.sumOfExpenses(month, year));
        BigDecimal borrowingsRemainingPayment = borrowings.sumOfRemainingPayment(month, year);
        return transactionsBalance.subtract(borrowingsRemainingPayment);
    }

    public BigDecimal checkBalancePlusRemainingPayment() {
        BigDecimal transactionsBalance = transactions.sumOfCredits().subtract(transactions.sumOfExpenses());
        BigDecimal borrowingsRemainingPayment = borrowings.sumOfRemainingPayment();
        return transactionsBalance.add(borrowingsRemainingPayment);
    }

    public BigDecimal checkBalancePlusRemainingPayment(Month month, int year) {
        BigDecimal transactionsBalance = transactions.sumOfCredits(month, year).subtract(transactions.sumOfExpenses(month, year));
        BigDecimal borrowingsRemainingPayment = borrowings.sumOfRemainingPayment(month, year);
        return transactionsBalance.add(borrowingsRemainingPayment);
    }
}
