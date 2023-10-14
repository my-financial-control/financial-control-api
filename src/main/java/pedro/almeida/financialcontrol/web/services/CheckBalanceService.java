package pedro.almeida.financialcontrol.web.services;

import org.springframework.stereotype.Service;
import pedro.almeida.financialcontrol.domain.usecases.CheckBalance;

import java.math.BigDecimal;

@Service
public class CheckBalanceService {

    private final CheckBalance checkBalance;

    public CheckBalanceService(CheckBalance checkBalance) {
        this.checkBalance = checkBalance;
    }

    public BigDecimal checkBalance() {
        return this.checkBalance.execute();
    }

}
