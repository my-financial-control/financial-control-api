package pedro.almeida.financialcontrol.web.controllers;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pedro.almeida.financialcontrol.application.dtos.response.CheckBalanceResponseDTO;
import pedro.almeida.financialcontrol.application.usecases.CheckBalance;
import pedro.almeida.financialcontrol.application.usecases.CheckBalancePlusRemainingPayments;

@Validated
@RestController
@RequestMapping("/api/v1/check-balance")
public class CheckBalanceController {
    private final CheckBalance checkBalance;
    private final CheckBalancePlusRemainingPayments checkBalancePlusRemainingPayments;

    public CheckBalanceController(CheckBalance checkBalance, CheckBalancePlusRemainingPayments checkBalancePlusRemainingPayments) {
        this.checkBalance = checkBalance;
        this.checkBalancePlusRemainingPayments = checkBalancePlusRemainingPayments;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CheckBalanceResponseDTO checkBalance(
            @RequestParam(value = "month", required = false) @Min(1) @Max(12) Integer month,
            @RequestParam(value = "year", required = false) @Positive @Min(2000) Integer year
    ) {
        return checkBalance.execute(month, year);
    }

    @GetMapping("/plus-remaining-payments")
    @ResponseStatus(HttpStatus.OK)
    public CheckBalanceResponseDTO checkBalancePlusRemainingPayments(
            @RequestParam(value = "month", required = false) @Min(1) @Max(12) Integer month,
            @RequestParam(value = "year", required = false) @Positive @Min(2000) Integer year
    ) {
        return checkBalancePlusRemainingPayments.execute(month, year);
    }
}
