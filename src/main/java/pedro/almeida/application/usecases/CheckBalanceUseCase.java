package pedro.almeida.application.usecases;

import pedro.almeida.domain.repositories.Borrowings;
import pedro.almeida.domain.repositories.Transactions;
import pedro.almeida.domain.usecases.CheckBalance;

import java.math.BigDecimal;
import java.time.Month;

public class CheckBalanceUseCase implements CheckBalance {

    private final Transactions transactions;
    private final Borrowings borrowings;

    public CheckBalanceUseCase(Transactions transactions, Borrowings borrowings) {
        this.transactions = transactions;
        this.borrowings = borrowings;
    }

    @Override
    public BigDecimal execute() {
        BigDecimal transactionsBalance = this.transactions.sumOfCredits().subtract(this.transactions.sumOfExpenses());
        BigDecimal borrowingsRemainingPayment = this.borrowings.sumOfRemainingPayment();
        return transactionsBalance.subtract(borrowingsRemainingPayment);
    }

    @Override
    public BigDecimal execute(Month month, int year) {
        BigDecimal transactionsBalance = this.transactions.sumOfCredits(month, year).subtract(this.transactions.sumOfExpenses(month, year));
        BigDecimal borrowingsRemainingPayment = this.borrowings.sumOfRemainingPayment(month, year);
        return transactionsBalance.subtract(borrowingsRemainingPayment);
    }

}
