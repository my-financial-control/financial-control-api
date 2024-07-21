package pedro.almeida.financialcontrol.application.usecases;

import pedro.almeida.financialcontrol.domain.services.ExtractConsultation;
import pedro.almeida.financialcontrol.domain.usecases.CheckBalancePlusRemainingPayments;

import java.math.BigDecimal;
import java.time.Month;

public class CheckBalancePlusRemainingPaymentsUseCase implements CheckBalancePlusRemainingPayments {
    private final ExtractConsultation extractConsultation;

    public CheckBalancePlusRemainingPaymentsUseCase(ExtractConsultation extractConsultation) {
        this.extractConsultation = extractConsultation;
    }

    @Override
    public BigDecimal execute() {
        return extractConsultation.checkBalancePlusRemainingPayment();
    }

    @Override
    public BigDecimal execute(Month month, int year) {
        return extractConsultation.checkBalancePlusRemainingPayment(month, year);
    }
}
