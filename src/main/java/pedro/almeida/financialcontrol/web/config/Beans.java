package pedro.almeida.financialcontrol.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pedro.almeida.financialcontrol.domain.usecases.FindAllTransactions;
import pedro.almeida.financialcontrol.repositories.inmemory.TransactionsInMemoryRepository;
import pedro.almeida.financialcontrol.usecases.FindAllTransactionsUseCase;

@Configuration
public class Beans {

    @Bean
    public FindAllTransactions findAllTransactions() {
        return new FindAllTransactionsUseCase(new TransactionsInMemoryRepository());
    }

}
