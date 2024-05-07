package pedro.almeida.financialcontrol.application.usecases;

import pedro.almeida.financialcontrol.domain.models.Extract;
import pedro.almeida.financialcontrol.domain.usecases.CheckBalance;

import java.math.BigDecimal;
import java.time.Month;

public class CheckBalanceUseCase implements CheckBalance {

    private final Extract extract;

    public CheckBalanceUseCase(Extract extract) {
        this.extract = extract;
    }

    @Override
    public BigDecimal execute() {
        return extract.checkBalance();
    }

    @Override
    public BigDecimal execute(Month month, int year) {
        return extract.checkBalance(month, year);
    }

}
