package pedro.almeida.financialcontrol.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pedro.almeida.financialcontrol.domain.repositories.Borrowings;
import pedro.almeida.financialcontrol.domain.repositories.Transactions;
import pedro.almeida.financialcontrol.domain.services.ExtractConsultation;

@Configuration
public class Beans {

    @Bean
    public ExtractConsultation extractConsultation(Transactions transactions, Borrowings borrowings) {
        return new ExtractConsultation(transactions, borrowings);
    }
}
