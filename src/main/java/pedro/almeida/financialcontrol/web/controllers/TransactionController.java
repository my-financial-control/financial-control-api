package pedro.almeida.financialcontrol.web.controllers;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pedro.almeida.financialcontrol.application.dtos.request.TransactionWithReceiptDTO;
import pedro.almeida.financialcontrol.application.dtos.response.CalculateTotalsResponseDTO;
import pedro.almeida.financialcontrol.application.dtos.response.ConsolidatedTransactionResponseDTO;
import pedro.almeida.financialcontrol.application.dtos.response.TransactionCategoryResponseDTO;
import pedro.almeida.financialcontrol.application.dtos.response.TransactionResponseDTO;
import pedro.almeida.financialcontrol.application.usecases.*;
import pedro.almeida.financialcontrol.domain.models.Receipt;

import java.io.IOException;
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
    private final FindTransactionReceipt findTransactionReceipt;

    public TransactionController(RegisterTransaction registerTransaction, FindAllTransactions findAllTransactions, CalculateTransactionsTotals calculateTransactionsTotals, ConsolidateTransactionsByMonth consolidateTransactionsByMonth, FindAllTransactionCategories findAllTransactionCategories, FindTransactionReceipt findTransactionReceipt) {
        this.registerTransaction = registerTransaction;
        this.findAllTransactions = findAllTransactions;
        this.calculateTransactionsTotals = calculateTransactionsTotals;
        this.consolidateTransactionsByMonth = consolidateTransactionsByMonth;
        this.findAllTransactionCategories = findAllTransactionCategories;
        this.findTransactionReceipt = findTransactionReceipt;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponseDTO register(@ModelAttribute TransactionWithReceiptDTO request) {
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

    @GetMapping("/{id}/receipt")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<byte[]> findReceipt(@PathVariable("id") String transactionId) throws IOException {
        Receipt receipt = findTransactionReceipt.execute(transactionId);

        if (receipt == null) {
            return ResponseEntity.notFound().build();
        }

        byte[] fileContent = receipt.getContent().readAllBytes();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + receipt.getFileName());
        headers.add(HttpHeaders.CONTENT_TYPE, receipt.getContentType());

        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
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
