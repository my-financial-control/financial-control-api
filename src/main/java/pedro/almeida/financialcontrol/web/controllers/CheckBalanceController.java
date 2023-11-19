package pedro.almeida.financialcontrol.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pedro.almeida.financialcontrol.web.dtos.response.CheckBalanceResponseDTO;
import pedro.almeida.financialcontrol.web.services.CheckBalanceService;

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
            @RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "year", required = false) Integer year
    ) {
        return new CheckBalanceResponseDTO(checkBalanceService.checkBalance(month, year));
    }

}
