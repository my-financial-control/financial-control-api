package pedro.almeida.financialcontrol.web.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pedro.almeida.financialcontrol.application.usecases.LookupsComponent;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lookups")
public class LookupController {
    private final LookupsComponent lookupsComponent;

    public LookupController(LookupsComponent lookupsComponent) {
        this.lookupsComponent = lookupsComponent;
    }

    @GetMapping("/transactions")
    public List<String> findTransactionTitles() {
        return lookupsComponent.transactionTitles();
    }
}
