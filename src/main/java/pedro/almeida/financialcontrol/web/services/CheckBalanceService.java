package pedro.almeida.financialcontrol.web.services;

import org.springframework.stereotype.Service;
import pedro.almeida.financialcontrol.domain.usecases.CheckBalance;
import pedro.almeida.financialcontrol.domain.usecases.CheckBalancePlusRemainingPayments;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

@Service
public class CheckBalanceService {
    private final CheckBalance checkBalance;
    private final CheckBalancePlusRemainingPayments checkBalancePlusRemainingPayments;

    public CheckBalanceService(CheckBalance checkBalance, CheckBalancePlusRemainingPayments checkBalancePlusRemainingPayments) {
        this.checkBalance = checkBalance;
        this.checkBalancePlusRemainingPayments = checkBalancePlusRemainingPayments;
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

    public BigDecimal checkBalancePlusRemainingPayments(Integer month, Integer year) {
        if (month == null) {
            return checkBalancePlusRemainingPayments.execute();
        }
        if (year == null) {
            year = LocalDate.now().getYear();
        }
        return checkBalancePlusRemainingPayments.execute(Month.of(month), year);
    }
}
