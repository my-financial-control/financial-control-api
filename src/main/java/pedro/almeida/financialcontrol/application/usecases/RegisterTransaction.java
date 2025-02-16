package pedro.almeida.financialcontrol.application.usecases;

import org.springframework.stereotype.Component;
import pedro.almeida.financialcontrol.application.dtos.request.TransactionRequestDTO;
import pedro.almeida.financialcontrol.application.dtos.response.TransactionResponseDTO;
import pedro.almeida.financialcontrol.domain.factories.TransactionFactory;
import pedro.almeida.financialcontrol.domain.models.Transaction;
import pedro.almeida.financialcontrol.domain.models.TransactionCategory;
import pedro.almeida.financialcontrol.domain.repositories.TransactionCategories;
import pedro.almeida.financialcontrol.domain.repositories.Transactions;

import java.util.UUID;

@Component
public class RegisterTransaction {

    private final Transactions transactions;
    private final TransactionCategories transactionCategories;

    public RegisterTransaction(Transactions transactions, TransactionCategories transactionCategories) {
        this.transactions = transactions;
        this.transactionCategories = transactionCategories;
    }

    public TransactionResponseDTO execute(TransactionRequestDTO transactionDTO) {
        TransactionCategory category = transactionCategories.findById(UUID.fromString(transactionDTO.categoryId()));
        Transaction transaction = TransactionFactory.buildTransaction(transactionDTO.title(), transactionDTO.description(), transactionDTO.value(), transactionDTO.type(), transactionDTO.currentMonth(), transactionDTO.date(), category);
        return new TransactionResponseDTO(transactions.save(transaction));
    }

}
