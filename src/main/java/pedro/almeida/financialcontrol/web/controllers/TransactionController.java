package pedro.almeida.financialcontrol.web.controllers;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pedro.almeida.financialcontrol.application.dtos.request.TransactionRequestDTO;
import pedro.almeida.financialcontrol.application.dtos.response.CalculateTotalsResponseDTO;
import pedro.almeida.financialcontrol.application.dtos.response.ConsolidatedTransactionResponseDTO;
import pedro.almeida.financialcontrol.application.dtos.response.TransactionCategoryResponseDTO;
import pedro.almeida.financialcontrol.application.dtos.response.TransactionResponseDTO;
import pedro.almeida.financialcontrol.application.usecases.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final RegisterTransaction registerTransaction;
    private final FindAllTransactions findAllTransactions;
    private final CalculateTransactionsTotals calculateTransactionsTotals;
    private final ConsolidateTransactionsByMonth consolidateTransactionsByMonth;
    private final FindAllTransactionCategories findAllTransactionCategories;

    public TransactionController(RegisterTransaction registerTransaction, FindAllTransactions findAllTransactions, CalculateTransactionsTotals calculateTransactionsTotals, ConsolidateTransactionsByMonth consolidateTransactionsByMonth, FindAllTransactionCategories findAllTransactionCategories) {
        this.registerTransaction = registerTransaction;
        this.findAllTransactions = findAllTransactions;
        this.calculateTransactionsTotals = calculateTransactionsTotals;
        this.consolidateTransactionsByMonth = consolidateTransactionsByMonth;
        this.findAllTransactionCategories = findAllTransactionCategories;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponseDTO register(@RequestBody TransactionRequestDTO request) {
        return registerTransaction.execute(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionResponseDTO> findAll(
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "month", required = false) @Min(1) @Max(12) Integer month,
            @RequestParam(value = "year", required = false) @Positive @Min(2000) Integer year
    ) {
        return findAllTransactions.execute(type, month, year);
    }

    @GetMapping("/totals")
    @ResponseStatus(HttpStatus.OK)
    public CalculateTotalsResponseDTO calculateTotals(
            @RequestParam(value = "month") @Min(1) @Max(12) Integer month,
            @RequestParam(value = "year") @Positive @Min(2000) Integer year
    ) {
        return calculateTransactionsTotals.execute(month, year);
    }

    @GetMapping("/consolidated")
    @ResponseStatus(HttpStatus.OK)
    public List<ConsolidatedTransactionResponseDTO> consolidatedMonth(
            @RequestParam(value = "type") String type,
            @RequestParam(value = "month") @Min(1) @Max(12) Integer month,
            @RequestParam(value = "year") @Positive @Min(2000) Integer year
    ) {
        return consolidateTransactionsByMonth.execute(type, month, year);
    }

    @GetMapping("/categories")
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionCategoryResponseDTO> findAllCategories() {
        return findAllTransactionCategories.execute();
    }
}
