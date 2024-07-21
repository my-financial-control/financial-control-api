package pedro.almeida.financialcontrol.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pedro.almeida.financialcontrol.application.usecases.*;
import pedro.almeida.financialcontrol.domain.services.ExtractConsultation;
import pedro.almeida.financialcontrol.domain.repositories.Borrowings;
import pedro.almeida.financialcontrol.domain.repositories.Transactions;
import pedro.almeida.financialcontrol.domain.usecases.*;
import pedro.almeida.financialcontrol.infra.repositories.inmemory.BorrowingInMemoryRepository;
import pedro.almeida.financialcontrol.infra.repositories.inmemory.TransactionsInMemoryRepository;

@Configuration
public class Beans {

    private static final Transactions transactions = new TransactionsInMemoryRepository();
    private static final Borrowings borrowings = new BorrowingInMemoryRepository();

    @Bean
    public FindAllTransactions findAllTransactions() {
        return new FindAllTransactionsUseCase(transactions);
    }

    @Bean
    public RegisterTransaction registerTransaction() {
        return new RegisterTransactionUseCase(transactions);
    }

    @Bean
    public CheckBalance checkBalance() {
        return new CheckBalanceUseCase(new ExtractConsultation(transactions, borrowings));
    }

    @Bean
    public CheckBalancePlusRemainingPayments checkBalancePlusRemainingPayments() {
        return new CheckBalancePlusRemainingPaymentsUseCase(new ExtractConsultation(transactions, borrowings));
    }

    @Bean
    public RegisterBorrowing registerBorrowing() {
        return new RegisterBorrowingUseCase(borrowings);
    }

    @Bean
    public FindAllBorrowings findAllBorrowings() {
        return new FindAllBorrowingsUseCase(borrowings);
    }

    @Bean
    public PayParcelBorrowing payParcelBorrowing() {
        return new PayParcelBorrowingUseCase(borrowings);
    }

}
