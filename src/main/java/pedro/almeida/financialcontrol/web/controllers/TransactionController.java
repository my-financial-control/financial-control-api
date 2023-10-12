package pedro.almeida.financialcontrol.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pedro.almeida.financialcontrol.domain.models.Transaction;
import pedro.almeida.financialcontrol.web.services.TransactionService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Transaction> findAll(
            @RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "year", required = false) Integer year
    ) {
        return this.transactionService.findAll(month, year);
    }

}
