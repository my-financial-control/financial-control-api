package pedro.almeida.financialcontrol.application.usecases;

import org.springframework.stereotype.Component;
import pedro.almeida.financialcontrol.domain.services.ExtractConsultation;

import java.math.BigDecimal;
import java.time.Month;

@Component
public class CheckBalancePlusRemainingPayments {
    private final ExtractConsultation extractConsultation;

    public CheckBalancePlusRemainingPayments(ExtractConsultation extractConsultation) {
        this.extractConsultation = extractConsultation;
    }

    public BigDecimal execute() {
        return extractConsultation.checkBalancePlusRemainingPayment();
    }

    public BigDecimal execute(Month month, int year) {
        return extractConsultation.checkBalancePlusRemainingPayment(month, year);
    }
}
