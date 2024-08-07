package pedro.almeida.financialcontrol.application.usecases;

import pedro.almeida.financialcontrol.domain.services.ExtractConsultation;

import java.math.BigDecimal;
import java.time.Month;

public class CheckBalance {

    private final ExtractConsultation extractConsultation;

    public CheckBalance(ExtractConsultation extractConsultation) {
        this.extractConsultation = extractConsultation;
    }

    public BigDecimal execute() {
        return extractConsultation.checkBalance();
    }

    public BigDecimal execute(Month month, int year) {
        return extractConsultation.checkBalance(month, year);
    }

}
