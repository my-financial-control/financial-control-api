package pedro.almeida.financialcontrol.domain.repositories;

import pedro.almeida.financialcontrol.domain.models.TransactionCategory;

import java.util.List;
import java.util.UUID;

public interface TransactionCategories {
    TransactionCategory findById(UUID id);
    List<TransactionCategory> findAll();
}
