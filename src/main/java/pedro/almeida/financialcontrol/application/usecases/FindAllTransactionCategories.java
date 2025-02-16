package pedro.almeida.financialcontrol.application.usecases;

import org.springframework.stereotype.Component;
import pedro.almeida.financialcontrol.application.dtos.response.TransactionCategoryResponseDTO;
import pedro.almeida.financialcontrol.domain.models.TransactionCategory;
import pedro.almeida.financialcontrol.domain.repositories.TransactionCategories;

import java.util.List;

@Component
public class FindAllTransactionCategories {
    private final TransactionCategories transactionCategoryRepository;

    public FindAllTransactionCategories(TransactionCategories transactionCategoryRepository) {
        this.transactionCategoryRepository = transactionCategoryRepository;
    }

    public List<TransactionCategoryResponseDTO> execute() {
        List<TransactionCategory> categories = transactionCategoryRepository.findAll();
        return categories.stream().map(TransactionCategoryResponseDTO::new).toList();
    }
}
