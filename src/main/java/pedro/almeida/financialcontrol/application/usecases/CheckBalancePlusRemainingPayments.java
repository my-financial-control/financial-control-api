package pedro.almeida.financialcontrol.application.usecases;

import org.springframework.stereotype.Component;
import pedro.almeida.financialcontrol.application.dtos.response.CheckBalanceResponseDTO;
import pedro.almeida.financialcontrol.domain.services.ExtractConsultation;

import java.time.LocalDate;
import java.time.Month;

@Component
public class CheckBalancePlusRemainingPayments {
    private final ExtractConsultation extractConsultation;

    public CheckBalancePlusRemainingPayments(ExtractConsultation extractConsultation) {
        this.extractConsultation = extractConsultation;
    }

    public CheckBalanceResponseDTO execute() {
        return new CheckBalanceResponseDTO(extractConsultation.checkBalancePlusRemainingPayment());
    }

    public CheckBalanceResponseDTO execute(Integer month, Integer year) {
        if (month == null) {
            return execute();
        }
        if (year == null) {
            year = LocalDate.now().getYear();
        }
        return new CheckBalanceResponseDTO(extractConsultation.checkBalancePlusRemainingPayment(Month.of(month), year));
    }
}
