package pedro.almeida.financialcontrol.application.usecases;

import pedro.almeida.financialcontrol.application.dtos.response.ConsolidatedTransactionResponseDTO;
import pedro.almeida.financialcontrol.domain.models.ConsolidatedTransaction;
import pedro.almeida.financialcontrol.domain.services.ExtractConsultation;

import java.util.List;

public class ConsolidateTransactionsByMonth {
    private final ExtractConsultation extractConsultation;

    public ConsolidateTransactionsByMonth(ExtractConsultation extractConsultation) {
        this.extractConsultation = extractConsultation;
    }

    public List<ConsolidatedTransactionResponseDTO> execute(String type, Integer month, Integer year) {
        List<ConsolidatedTransaction> transactionsConsolidated = extractConsultation.consolidateByMonth(type, month, year);
        return ConsolidatedTransactionResponseDTO.toConsolidatedTransactionDTO(transactionsConsolidated);
    }
}
