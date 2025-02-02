package pedro.almeida.financialcontrol.application.dtos.response;

import pedro.almeida.financialcontrol.domain.models.ConsolidatedTransaction;

import java.math.BigDecimal;
import java.util.List;

public record ConsolidatedTransactionResponseDTO(
        String title,
        BigDecimal total,
        List<TransactionResponseDTO> transactions
) {
    public ConsolidatedTransactionResponseDTO(ConsolidatedTransaction consolidatedTransaction) {
        this(
                consolidatedTransaction.getTitle(),
                consolidatedTransaction.getTotal(),
                TransactionResponseDTO.toTransactionDTO(consolidatedTransaction.getTransactions())
        );
    }

    public static List<ConsolidatedTransactionResponseDTO> toConsolidatedTransactionDTO(List<ConsolidatedTransaction> consolidatedTransactions) {
        return consolidatedTransactions.stream().map(ConsolidatedTransactionResponseDTO::new).toList();
    }
}
