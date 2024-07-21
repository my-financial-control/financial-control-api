package pedro.almeida.financialcontrol.web.controllers;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pedro.almeida.financialcontrol.web.dtos.request.*;
import pedro.almeida.financialcontrol.web.dtos.response.*;
import pedro.almeida.financialcontrol.web.services.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponseDTO register(@RequestBody TransactionRequestDTO transactionRequestDTO) {
        return new TransactionResponseDTO(transactionService.register(transactionRequestDTO.toTransaction()));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionResponseDTO> findAll(
            @RequestParam(value = "month", required = false) @Min(1) @Max(12) Integer month,
            @RequestParam(value = "year", required = false) @Positive @Min(2000) Integer year
    ) {
        return TransactionResponseDTO.toTransactionDTO(transactionService.findAll(month, year));
    }

}
