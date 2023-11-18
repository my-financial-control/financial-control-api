package pedro.almeida.financialcontrol.web.services;

import org.springframework.stereotype.Service;
import pedro.almeida.financialcontrol.domain.usecases.CheckBalance;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

@Service
public class CheckBalanceService {

    private final CheckBalance checkBalance;

    public CheckBalanceService(CheckBalance checkBalance) {
        this.checkBalance = checkBalance;
    }

    public BigDecimal checkBalance(Integer month, Integer year) {
        if (month == null) {
            return this.checkBalance.execute();
        }
        if (year == null) {
            year = LocalDate.now().getYear();
        }
        return this.checkBalance.execute(Month.of(month), year);
    }

}
