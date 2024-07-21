package pedro.almeida.financialcontrol.application.usecases;

import pedro.almeida.financialcontrol.domain.services.ExtractConsultation;
import pedro.almeida.financialcontrol.domain.usecases.CheckBalance;

import java.math.BigDecimal;
import java.time.Month;

public class CheckBalanceUseCase implements CheckBalance {

    private final ExtractConsultation extractConsultation;

    public CheckBalanceUseCase(ExtractConsultation extractConsultation) {
        this.extractConsultation = extractConsultation;
    }

    @Override
    public BigDecimal execute() {
        return extractConsultation.checkBalance();
    }

    @Override
    public BigDecimal execute(Month month, int year) {
        return extractConsultation.checkBalance(month, year);
    }

}
