package pedro.almeida.financialcontrol.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pedro.almeida.financialcontrol.application.usecases.FindAllTransactionsUseCase;
import pedro.almeida.financialcontrol.application.usecases.RegisterTransactionUseCase;
import pedro.almeida.financialcontrol.domain.repositories.Transactions;
import pedro.almeida.financialcontrol.domain.usecases.FindAllTransactions;
import pedro.almeida.financialcontrol.domain.usecases.RegisterTransaction;
import pedro.almeida.financialcontrol.infra.repositories.inmemory.TransactionsInMemoryRepository;

@Configuration
public class Beans {

    private static final Transactions transactions = new TransactionsInMemoryRepository();

    @Bean
    public FindAllTransactions findAllTransactions() {
        return new FindAllTransactionsUseCase(transactions);
    }

    @Bean
    public RegisterTransaction registerTransaction() {
        return new RegisterTransactionUseCase(transactions);
    }

}
