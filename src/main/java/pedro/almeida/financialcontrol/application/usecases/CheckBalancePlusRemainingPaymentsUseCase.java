package pedro.almeida.financialcontrol.application.usecases;

import pedro.almeida.financialcontrol.domain.models.Extract;
import pedro.almeida.financialcontrol.domain.usecases.CheckBalancePlusRemainingPayments;

import java.math.BigDecimal;
import java.time.Month;

public class CheckBalancePlusRemainingPaymentsUseCase implements CheckBalancePlusRemainingPayments {
    private final Extract extract;

    public CheckBalancePlusRemainingPaymentsUseCase(Extract extract) {
        this.extract = extract;
    }

    @Override
    public BigDecimal execute() {
        return extract.checkBalancePlusRemainingPayment();
    }

    @Override
    public BigDecimal execute(Month month, int year) {
        return extract.checkBalancePlusRemainingPayment(month, year);
    }
}
