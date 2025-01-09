package pedro.almeida.financialcontrol.application.usecases;

import org.springframework.stereotype.Component;
import pedro.almeida.financialcontrol.application.dtos.response.TransactionResponseDTO;
import pedro.almeida.financialcontrol.domain.models.Transaction;
import pedro.almeida.financialcontrol.domain.repositories.Transactions;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Component
public class FindAllTransactions {

    private final Transactions transactions;

    public FindAllTransactions(Transactions transactions) {
        this.transactions = transactions;
    }

    public List<TransactionResponseDTO> execute() {
        List<Transaction> trs = transactions.findAll();
        return TransactionResponseDTO.toTransactionDTO(trs);
    }

    public List<TransactionResponseDTO> execute(Integer month, Integer year) {
        if (month == null) {
            return execute();
        }
        if (year == null) {
            year = LocalDate.now().getYear();
        }
        List<Transaction> trs = transactions.findAll(Month.of(month), year);
        return TransactionResponseDTO.toTransactionDTO(trs);
    }

}
