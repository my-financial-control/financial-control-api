package pedro.almeida.financialcontrol.web.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pedro.almeida.financialcontrol.web.dtos.request.TransactionRequestDTO;
import pedro.almeida.financialcontrol.web.dtos.response.TransactionResponseDTO;
import pedro.almeida.financialcontrol.web.services.TransactionService;

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
        return new TransactionResponseDTO(this.transactionService.register(transactionRequestDTO.toTransaction()));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionResponseDTO> findAll(
            @RequestParam(value = "month", required = false) @Min(1) @Max(12) Integer month,
            @RequestParam(value = "year", required = false) @Positive @Min(2000) Integer year
    ) {
        return TransactionResponseDTO.toTransactionDTO(this.transactionService.findAll(month, year));
    }

}
