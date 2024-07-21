package pedro.almeida.financialcontrol.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pedro.almeida.financialcontrol.application.usecases.*;
import pedro.almeida.financialcontrol.domain.services.*;
import pedro.almeida.financialcontrol.domain.repositories.*;
import pedro.almeida.financialcontrol.infra.repositories.inmemory.*;

@Configuration
public class Beans {

    private static final Transactions transactions = new TransactionsInMemoryRepository();
    private static final Borrowings borrowings = new BorrowingInMemoryRepository();

    @Bean
    public FindAllTransactions findAllTransactions() {
        return new FindAllTransactions(transactions);
    }

    @Bean
    public RegisterTransaction registerTransaction() {
        return new RegisterTransaction(transactions);
    }

    @Bean
    public CheckBalance checkBalance() {
        return new CheckBalance(new ExtractConsultation(transactions, borrowings));
    }

    @Bean
    public CheckBalancePlusRemainingPayments checkBalancePlusRemainingPayments() {
        return new CheckBalancePlusRemainingPayments(new ExtractConsultation(transactions, borrowings));
    }

    @Bean
    public RegisterBorrowing registerBorrowing() {
        return new RegisterBorrowing(borrowings);
    }

    @Bean
    public FindAllBorrowings findAllBorrowings() {
        return new FindAllBorrowings(borrowings);
    }

    @Bean
    public PayParcelBorrowing payParcelBorrowing() {
        return new PayParcelBorrowing(borrowings);
    }

}
