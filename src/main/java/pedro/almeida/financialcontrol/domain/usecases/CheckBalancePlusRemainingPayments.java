package pedro.almeida.financialcontrol.domain.usecases;

import java.math.BigDecimal;
import java.time.Month;

public interface CheckBalancePlusRemainingPayments {
    BigDecimal execute();

    BigDecimal execute(Month month, int year);
}
