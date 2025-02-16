package pedro.almeida.financialcontrol.application.usecases;

import org.springframework.stereotype.Component;
import pedro.almeida.financialcontrol.application.dtos.response.CalculateTotalsResponseDTO;
import pedro.almeida.financialcontrol.domain.repositories.Transactions;

import java.math.BigDecimal;
import java.time.Month;

@Component
public class CalculateTransactionsTotals {
    private final Transactions transactions;

    public CalculateTransactionsTotals(Transactions transactions) {
        this.transactions = transactions;
    }

    public CalculateTotalsResponseDTO execute(Integer month, Integer year) {
        BigDecimal credits = transactions.sumOfCredits(Month.of(month), year);
        BigDecimal expenses = transactions.sumOfExpenses(Month.of(month), year);
        return new CalculateTotalsResponseDTO(credits, expenses);
    }
}
