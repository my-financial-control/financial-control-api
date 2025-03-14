package pedro.almeida.financialcontrol.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pedro.almeida.financialcontrol.domain.repositories.Borrowings;
import pedro.almeida.financialcontrol.domain.repositories.Transactions;
import pedro.almeida.financialcontrol.domain.services.ExtractConsultation;
import pedro.almeida.financialcontrol.domain.services.ReceiptService;
import pedro.almeida.financialcontrol.domain.services.interfaces.Storage;

@Configuration
public class Beans {

    @Bean
    public ExtractConsultation extractConsultation(Transactions transactions, Borrowings borrowings) {
        return new ExtractConsultation(transactions, borrowings);
    }

    @Bean
    public ReceiptService receiptService(Storage storage) {
        return new ReceiptService(storage);
    }
}
