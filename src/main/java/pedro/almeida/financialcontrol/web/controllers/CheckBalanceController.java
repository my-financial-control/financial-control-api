package pedro.almeida.financialcontrol.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
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
    public CheckBalanceResponseDTO checkBalance() {
        return new CheckBalanceResponseDTO(checkBalanceService.checkBalance());
    }

}
