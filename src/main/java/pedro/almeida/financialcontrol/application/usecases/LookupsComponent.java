package pedro.almeida.financialcontrol.application.usecases;

import org.springframework.stereotype.Component;
import pedro.almeida.financialcontrol.domain.repositories.Lookups;

import java.util.List;

@Component
public class LookupsComponent {
    private final Lookups repository;

    public LookupsComponent(Lookups repository) {
        this.repository = repository;
    }

    public List<String> transactionTitles() {
        return repository.transactionTitles();
    }
}
