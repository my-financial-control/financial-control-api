package pedro.almeida.financialcontrol.application.usecases;

import org.springframework.stereotype.Component;
import pedro.almeida.financialcontrol.application.dtos.response.TransactionResponseDTO;
import pedro.almeida.financialcontrol.domain.repositories.Transactions;

import java.util.List;

@Component
public class FindAllTransactions {

    private final Transactions transactions;

    public FindAllTransactions(Transactions transactions) {
        this.transactions = transactions;
    }

    public List<TransactionResponseDTO> execute( String type, Integer month, Integer year) {
        return TransactionResponseDTO.toTransactionDTO(transactions.findAll(type, month, year));
    }
}
