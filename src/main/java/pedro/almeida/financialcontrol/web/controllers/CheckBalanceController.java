package pedro.almeida.financialcontrol.web.controllers;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pedro.almeida.financialcontrol.web.dtos.response.*;
import pedro.almeida.financialcontrol.web.services.*;

@Validated
@RestController
@RequestMapping("/api/v1/check-balance")
public class CheckBalanceController {

    private final CheckBalanceService checkBalanceService;

    public CheckBalanceController(CheckBalanceService checkBalanceService) {
        this.checkBalanceService = checkBalanceService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CheckBalanceResponseDTO checkBalance(
            @RequestParam(value = "month", required = false) @Min(1) @Max(12) Integer month,
            @RequestParam(value = "year", required = false) @Positive @Min(2000) Integer year
    ) {
        return new CheckBalanceResponseDTO(checkBalanceService.checkBalance(month, year));
    }

    @GetMapping("/plus-remaining-payments")
    @ResponseStatus(HttpStatus.OK)
    public CheckBalanceResponseDTO checkBalancePlusRemainingPayments(
            @RequestParam(value = "month", required = false) @Min(1) @Max(12) Integer month,
            @RequestParam(value = "year", required = false) @Positive @Min(2000) Integer year
    ) {
        return new CheckBalanceResponseDTO(checkBalanceService.checkBalancePlusRemainingPayments(month, year));
    }
}
