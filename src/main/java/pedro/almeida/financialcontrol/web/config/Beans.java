package pedro.almeida.financialcontrol.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pedro.almeida.financialcontrol.application.usecases.FindAllTransactionsUseCase;
import pedro.almeida.financialcontrol.domain.usecases.FindAllTransactions;
import pedro.almeida.financialcontrol.infra.repositories.inmemory.TransactionsInMemoryRepository;

@Configuration
public class Beans {

    @Bean
    public FindAllTransactions findAllTransactions() {
        return new FindAllTransactionsUseCase(new TransactionsInMemoryRepository());
    }

}
